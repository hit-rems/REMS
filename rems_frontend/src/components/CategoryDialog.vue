<template>
  <el-dialog :model-value="dialogVisible" @update:model-value="$emit('update:dialogVisible', $event)" :title="title"
             width="30%">
    <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px"
             @submit.native.prevent="title === '添加分类' ? addCategory() : updateCategory()">
      <el-form-item label="分类名称" prop="name">
        <el-input v-model="categoryModel.name" minlength="1" maxlength="10"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="$emit('update:dialogVisible', false)">取消</el-button>
        <el-button type="primary"
                   @click="title === '添加分类' ? addCategory() : updateCategory()"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
export default {
  props: {
    dialogVisible: Boolean,
    title: String,
    categoryModel: Object,
    rules: Object,
    addCategory: Function,
    updateCategory: Function
  }
}
</script>