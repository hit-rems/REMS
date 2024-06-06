<template>
  <el-table :data="categoriesThisPage" style="width: 100%">
    <el-table-column label="序号" width="100" type="index" align="center"></el-table-column>
    <el-table-column label="分类名称" prop="name" align="center"></el-table-column>
    <el-table-column label="数量" prop="num" align="center"></el-table-column>
    <el-table-column label="操作" width="100" align="center">
      <template #default="{ row }">
        <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row); title = '编辑分类'"></el-button>
        <el-button :icon="Delete" circle plain type="danger" @click="deleteCategory(row)"></el-button>
      </template>
    </el-table-column>
    <template #empty>
      <el-empty description="没有数据"/>
    </template>
  </el-table>
</template>

<script>
import {Edit, Delete} from '@element-plus/icons-vue'

export default {
  computed: {
    Delete() {
      return Delete
    },
    Edit() {
      return Edit
    }
  },
  props: {
    categoriesThisPage: Array,
    showDialog: Function,
    deleteCategory: Function
  },
  components: {
    Edit,
    Delete
  }
}
</script>