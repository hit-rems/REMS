<script setup>
import Tabs from '@/components/Tabs.vue';
import CategoryTable from "@/components/CategoryTable.vue";

// 假设您有以下标签页数据
const tabs = [
  { name: '1', label: '全部' },
  { name: '2', label: '待审核' },
  { name: '3', label: '待使用' },
  { name: '4', label: '已通过' },
  { name: '5', label: '未通过' },

];
</script>

<template>
  <Tabs :tabs="tabs" />
  <CategoryTable :categoriesThisPage="categoriesThisPage" :showDialog="showDialog" :deleteCategory="deleteCategory"
                 :title.sync="title" @update:title="title = $event" :columns="columns"/>
</template>

<style scoped lang="scss">

</style>