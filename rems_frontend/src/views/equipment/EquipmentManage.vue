<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref } from 'vue'

//设备分类数据模型
const categorys = ref([])

//用户搜索时下拉框选中的设备类型和设备状态
const type = ref('')
const discard = ref('')

//设备列表数据模型
const equipments = ref([
    {},
])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(3)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    equipmentList()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    equipmentList()
}


//回显科研设备分类
import { equipmentCategoryListService, equipmentListService,equipmentAddService } from '@/api/equipment.js'
const equipmentCategoryList = async () => {
    let result = await equipmentCategoryListService();
    categorys.value = result.data;
}

//获取科研设备列表数据
const equipmentList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value,
        type: type.value ? type.value : null,
        discard: discard.value ? discard.value : null,
    }

    let result = await equipmentListService(params);

    console.log("hello")
    console.log(result)

    //渲染视图
    total.value = result.data.total;
    equipments.value = result.data.items;

}


equipmentCategoryList()
equipmentList();

// import { QuillEditor } from '@vueup/vue-quill'
// import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { Plus } from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const equipmentModel = ref({
    id: '',
    type: '',
    name: '',
    department: '',
    discard: '',
    brand: '',
    createTime: '',
    updateTime: '',
    url: '',
})


//导入token
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();

//上传成功的回调函数
const uploadSuccess = (result)=>{
    equipmentModel.value.coverImg = result.data;
    console.log(result.data);
}

//添加设备
import {ElMessage} from 'element-plus'
const addequipment = async ()=>{
    //调用接口
    let result = await equipmentAddService(equipmentModel.value);

    ElMessage.success('添加成功');
    //让抽屉消失
    visibleDrawer.value = false;
    //刷新当前列表
    equipmentList()
}
</script>


<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>科研设备管理</span>
                <div class="extra">
                    <el-button type="primary" @click="visibleDrawer = true">添加设备</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="设备类型：">
                <el-select placeholder="请选择" v-model="type" style="width: 120px;">
                    <el-option v-for="c in categorys" :key="c.type" :label="c.type" :value="c.type">
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
                <el-button type="primary" @click="equipmentList">搜索</el-button>
                <el-button @click="type = ''; discard = ''">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 设备列表 -->
        <el-table :data="equipments" style="width: 100%">
          <el-table-column label="设备号" prop="id"></el-table-column>
          <el-table-column label="设备名称" prop="name"></el-table-column>
          <el-table-column label="设备类型" prop="type"></el-table-column>
          <el-table-column label="品牌" prop="brand"></el-table-column>
          <el-table-column label="所属单位" prop="department"></el-table-column>
          <el-table-column label="状态" prop="discard"></el-table-column>
          <el-table-column label="维护时间" width="200" prop="updateTime"> </el-table-column>
          <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary"></el-button>
                    <el-button :icon="Delete" circle plain type="danger"></el-button>
                </template>
          </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />

        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" title="添加设备" direction="rtl" size="50%">
            <!-- 添加设备表单 -->

            <el-form :model="equipmentModel" label-width="100px">
                <el-form-item label="设备号">
                  <el-input v-model="equipmentModel.id" placeholder="请输入设备号"></el-input>
                </el-form-item>
                <el-form-item label="设备名称">
                    <el-input v-model="equipmentModel.name" placeholder="请输入设备名称"></el-input>
                </el-form-item>
                <el-form-item label="设备类型">
                    <el-select placeholder="请选择" v-model="equipmentModel.type">
                        <el-option v-for="c in categorys" :key="c.type" :label="c.type" :value="c.type">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="品牌">
                  <el-input v-model="equipmentModel.brand" placeholder="请输入设备品牌"></el-input>
                </el-form-item>
                <el-form-item label="所属单位">
                  <el-select placeholder="请选择" v-model="equipmentModel.department">
                    <el-option label="计算学部" value="计算学部"></el-option>
                    <el-option label="数学学院" value="数学学院"></el-option>
                    <el-option label="物理学院" value="物理学院"></el-option>
                  </el-select>
                </el-form-item>

                <el-form-item label="设备图片">
                    <!-- 
                        auto-upload:设置是否自动上传
                        action:设置服务器接口路径
                        name:设置上传的文件字段名
                        headers:设置上传的请求头
                        on-success:设置上传成功的回调函数
                     -->
                   
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                    action="/api/upload"
                    name="file"
                    :headers="{'Authorization':tokenStore.token}"
                    :on-success="uploadSuccess"
                    >
                        <img v-if="equipmentModel.coverImg" :src="equipmentModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addequipment()">添加设备</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
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