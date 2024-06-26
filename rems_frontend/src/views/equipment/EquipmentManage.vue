<script setup>
import {
  Edit,
  Delete,
  Plus
} from '@element-plus/icons-vue'

import {ref} from 'vue'
import Table from '@/components/Table.vue'
import Pager from '@/components/Pager.vue'
import EquipmentDialog from '@/components/EquipmentDialog.vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {
  equipmentCategoryListService,
  equipmentListService,
  equipmentAddService,
  equipmentBatchAddService,
  equipmentDeleteService,
  equipmentUpdateService
} from '@/api/equipment.js'


import useUserInfoStore from "@/stores/userInfo.js";
const userInfoStore = useUserInfoStore();
// 当前管理员所属的单位
const adminDepartment = userInfoStore.info.department;
// 管理员 / 超级管理员
const adminType = userInfoStore.info.type;
const isSuperAdmin = (adminType === '超级管理员');
console.log('isSuperAdmin = ', isSuperAdmin);

//设备分类数据模型
const categories = ref([])

//用户搜索时下拉框选中的设备类型和设备状态
const type = ref('')
const discard = ref('')

//分页查询设备列表的数据模型
const equipments = ref([
  {},
])

//添加表单数据模型
const equipmentModel = ref({
  id: 0,
  type: '',
  name: '',
  department: adminDepartment,
  discard: '',
  brand: '',
  createTime: '',
  updateTime: '',
  file: null,
  url: '',
})

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(10)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size
  equipmentPageList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num
  equipmentPageList()
}


//回显科研设备分类
const equipmentCategoryList = async () => {
  let result = await equipmentCategoryListService();
  categories.value = result.data;
}

//获取科研设备列表数据
const equipmentPageList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    type: type.value ? type.value : null,
    discard: discard.value ? discard.value : null,
    department: isSuperAdmin ? null : adminDepartment
  }

  let result = await equipmentListService(params);
  
  //渲染视图
  total.value = result.data.total;
  equipments.value = result.data.items.map(item => {
    // 假设 'updateTime' 是需要格式化的字段
    if (item.updateTime) {
      item.updateTime = formatDate(item.updateTime);
    }
    return item;
  });

}

equipmentCategoryList()
equipmentPageList();

// import { QuillEditor } from '@vueup/vue-quill'
// import '@vueup/vue-quill/dist/vue-quill.snow.css'
//控制抽屉是否显示
const visibleDrawer = ref(false)

const addRules = {
  id: [
    {required: true, message: '请输入设备号', trigger: 'blur'},
    {type: 'number', message: '设备号必须为整数', trigger: 'blur', transform: value => Number(value)}
  ],
  name: [
    {required: true, message: '请输入设备名称', trigger: 'blur'}
  ],
  type: [
    {required: true, message: '请选择设备类型', trigger: 'change'}
  ],
  brand: [
    {required: true, message: '请输入设备品牌', trigger: 'blur'}
  ],
  department: [
    {required: true, message: '请选择所属单位', trigger: 'change'}
  ],
  discard: [
    {required: true, message: '请选择设备状态', trigger: 'change'}
  ],
  file: [
    {required: true, message: '请上传图片'}
  ],
}

const title = ref('')

//导入token
import {useTokenStore} from '@/stores/token.js';
const tokenStore = useTokenStore();


// 上传图片的事件
const imageData = ref(null)
const onFileChange = (e) => {
  const file = e.target.files[0]; // 获取图片资源
  equipmentModel.value.file = file
  console.log("equipmentModel.file")
  console.log(equipmentModel.value.file)
  // 只选择图片文件
  if (!file.type.match('image.*')) {
    return false;
  }
  const reader = new FileReader();
  reader.readAsDataURL(file); // 读取文件
  // 渲染文件
  reader.onload = (arg) => {
    imageData.value = arg.target.result; // 将Data URL存储在imageData中
    console.log("imageData")
    console.log(imageData.value)
  };
}

const imgInput = ref(null);
// 点击按钮触发input type=file的事件
const triggerFileInput = () => {
  imgInput.value.click();
}


