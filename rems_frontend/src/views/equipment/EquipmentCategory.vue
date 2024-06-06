<script setup>
import {ref} from 'vue'
import EquipmentCategoryPieChart from '@/components/EquipmentCategoryPieChart.vue'
import Pager from '@/components/Pager.vue'
import CategoryHeader from '@/components/CategoryHeader.vue'
import CategoryTable from '@/components/CategoryTable.vue'
import CategoryDialog from "@/components/CategoryDialog.vue";

//声明一个异步的函数
import {
  categoryListService,
  categoryAddService,
  categoryUpdateService,
  categoryDeleteService,
  categoryPageListService,
} from '@/api/category.js'

import {
  Edit,
  Delete
} from '@element-plus/icons-vue'

//饼图数据
const chartData = ref([])
//获取所有分类
const categories = ref([{
  name: '',
  num: '',
}])
const categoryList = async () => {
  let result = await categoryListService();
  categories.value = result.data;

  chartData.value = categories.value.map(category => ({
    name: category.name,
    value: category.num
  }));
}

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//查询当前页面的所有分类
const categoriesThisPage = ref([])
const categoryPageList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value
  }
  let result = await categoryPageListService(params);
  total.value = result.data.total;
  categoriesThisPage.value = result.data.items;
}

categoryList();  //刷新所有分类列表
categoryPageList();  //刷新当前页面的分类列表

//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const categoryModel = ref({
  name: '',
  num: '',
})

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  categoryPageList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  categoryPageList()
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
  await categoryAddService(categoryModel.value.name);
  ElMessage.success('添加成功')

  await categoryList();  //刷新所有分类列表
  await categoryPageList();  //刷新当前页面的分类列表
  dialogVisible.value = false;
}

//定义变量,控制标题的展示
const title = ref('')

//展示编辑弹窗
const oldName = ref('')
const showDialog = (row) => {
  dialogVisible.value = true;
  //数据拷贝
  categoryModel.value.name = row.name;
  oldName.value = row.name
  //扩展id属性,将来需要传递给后台,完成分类的修改
  categoryModel.value.id = row.id
}

//编辑分类
const updateCategory = async () => {
  //调用接口
  await categoryUpdateService(oldName.value, categoryModel.value.name);
  ElMessage.success('修改成功')

  await categoryList();  //刷新所有分类列表
  await categoryPageList();  //刷新当前页面的分类列表
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
  //如果 row.num > 0,提示用户,不允许删除
  if (row.num > 0) {
    ElMessage.error('该分类下有设备，不允许删除')
    return
  }

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
        await categoryDeleteService(row.name);
        ElMessage({type: 'success', message: '删除成功'})
        //刷新列表
        await categoryList();
        await categoryPageList();
      })
      .catch(() => {
        ElMessage({type: 'info', message: '用户取消了删除'})
      })
}

const onEdit = (row) => {
  showDialog(row)
  title.value = '编辑分类'
}

const onDelete = (row) => {
  deleteCategory(row)
}

const columns = [
  {label: '序号', width: '100', type: 'index', align: 'center'},
  {label: '分类名称', prop: 'name', align: 'center'},
  {label: '数量', prop: 'num', align: 'center'},
  {
    label: '操作',
    width: '100',
    align: 'center',
    slot: [
      {
        icon: Edit,
        type: 'primary',
        action: onEdit,
      },
      {
        icon: Delete,
        type: 'danger',
        action: onDelete,
      }
    ]
  },
]


</script>
<template>
  <el-card class="page-container">

    <CategoryHeader :dialogVisible.sync="dialogVisible" :title.sync="title" :clearData="clearData"
                    @update:dialogVisible="dialogVisible = $event"
                    @update:title="title = $event"/>

    <CategoryTable :categoriesThisPage="categoriesThisPage" :showDialog="showDialog" :deleteCategory="deleteCategory"
                   :title.sync="title" @update:title="title = $event" :columns="columns"/>

    <!-- 分页条 -->
    <Pager :pageNum.sync="pageNum" :pageSize.sync="pageSize" :total="total" :on-size-change="onSizeChange"
           :on-current-change="onCurrentChange"/>

    <!-- 添加分类弹窗 -->
    <CategoryDialog :dialogVisible.sync="dialogVisible" :title="title" :categoryModel.sync="categoryModel"
                    :rules="rules" :addCategory="addCategory" :updateCategory="updateCategory"
                    @update:dialogVisible="dialogVisible = $event"/>

    <!-- 饼图容器 -->
    <div style="display: flex; justify-content: center; align-items: center;">
      <EquipmentCategoryPieChart :chartData="chartData"/>
    </div>

  </el-card>
</template>

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;
}
</style>