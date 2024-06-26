<script setup>
import { ref, watch } from 'vue';
import Tabs from '@/components/Tabs.vue';
import Table from "@/components/Table.vue";
import Pager from '@/components/Pager.vue';
import {bookPageListService, updateStatusBatchService, updateStatusService} from '@/api/audit';
import { Select, CloseBold } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus';

// 假设您有以下标签页数据
const tabs = [
  { name: '全部', label: '全部', total: 0 },
  { name: '待审核', label: '待审核', total: 0 },
  { name: '待使用', label: '待使用', total: 0 },
  { name: '已通过', label: '已通过', total: 0 },
  { name: '未通过', label: '未通过', total: 0 },
];

const currentTab = ref(tabs[0].name);

const currentContent = ref([{
  id: '',
  equipmentId: '',
  equipmentName: '',
  name: '',
  createTime: '',
  startTime: '',
  endTime: '',
  reason: '',
  status: '',
}]);

const title = ref('');
const columns = ref([
  { label: '申请单号', prop: 'id', width: '120', align: 'center', sortable: true },
  { label: '设备编号', prop: 'equipmentId', width: '80', align: 'center' },
  { label: '设备名称', prop: 'equipmentName', width: '200', align: 'center' },
  { label: '申请人', prop: 'name', align: 'center' },
  { label: '申请时间', prop: 'createTime', width: '120', align: 'center', sortable: true },
  { label: '预约开始时间', prop: 'startTime', width: '120', align: 'center' },
  { label: '预约结束时间', prop: 'endTime', width: '120', align: 'center' },
  { label: '用途', prop: 'reason', align: 'center' },
  { label: '审核状态', prop: 'status', width: '100', align: 'center' },
  { label: '操作', prop: 'action', width: '120', align: 'center',
    slot: [
      { 
        icon: Select, 
        type: row => row.status === '待审核' ? 'success' : 'info', 
        action: row => onClickButton(row, '通过')
      },
      { 
        icon: CloseBold, 
        type: row => row.status === '待审核' ? 'danger' : 'info', 
        action: row => onClickButton(row, '拒绝')
      }
    ] 
  }
]);

// 分页相关
const pageNum = ref(1);
const pageSize = ref(10);
const total = ref(0);
// 五个页面各自的条目数量
const eachTotal = ref([0, 0, 0, 0, 0]);
const idList = ref([]);

const onSizeChange = (size) => {
  pageSize.value = size;
  bookPageList();
};

const onCurrentChange = (page) => {
  pageNum.value = page;
  bookPageList();
};

// 格式化时间
const formatDate = (dateString) => {
  const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' };
  return new Date(dateString).toLocaleString('zh-CN', options).replace(/\//g, '-');
}

// 根据当前标签页获取对应的数据
const bookPageList = async () => {
  // console.log('currentTab', currentTab.value);
  let params = {
    queryType: currentTab.value,
    pageNum: pageNum.value,
    pageSize: pageSize.value
  }
  let result = await bookPageListService(params);
  total.value = result.data.total;
  eachTotal.value = result.data.countStatus;
  currentContent.value = result.data.items;
  currentContent.value.forEach(item => {
    item.createTime = formatDate(item.createTime);
    item.startTime = formatDate(item.startTime);
    item.endTime = formatDate(item.endTime);
  });
}

// 修改审核状态
const updateStatus = async (row, status) => {
  let params = {
    id: row.id,
    status: status
  }
  let result = await updateStatusService(params);
}

const updateStatusBatch = async (idList,status) => {
  let params = {
    idList: idList,
    status: status
  }
  let result = await updateStatusBatchService(params);
}

// 点击通过或拒绝
const onClickButton = (row, type) => {
  // 如果状态不是 '待审核'，则不允许通过
  if (row.status !== '待审核') {
    ElMessage({type: 'warning', message: '该申请单已经审核过了'});
    return;
  }
  //提示用户  确认框
  if (type === '通过') {
    ElMessageBox.confirm(
      '你确认要通过该申请单吗？',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
    ).then(async () => {
      //调用接口
      await updateStatus(row, '已通过');
      ElMessage({type: 'success', message: '操作成功'});
      await bookPageList();
    })
    .catch(() => {
      ElMessage({type: 'info', message: '用户取消了操作'});
    })
  } else {
    ElMessageBox.confirm(
      '你确认要拒绝该申请单吗？',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
    ).then(async () => {
      //调用接口
      await updateStatus(row, '未通过');
      ElMessage({type: 'success', message: '操作成功'});
      await bookPageList();
    })
    .catch(() => {
      ElMessage({type: 'info', message: '用户取消了操作'});
    })
  }
}

//批量处理
const handleTableSelectionChange = (selectedRows) => {
  // 处理选中行的数据
  idList.value = selectedRows.map(row => row.id);
  console.log(selectedRows);
  console.log(idList.value);
}
const BatchPasses = async (idList) => {
  //判断是否有数据
  if (idList.length === 0) {
    ElMessage({type: 'warning', message: '请先选择要操作的数据'});
    return;
  }
  ElMessageBox.confirm(
      '你确认要通过这些申请单吗？（共' + idList.length + '条）',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    await updateStatusBatch(idList, '已通过');
    // 显示操作成功的消息
    ElMessage({type: 'success', message: '批量操作成功'});
    // 重新获取数据
    await bookPageList();
  })
  .catch(() => {
    ElMessage({type: 'info', message: '用户取消了操作'});
  })
}


const BatchFailure = async (idList) => {
  if (idList.length === 0) {
    ElMessage({type: 'warning', message: '请先选择要操作的数据'});
    return;
  }
  ElMessageBox.confirm(
      '你确认要拒绝这些申请单吗？（共' + idList.length + '条）',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    await updateStatusBatch(idList, '未通过');
    // 显示操作成功的消息
    ElMessage({type: 'success', message: '批量操作成功'});
    // 重新获取数据
    await bookPageList();
  })
  .catch(() => {
    ElMessage({type: 'info', message: '用户取消了操作'});
  })
}

bookPageList();

watch(currentTab, () => {
  pageNum.value = 1;
  // 仅当 currentTab 变化之后，调用 bookPageList，避免 currentTab 更新太慢导致数据错误
  bookPageList();
});

</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h1>设备预约审核</h1>
      </div>
    </template>

    <Tabs :tabs="tabs" v-model="currentTab" :total="total" :eachTotal="eachTotal">
      <Table :content="currentContent" :title.sync="title" @update:title="title = $event" :columns="columns" :show-selection-column="currentTab === '待审核'" @selection-change="handleTableSelectionChange"/>
    </Tabs>
    <el-row type="flex" justify="space-between" align="bottom">
      <el-col v-if="currentTab === '待审核'" :span="2">
        <el-button type="primary" size="small" @click="BatchPasses(idList)">
          批量通过
        </el-button>
      </el-col>
      <el-col v-if="currentTab === '待审核'" :span="2">
        <el-button type="primary" size="small" @click="BatchFailure(idList)">
          批量拒绝
        </el-button>
      </el-col>
      <el-col :span="currentTab === '待审核' ? 20 : 24">
        <Pager :pageNum.sync="pageNum" :pageSize.sync="pageSize" :total="total" :on-size-change="onSizeChange"
               :on-current-change="onCurrentChange"/>
      </el-col>
    </el-row>
  </el-card>


</template>


<style scoped lang="scss">
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}

.button-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>

