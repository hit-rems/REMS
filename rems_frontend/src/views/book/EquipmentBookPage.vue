<script setup>
import { ref, watch } from 'vue';
import { useRoute } from 'vue-router'
// import BookPage from "@/components/BookPage.vue";

import {equipmentQueryService} from '@/api/equipment.js'
import {bookQueryService, bookAddService} from '@/api/book.js'
import {ElMessage, ElMessageBox} from "element-plus";

// 从路由接收参数
const route = useRoute()
let id = parseInt(route.params.id);
// let id = 2;

const equipmentModel = ref({
  id: 0,
  type: '',
  name: '',
  department: '',
  discard: '',
  brand: '',
  createTime: '',
  updateTime: '',
  file: null,
  url: '',
})

const currentContent = ref([
  { startTime: '00:00', endTime: '04:00', status: ''},
  { startTime: '04:00', endTime: '08:00', status: ''},
  { startTime: '08:00', endTime: '12:00', status: ''},
  { startTime: '12:00', endTime: '16:00', status: ''},
  { startTime: '16:00', endTime: '20:00', status: ''},
  { startTime: '20:00', endTime: '24:00', status: ''}
]);

const columns = ref([
  { label: '预约开始时间', prop: 'startTime', width: '120', align: 'center' },
  { label: '预约结束时间', prop: 'endTime', width: '120', align: 'center' },
  { label: '状态', prop: 'status', width: '100', align: 'center'},
]);


// 距离当前日期的天数 0 - 6
let dateCount = 0;
const title = ref('');

// 获取指定id的设备信息
const getEquipmentInfo = async () => {
  // id = 1;
  let result = await equipmentQueryService(id);
  equipmentModel.value = result.data;
  // console.log(equipmentModel.value);
}

// 获取指定设备、指定日期的预约情况
const getEquipmentBookStatus = async () => {
  const params = {
    id: id,
    time: dateCount
  };
  let result = await bookQueryService(params);
  for(let i = 0; i<6; i++){
    if(result.data[i]){
      currentContent.value[i].status = '不可约';
    } else {
      currentContent.value[i].status = '可预约';
    }
  }
  // console.log(result.data);
  // console.log(currentContent.value);
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

// Helper function to parse date string and return Date object
const parseDateString = (dateString) => {
  // console.log(typeof dateString)
  const [year, month, day] = dateString.split('-').map(Number);
  return new Date(year, month - 1, day); // month is 0-indexed in Date
};

// Function to calculate difference in days between given date and current date
const calculateDateDifference = (dateString) => {
  const givenDate = parseDateString(dateString);
  const currentDate = new Date();
  // Clear time part for accurate difference calculation
  givenDate.setHours(0, 0, 0, 0);
  currentDate.setHours(0, 0, 0, 0);
  const differenceInMillis = givenDate - currentDate;
  const differenceInDays = differenceInMillis / (1000 * 60 * 60 * 24);
  return differenceInDays;
};

const dates = generateDateArray();
// Creating the tabs array with dates as name and label
const tabs = dates.map(date => ({ name: date, label: date }));
// console.log(tabs)
const currentTab = ref(tabs[0].name);

getEquipmentInfo();
getEquipmentBookStatus();

// 监视Tab的变化，并实时更新dateCount
watch(currentTab, () => {
  dateCount = calculateDateDifference(currentTab.value);
  // console.log(dateCount);
  getEquipmentBookStatus();
});


const timeMapping = {
  "00:00": 0,
  "04:00": 1,
  "08:00": 2,
  "12:00": 3,
  "16:00": 4,
  "20:00": 5
};

let selectedTimeCount = 0;
let bookArray = new Array(6);

// 处理选中的行的预约
const handleTableSelectionChange = (selectedRows) => {
  selectedTimeCount = 0;
  bookArray.fill(false);
  selectedRows.forEach(row => {
    bookArray[timeMapping[row.startTime]] = true;
    selectedTimeCount++;
  })
  // console.log("bookArray:");
  // console.log(bookArray);
}

// 绑定预约按钮的函数
const bookAdd = async () => {
  const params = ref({
    id: id,
    day: dateCount,
    timelist: bookArray,
    reason: '',
  })
  console.log(params.value);

  ElMessageBox.confirm(
      '请确认预约（共' + selectedTimeCount + '个时间段）',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    await bookAddService(params.value);
    ElMessage({type: 'success', message: '预约成功'});
    getEquipmentBookStatus();
  })
    .catch(() => {
      ElMessage({type: 'info', message: '用户取消了操作'});
    })
}


const tableRowClassName = (row) => {
  // console.log(row.row.status);
  if (row.row.status === '不可约') {
    return 'warning-row'
  } else {
    return 'success-row'
  }
  return ''
}

const isRowSelectable = (row) => {
  return row.status === '可预约';
}


</script>

<template>
  <el-row>
    <el-col :span="12">
      <img :src="equipmentModel.url" :alt="equipmentModel.name" height="80%" width="80%"/>
    </el-col>
    <el-col :span="2"></el-col>
    <el-col :span="10">
      <el-descriptions :title="equipmentModel.name" :column="1" class="descriptions">
        <el-descriptions-item label="编号">{{ equipmentModel.id }}</el-descriptions-item>
        <el-descriptions-item label="类别">{{ equipmentModel.type }}</el-descriptions-item>
        <el-descriptions-item label="品牌">{{ equipmentModel.brand }}</el-descriptions-item>
        <el-descriptions-item label="所属单位">{{ equipmentModel.department }}</el-descriptions-item>
      </el-descriptions>

    </el-col>
  </el-row>
  <el-row>
    <div class="tabs-container">
      <Tabs v-model="currentTab" :tabs="tabs">
        <div class="table-container" align="center">
          <Table :content="currentContent" :title.sync="title" @update:title="title = $event"
                 :columns="columns" :showSelectionColumn="true" @selection-change="handleTableSelectionChange"
                 :row-class-name="tableRowClassName"
                 :isRowSelectable="isRowSelectable"
          />
        </div>
      </Tabs>
    </div>
  </el-row>
    <el-button type="primary" @click="bookAdd()">预约</el-button>


</template>

<style scoped>
.tabs-container {
  width: 100%;
  display: flex;
  justify-content: center;
  margin-bottom: 20px; /* Adjust the spacing between Tabs and Table */
}

.table-container {
  width: 100%;
  display: flex;
  justify-content: center;
}

</style>