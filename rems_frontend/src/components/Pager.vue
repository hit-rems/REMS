<template>
  <el-pagination current-page="pageNum" page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                 layout="jumper, total, sizes, prev, pager, next" background :total="total"
                 @size-change="onSizeChange"
                 @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end"/>
</template>

<script>
export default {
  props: {
    pageNum: {
      type: Number,
      default: 1
    },
    pageSize: {
      type: Number,
      default: 10
    },
    total: {
      type: Number,
      default: 0
    },
    onSizeChange: {
      type: Function,
      default: () => {
      }
    },
    onCurrentChange: {
      type: Function,
      default: () => {
      }
    }
  }
}
</script>