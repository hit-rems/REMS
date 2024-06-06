<script setup>
import { ref, watch } from 'vue';
import Tabs from '@/components/Tabs.vue';
import Table from "@/components/Table.vue";
import Pager from '@/components/Pager.vue';
import { bookPageListService } from '@/api/audit';
import { Edit, Delete } from '@element-plus/icons-vue'

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
  { label: '申请单号', prop: 'id', width: '100', align: 'center' },
  { label: '设备编号', prop: 'equipmentId', width: '100', align: 'center' },
  { label: '设备名称', prop: 'equipmentName', width: '200', align: 'center' },
  { label: '申请人', prop: 'name', width: '120', align: 'center' },
  { label: '申请时间', prop: 'createTime', width: '120', align: 'center' },
  { label: '预约开始时间', prop: 'startTime', width: '120', align: 'center' },
  { label: '预约结束时间', prop: 'endTime', width: '120', align: 'center' },
  { label: '用途', prop: 'reason', width: '120', align: 'center' },
  { label: '审核状态', prop: 'status', width: '120', align: 'center' },
  { label: '操作', prop: 'action', width: '120', align: 'center', slot: [
    { icon: Edit, type: 'primary', action: row => console.log(row) },
    { icon: Delete, type: 'danger', action: row => console.log(row) }
  ] }
]);

// 分页相关
const pageNum = ref(1);
const pageSize = ref(10);
const total = ref(0);
// 五个页面各自的条目数量
const eachTotal = ref([0, 0, 0, 0, 0]);

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
  eachTotal.value = result.data.eachTotal;
  currentContent.value = result.data.items;
  currentContent.value.forEach(item => {
    item.createTime = formatDate(item.createTime);
    item.startTime = formatDate(item.startTime);
    item.endTime = formatDate(item.endTime);
  });
}

bookPageList();

watch(currentTab, () => {
  // 仅当 currentTab 变化之后，调用 bookPageList，避免 currentTab 更新太慢导致数据错误
  bookPageList();
});
</script>

<template>
  <Tabs :tabs="tabs" v-model="currentTab" :total="total">
    <Table :content="currentContent" :title.sync="title" @update:title="title = $event" :columns="columns"/>
  </Tabs>
  <Pager :pageNum.sync="pageNum" :pageSize.sync="pageSize" :total="total" :on-size-change="onSizeChange"
           :on-current-change="onCurrentChange"/>
</template>

<style scoped lang="scss">

</style>