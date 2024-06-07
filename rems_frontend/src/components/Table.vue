<template>
  <el-table :data="content" @selection-change="handleSelectionChange" fit>
    <el-table-column v-if="showSelectionColumn" type="selection" ></el-table-column>
    <el-table-column v-for="(column, index) in columns" :key="index"
                     :label="column.label" :prop="column.prop" :width="column.width" :align="column.align" :type="column.type" :sortable="column.sortable">
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
    }
  },
  methods: {
    handleSelectionChange(selectedRows) {
      this.$emit('selection-change', selectedRows);
    }
  }
}
</script>