//添加设备
const addEquipmentForm = ref(null)
const addEquipment = async () => {
  const form = addEquipmentForm.value
  form.validate(async (valid) => {
    console.log("valid:", valid);
    if (valid) {
      if (equipmentModel.value.file) {
        const formData = new FormData()
        // 遍历 equipmentModel.value 并添加到 formData 中
        for (const key in equipmentModel.value) {
          if (key === 'file' && equipmentModel.value.file) {
            formData.append('file', equipmentModel.value.file)
          } else {
            console.log(key, equipmentModel.value[key]);
            formData.append(key, equipmentModel.value[key]);
          }
        }

        // 调用接口
        // let result = await equipmentAddService(equipmentModel.value);
        let result = await equipmentAddService(formData);
        ElMessage.success('添加成功');
        // 让抽屉消失
        visibleDrawer.value = false;
        // 刷新当前列表
        equipmentPageList();
        //清除添加设备页面的原数据
        clearEquipmentModel();
      } else {
        ElMessage.error('未检测到图片')
      }
    } else {
      ElMessage.error('添加设备失败，请检查输入项');
    }
  })
}

// 通过图片批量上传设备
const batchAddEquipment = async (files) => {
  // 如果没有选择文件
  if (!Array.isArray(files)) {
    ElMessage.error('文件列表无效');
    return;
  } else if (files.length === 0) {
    ElMessage.error('尚未选择文件');
    return;
  }
  // 调用接口
  let result = await equipmentBatchAddService(files);
  ElMessage.success('批量添加成功');
  // 刷新当前列表
  equipmentPageList();
  // 关闭对话框
  dialogVisible.value = false;
}


//展示编辑弹窗
const showDrawer = (row) => {
  visibleDrawer.value = true;
  //数据拷贝
  equipmentModel.value.id = row.id;
  equipmentModel.value.name = row.name;
  equipmentModel.value.type = row.type;
  equipmentModel.value.brand = row.brand;
  equipmentModel.value.department = row.department;
  // equipmentModel.value.department = adminDepartment;
  equipmentModel.value.discard = row.discard;
  equipmentModel.value.url = row.url
  // equipmentModel.value.file = row.file
}


const updateEquipment = async () => {
  let result = await equipmentUpdateService(equipmentModel.value);
  ElMessage.success('修改成功')
  equipmentPageList();
  visibleDrawer.value = false;
}

const deleteEquipment = (row) => {
  //提示用户  确认框
  ElMessageBox.confirm(
      '你确认要删除该设备吗?',
      '温馨提示',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
      }
  )
      .then(async () => {
        //调用接口
        console.log(row.id)
        let result = await equipmentDeleteService(row.id);
        ElMessage({type: 'success', message: '删除成功'})
        //刷新设备列表
        equipmentPageList();
      })
      .catch(() => {
        ElMessage({
          type: 'info', message: '用户取消了删除',
        })
      })
}


const clearEquipmentModel = () => {
  equipmentModel.value = {
    id: 0,
    type: '',
    name: '',
    department: adminDepartment,
    discard: '',
    brand: '',
    createTime: '',
    updateTime: '',
    file: null,
    url: '',
  }
  imageData.value = null;
}

// 定义点击编辑和删除按钮的回调函数
const onEdit = (row) => {
  showDrawer(row)
  title.value = '编辑设备'
}
const onDelete = (row) => {
  deleteEquipment(row)
}

