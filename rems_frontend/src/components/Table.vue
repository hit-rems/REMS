<template>
  <el-table :data="content" @selection-change="handleSelectionChange"
            :row-class-name="tableRowClassName"
            fit="true"
            ref="childTable"
            style="width: 100%">
    <el-table-column v-if="showSelectionColumn"
                     :selectable="isRowSelectable" type="selection" ></el-table-column>
    <el-table-column v-for="(column, index) in columns" :key="index"
                     :label="column.label" :prop="column.prop" v-bind="{ width: column.width || undefined }" :align="column.align" :type="column.type" :sortable="column.sortable">
      <template v-if="Array.isArray(column.slot)" #default="{ row }">
        <el-button v-for="(slot, slotIndex) in column.slot" :key="slotIndex"
                   :icon="slot.icon" circle plain :type="typeof slot.type === 'function' ? slot.type(row) : slot.type"
                   :class="'full-height-button'"
                   @click="slot.action(row)"></el-button>
      </template>
    </el-table-column>
    <template #empty>
      <el-empty description="没有数据"/>
    </template>
  </el-table>
</template>

<script>
export default {
  props: {
    content: Array,
    title: String,
    columns: {
      type: Array,
      required: true
    },
    showSelectionColumn: {
      type: Boolean,
      default: false // 设置默认值为false
    },
    tableRowClassName: {
      type: Function
    },
    isRowSelectable: {
      type: Function
    }
  },
  methods: {
    handleSelectionChange(selectedRows) {
      this.$emit('selection-change', selectedRows);
    },
    handleClearSelection() {
      if (this.$refs.childTable) {
        this.$refs.childTable.clearSelection();
      }
    },
  }
}
</script>

<script setup>
// import {ref} from 'vue';
// import {getCurrentInstance} from 'vue';
// import {toRefs} from '@vue/reactivity';
//
// // const instance = getCurrentInstance();
// const childTable = ref(null);
// const row = ref(null);
//
// const clear = () => {
//   console.log("i am in");
  // var childTableInstance = toRefs(instance.refs.childTable);
  // childTableInstance.clearSelection.value();
  // childTable.value.clearSelection.value();
  // childTable.value.clearSelection();
  // childTable.value.toggleAllSelection(row, false);
// };
//
// defineExpose({
//   clear
// });
</script>

<style>
  .el-table .warning-row {
    --el-table-tr-bg-color: var(--el-color-danger-light-5);
  }
  .el-table .success-row {
    --el-table-tr-bg-color: var(--el-color-success-light-5);
  }
</style>