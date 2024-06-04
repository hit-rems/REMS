<script setup>
import {
  Edit,
  Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
const categorys = ref([
  {
    "id": 1,
    "categoryName": "美食",
    "createTime": "2023-09-02 12:06:59",
    "updateTime": "2023-09-02 12:06:59"
  },
  {
    "id": 2,
    "categoryName": "娱乐",
    "createTime": "2023-09-02 12:08:16",
    "updateTime": "2023-09-02 12:08:16"
  },
])
//声明一个异步的函数
import { equipmentCategoryListService, equipmentCategoryAddService, equipmentCategoryUpdateService,equipmentCategoryDeleteService } from '@/api/equipment.js'
const equipmentCategoryList = async () => {
  let result = await equipmentCategoryListService();
  categorys.value = result.data;
}

equipmentCategoryList();
//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const categoryModel = ref({
  categoryName: '',
})

//添加分类表单校验
const rules = {
  categoryName: [
    { required: true, message: '请输入分类名称', trigger: 'blur' },
  ],
}

//调用接口,添加表单
import { ElMessage } from 'element-plus'
const addCategory = async () => {
  //调用接口
  let result = await equipmentCategoryAddService(categoryModel.value);
  ElMessage.success(result.msg ? result.msg : '添加成功')

  //调用获取所有科研设备分类的函数
  equipmentCategoryList();
  dialogVisible.value = false;
}

//定义变量,控制标题的展示
const title = ref('')

//展示编辑弹窗
const showDialog = (row) => {
  dialogVisible.value = true; title.value = '编辑分类'
  //数据拷贝
  categoryModel.value.categoryName = row.categoryName;
  //扩展id属性,将来需要传递给后台,完成分类的修改
  categoryModel.value.id = row.id
}

//编辑分类
const updateCategory = async () => {
  //调用接口
  let result = await equipmentCategoryUpdateService(categoryModel.value);
  ElMessage.success('修改成功')

  //调用获取所有分类的函数
  equipmentCategoryList();
  //隐藏弹窗
  dialogVisible.value = false;
}

//清空模型的数据
const clearData = () => {
  categoryModel.value.categoryName = '';
}

//删除分类
import {ElMessageBox} from 'element-plus'
const deleteCategory = (row) => {
  //提示用户  确认框

  ElMessageBox.confirm(
      '你确认要删除该分类信息吗?',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(async () => {
        //调用接口
        let result = await equipmentCategoryDeleteService(row.id);
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
        //刷新列表
        equipmentCategoryList();
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '用户取消了删除',
        })
      })
}
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <span>科研设备分类</span>
        <div class="extra">
          <el-button type="primary" @click="dialogVisible = true; title = '添加分类'; clearData()">添加分类</el-button>
        </div>
      </div>
    </template>
    <el-table :data="categorys" style="width: 100%">
      <el-table-column label="序号" width="100" type="index"> </el-table-column>
      <el-table-column label="分类名称" prop="categoryName"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
          <el-button :icon="Delete" circle plain type="danger" @click="deleteCategory(row)"></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

    <!-- 添加分类弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="10"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="title == '添加分类' ? addCategory() : updateCategory()"> 确认 </el-button>
                </span>
      </template>
    </el-dialog>
  </el-card>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>