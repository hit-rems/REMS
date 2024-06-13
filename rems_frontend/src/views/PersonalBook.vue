<script>
import {ref} from "vue";

const tabs = [
  { name: '全部', label: '全部', total: 0 },
  { name: '待审核', label: '待审核', total: 0 },
  { name: '待使用', label: '待使用', total: 0 },
  { name: '已通过', label: '已通过', total: 0 },
  { name: '未通过', label: '未通过', total: 0 },
];

const currentTab = ref(tabs[0].name);

const total = ref(0);
// 五个页面各自的条目数量
const eachTotal = ref([0, 0, 0, 0, 0]);



</script>


<template>

</template>