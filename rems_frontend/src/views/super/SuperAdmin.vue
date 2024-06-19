<script setup>
import { ref, watch } from 'vue';
import Tabs from '@/components/Tabs.vue';
import Table from "@/components/Table.vue";
import Pager from '@/components/Pager.vue';
import { superPageListService, superUpdateService } from '@/api/super';
import { Select, CloseBold } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus';

// 标签页
const tabs = [
  { name: '全部', label: '全部', total: 0 },
  { name: '待审核', label: '待审核', total: 0 },
  { name: '已通过', label: '已通过', total: 0 },
  { name: '未通过', label: '未通过', total: 0 },
];

const currentTab = ref(tabs[0].name);

const currentContent = ref([{
  id: '',
  username: '',
  nickname: '',
  department: '',
  email: '',
  status: '',
  createTime: '',
}]);

const title = ref('');
const columns = ref([
  { label: '申请单号', prop: 'id', align: 'center', sortable: true},
  { label: '用户名', prop: 'username', align: 'center' },
  { label: '昵称', prop: 'nickname', align: 'center' },
  { label: '部门', prop: 'department', align: 'center' },
  { label: '邮箱', prop: 'email', align: 'center' },
  { label: '审核状态', prop: 'status', align: 'center' },
  { label: '申请时间', prop: 'createTime', align: 'center', sortable: true},
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
  superPageList();
};

const onCurrentChange = (page) => {
  pageNum.value = page;
  superPageList();
};

// 格式化时间
const formatDate = (dateString) => {
  const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' };
  return new Date(dateString).toLocaleString('zh-CN', options).replace(/\//g, '-');
}

// 根据当前标签页获取对应的数据
const superPageList = async () => {
  let params = {
    queryType: currentTab.value,
    pageNum: pageNum.value,
    pageSize: pageSize.value
  }
  let result = await superPageListService(params);
  total.value = result.data.total;
  eachTotal.value = result.data.countStatus;
  currentContent.value = result.data.items;
  currentContent.value.forEach(item => {
    item.createTime = formatDate(item.createTime);
  });
}

// 修改审核状态
const updateStatus = async (row, status) => {
  let params = {
    id: row.id,
    status: status
  }
  let result = await superUpdateService(params);
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
      await superPageList();
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
      await superPageList();
    })
    .catch(() => {
      ElMessage({type: 'info', message: '用户取消了操作'});
    })
  }
}

superPageList();

watch(currentTab, () => {
  pageNum.value = 1;
  // 仅当 currentTab 变化之后，调用 superPageList，避免 currentTab 更新太慢导致数据错误
  superPageList();
});

</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h1>用户管理</h1>
      </div>
    </template>

    <Tabs :tabs="tabs" v-model="currentTab" :total="total" :eachTotal="eachTotal">
      <Table :content="currentContent" :title.sync="title" @update:title="title = $event" :columns="columns" :show-selection-column="currentTab === '待审核'" @selection-change="handleTableSelectionChange"/>
    </Tabs>
    <el-row type="flex" justify="space-between" align="bottom">
      <el-col>
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

