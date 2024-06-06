<template>
  <div class="header">
    <span>科研设备分类</span>
    <div class="extra">
      <el-button type="primary" @click="onClick">添加分类</el-button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      required: true
    },
    title: {
      type: String,
      required: true
    },
    clearData: {
      type: Function,
      required: true
    }
  },
  methods: {
    onClick() {
      this.clearData();
      this.$emit('update:dialogVisible', true);
      this.$emit('update:title', '添加分类');
    }
  }
}
</script>

<style lang="scss" scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
}
</style>