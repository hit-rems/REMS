<template>
  <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
    <el-tab-pane
        v-for="(tab, index) in tabs"
        :key="tab.name"
        :name="tab.name"
    >
      <template #label>
        <div>
          {{ tab.label }}
          <el-badge :value="eachTotal[index]" class="item"></el-badge>
        </div>
      </template>
      <!-- 插槽 -->
      <slot></slot>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
export default {
  props: {
    eachTotal: {
      type: Array,
      required: true
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

<style scoped lang="scss">
.item {
  margin-left: 5px; // 根据需要调整间距
}
</style>