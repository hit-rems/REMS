<script setup>
import {
  Edit,
  Delete
} from '@element-plus/icons-vue'
import {reactive, ref} from 'vue'
import {computed} from 'vue'
import EquipmentCategoryPieChart from './EquipmentCategoryPieChart.vue'

const categories = ref([])
//声明一个异步的函数
import {
  categoryListService,
  categoryAddService,
  categoryUpdateService,
  categoryDeleteService
} from '@/api/category.js'

const categoryList = async () => {
  let result = await categoryListService();
  categories.value = result.data;
}

const categoryPagelist = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value
  }
  let result = await categoryPageListService(params);
  categories.value = result.data;
}

categoryList();
//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const categoryModel = ref({
  name: '',
  num: '',
})

//饼图数据
const chartData = computed(() => {
  return categories.value.map(category => ({
    name: category.name,
    value: category.num
  }));
});

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  categoryList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  categoryList()
}

//添加分类表单校验
const rules = {
  name: [
    {required: true, message: '请输入分类名称', trigger: 'blur'},
  ],
}

//调用接口,添加表单
import {ElMessage} from 'element-plus'

const addCategory = async () => {
  // 当 categoryModel.value.name 为空时，提示用户
  if (!categoryModel.value.name) {
    ElMessage.error('请输入分类名称')
    return
  }
  //调用接口
  let result = await categoryAddService(categoryModel.value.name);
  ElMessage.success(result.msg ? result.msg : '添加成功')

  //调用获取所有科研设备分类的函数
  categoryList();
  dialogVisible.value = false;
}

//定义变量,控制标题的展示
const title = ref('')

//展示编辑弹窗
const showDialog = (row) => {
  dialogVisible.value = true;
  //数据拷贝
  categoryModel.value.name = row.name;
  //扩展id属性,将来需要传递给后台,完成分类的修改
  categoryModel.value.id = row.id
}

//编辑分类
const updateCategory = async () => {
  //调用接口
  let result = await categoryUpdateService(categoryModel.value);
  ElMessage.success('修改成功')

  //调用获取所有分类的函数
  categoryList();
  //隐藏弹窗
  dialogVisible.value = false;
}

//清空模型的数据
const clearData = () => {
  categoryModel.value.name = '';
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
        let result = await categoryDeleteService(row.id);
        ElMessage({
          type: 'success',
          message: '删除成功',
        })
        //刷新列表
        categoryList();
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
    <el-table :data="categories" style="width: 100%">
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

    <!-- 分页条 -->
    <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
                   layout="jumper, total, sizes, prev, pager, next" background :total="total"
                   @size-change="onSizeChange"
                   @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end"/>

    <!-- 添加分类弹窗 -->
    <el-dialog v-model="dialogVisible" :title="title" width="30%">
      <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="categoryModel.name" minlength="1" maxlength="10"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary"
                               @click="title === '添加分类' ? addCategory() : updateCategory()"> 确认 </el-button>
                </span>
      </template>
    </el-dialog>
    <!-- 饼图容器 -->
    <div style="display: flex; justify-content: center; align-items: center; padding-top: 20px;">
      <EquipmentCategoryPieChart :chartData="chartData" />
    </div>
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