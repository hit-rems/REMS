<script>

export default {
  props: {
    tabs: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      activeName: this.tabs[0].name
    };
  },
  methods: {
    handleClick(tab, event) {
      this.activeName = tab.name;
      console.log(tab, event);
    }
  }
};
</script>

<template>
  <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
    <el-tab-pane v-for="tab in tabs" :key="tab.name" :label="tab.label" :name="tab.name">{{ tab.label }}</el-tab-pane>
  </el-tabs>
</template>

<style scoped lang="scss">

</style>