// 格式化时间
const formatDate = (dateString) => {
  const options = { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit' };
  return new Date(dateString).toLocaleString('zh-CN', options).replace(/\//g, '-');
}

const columns = [
  {label: '设备号', width: '80', prop: 'id', align: 'center'},
  {label: '设备名称', width: '250', prop: 'name', align: 'center'},
  {label: '设备类型', width: '150', prop: 'type', align: 'center'},
  {label: '品牌', width: '250', prop: 'brand', align: 'center'},
  {label: '所属单位', prop: 'department', align: 'center'},
  {label: '状态', width: '80', prop: 'discard', align: 'center'},
  {label: '维护时间', width: '200', prop: 'updateTime', align: 'center', type: 'time'},
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

const dialogVisible = ref(false)
const showDialog = () => {
  dialogVisible.value = true;
  title.value = '批量添加设备';
}

</script>


<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h1>科研设备管理</h1>
        <div class="extra">
          <!--                    <el-button type="primary" @click="visibleDrawer = true">添加设备</el-button>-->
          <el-button type="primary" @click="showDialog();">批量添加设备
          </el-button>
          <el-button type="primary" @click="visibleDrawer = true; title='添加设备'; clearEquipmentModel()">添加设备
          </el-button>
        </div>
      </div>
    </template>

    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="设备类型：">
        <el-select placeholder="请选择" v-model="type" style="width: 120px;">
          <el-option v-for="c in categories" :key="c.name" :label="c.name" :value="c.name">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="设备状态：">
        <el-select placeholder="请选择" v-model="discard" style="width: 100px;">
          <el-option label="正常" value="正常"></el-option>
          <el-option label="报废" value="报废"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="equipmentPageList">搜索</el-button>
        <el-button @click="type = ''; discard = ''">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 设备列表 -->
    <Table :columns="columns" :data="equipments"/>

    <!-- 分页条 -->
    <Pager :pageNum.sync="pageNum" :pageSize.sync="pageSize" :total="total" :on-size-change="onSizeChange"
           :on-current-change="onCurrentChange"/>

    <!-- 抽屉 -->
    <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="30%">
      <!-- 添加设备表单 -->
      <el-form ref="addEquipmentForm" :model="equipmentModel" :rules="addRules" label-width="100px">
        <el-form-item label="设备号" prop="id">
          <el-input v-if="title ==='添加设备'" v-model="equipmentModel.id" type="number" placeholder="请输入设备号"></el-input>
          <el-input v-else v-model="equipmentModel.id" type="number" placeholder="请输入设备号" disabled></el-input>
        </el-form-item>
        <el-form-item label="设备名称" prop="name">
          <el-input v-model="equipmentModel.name" placeholder="请输入设备名称"></el-input>
        </el-form-item>
        <el-form-item label="设备类型" prop="type">
          <el-select placeholder="请选择" v-model="equipmentModel.type">
            <el-option v-for="c in categories" :key="c.name" :label="c.name" :value="c.name">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="equipmentModel.brand" placeholder="请输入设备品牌"></el-input>
        </el-form-item>
        <el-form-item label="所属单位" prop="department">
          <el-select v-if="isSuperAdmin" placeholder="请选择" v-model="equipmentModel.department">
            <el-option label="计算学部" value="计算学部"></el-option>
            <el-option label="数学学院" value="数学学院"></el-option>
            <el-option label="物理学院" value="物理学院"></el-option>
            <el-option :label=adminDepartment :value=adminDepartment></el-option>
          </el-select>
          <el-select v-else placeholder="请选择" v-model="equipmentModel.department" disabled>
            <el-option label="计算学部" value="计算学部"></el-option>
            <el-option label="数学学院" value="数学学院"></el-option>
            <el-option label="物理学院" value="物理学院"></el-option>
            <el-option :label=adminDepartment :value=adminDepartment></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="discard">
          <el-select placeholder="请选择" v-model="equipmentModel.discard">
            <el-option label="正常" value="正常"></el-option>
            <el-option label="报废" value="报废"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备图片" prop="file">
          <el-button size="mini" @click="triggerFileInput">上传图片</el-button>
          <input id="img_input" ref="imgInput" type="file" accept="image/*" @change="onFileChange" style="display: none;"/>
<!--          <label for="img_input"></label>-->
          <div v-if="imageData || equipmentModel.url" class="preview_box">
            <img v-if="title ==='添加设备'&&imageData" class="preview" :src="imageData" width="50%" height="50%" style="margin-top: 10px;"
                 alt="avatar"/>
            <img v-else :src="equipmentModel.url" width="50%" height="50%" class="avatar" style="margin-top: 10px;">
          </div>
        </el-form-item>

        <!--添加设备按钮-->
        <el-form-item>
          <!--                    <el-button type="primary" @click="addEquipment()">添加设备</el-button>-->
          <el-button type="primary"
                     @click="title === '添加设备' ? addEquipment() : updateEquipment()">确认
          </el-button>
        </el-form-item>
      </el-form>
    </el-drawer>

    <!-- 批量添加设备 -->
    <EquipmentDialog :dialogVisible.sync="dialogVisible" :title="批量添加设备" @update:dialogVisible="dialogVisible = $event"
                     :batchAddEquipment="batchAddEquipment"/>
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

/* 抽屉样式 */
.avatar-uploader {
  :deep() {
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }

    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }

    .el-upload:hover {
      border-color: var(--el-color-primary);
    }

    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      text-align: center;
    }
  }
}

.editor {
  width: 100%;

  :deep(.ql-editor) {
    min-height: 200px;
  }
}
</style>