<script setup>
import Tabs from '@/components/Tabs.vue';

// 假设您有以下标签页数据
const tabs = [
  { name: 'Tab1', label: '标签页1' },
  { name: 'Tab2', label: '标签页2' },

];
</script>

<template>
  <Tabs :tabs="tabs" />
</template>

<style scoped lang="scss">

</style>