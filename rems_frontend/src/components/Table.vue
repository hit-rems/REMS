<template>
  <el-table :data="content" style="width: 100%">
    <!-- <el-table-column type="selection"></el-table-column> -->
    <el-table-column v-for="(column, index) in columns" :key="index"
                     :label="column.label" :prop="column.prop" :width="column.width" :align="column.align" :type="column.type">
      <template v-if="Array.isArray(column.slot)" #default="{ row }">
        <el-button v-for="(slot, slotIndex) in column.slot" :key="slotIndex"
                   :icon="slot.icon" circle plain :type="slot.type"
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
    }
  }
}
</script>