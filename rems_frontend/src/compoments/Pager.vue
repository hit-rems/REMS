<template>
  <el-pagination current-page="currentPage" page-size="currentPageSize" :page-sizes="[3, 5, 10, 15]"
                 layout="jumper, total, sizes, prev, pager, next" background :total="total"
                 @size-change="handleSizeChange"
                 @current-change="handleCurrentChange" style="margin-top: 20px; justify-content: flex-end"/>
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
      default: 5
    },
    total: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      currentPage: this.pageNum,
      currentPageSize: this.pageSize
    };
  },
  methods: {
    handleSizeChange(newSize) {
      this.currentPageSize = newSize;
      this.$emit('update:pageSize', newSize);
    },
    handleCurrentChange(newPageNum) {
      this.currentPage = newPageNum;
      this.$emit('update:pageNum', newPageNum);
    }
  },
  watch: {
    pageNum(newPageNum) {
      this.currentPage = newPageNum;
    },
    pageSize(newPageSize) {
      this.currentPageSize = newPageSize;
    }
  }
}
</script>