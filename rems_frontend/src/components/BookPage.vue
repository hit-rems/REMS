<template>
  <el-row>
    <el-col>
      <img :src="equipmentModel.url" :alt="equipmentModel.name" />
    </el-col>
    <el-col>
      <el-descriptions :title="equipmentModel.name" :column="1" class="descriptions">
        <el-descriptions-item label="编号">{{ equipmentModel.id }}</el-descriptions-item>
        <el-descriptions-item label="类别">{{ equipmentModel.type }}</el-descriptions-item>
        <el-descriptions-item label="品牌">{{ equipmentModel.brand }}</el-descriptions-item>
        <el-descriptions-item label="所属单位">{{ equipmentModel.department }}</el-descriptions-item>
      </el-descriptions>
      <Tabs v-if="tabs" :tabs="tabs">
        <Table :content="currentContent" :title.sync="title" @update:title="title = $event" :columns="columns"/>
      </Tabs>
    </el-col>
  </el-row>
</template>

<script>
import { ref, watch } from 'vue';
import Tabs from '@/components/Tabs.vue'
import Table from '@/components/Table.vue'
export default {
  props: {
    equipmentModel: {
      type: Object,
      required: true
    },
    currentContent: {
      type: Array,
      required: true
    },
  }
}


const getFormattedDate = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const generateDateArray = () => {
  const dateArray = [];
  const today = new Date();

  for (let i = 0; i < 7; i++) {
    const currentDate = new Date(today);
    currentDate.setDate(today.getDate() + i);
    dateArray.push(getFormattedDate(currentDate));
  }
  return dateArray;
};

const dates = generateDateArray();

const currentContent = ref([{
  startTime: '',
  endTime: '',
  status: '',
}]);

const columns = ref([
  { label: '预约开始时间', prop: 'startTime', width: '120', align: 'center' },
  { label: '预约结束时间', prop: 'endTime', width: '120', align: 'center' },
  { label: '状态', prop: 'status', width: '100', align: 'center' },
]);

// Creating the tabs array with dates as name and label
const tabs = dates.map(date => ({ name: date, label: date }));

// const tabs = [
//   {name: '2024-06-07', label: '2024-06-07'},
//   {name: '2024-06-08', label: '2024-06-08'},
//   {name: '2024-06-09', label: '2024-06-09'},
//   {name: '2024-06-10', label: '2024-06-10'},
//   {name: '2024-06-11', label: '2024-06-11'},
//   {name: '2024-06-12', label: '2024-06-12'},
//   {name: '2024-06-13', label: '2024-06-13'}
// ]

</script>