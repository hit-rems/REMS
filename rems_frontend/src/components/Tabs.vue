<script>
export default {
  props: {
    total: Number,
    eachTotal:{
      type: Array,
    },
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
      console.log(tab.label, event);
    }
  }
};
</script>

<template>
  <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
    <el-tab-pane
      v-for="(tab, index) in tabs"
      :key="tab.name"
      :label="tab.label"
      :name="tab.name"
    >
      <template #label>
        {{ tab.label }}
        <el-badge :value="eachTotal[tab.label]" class="item" type="info" color="red"></el-badge>
      </template>
      <!-- 插槽 -->
      <slot></slot>
    </el-tab-pane>
  </el-tabs>
</template>

<style scoped lang="scss">

</style>