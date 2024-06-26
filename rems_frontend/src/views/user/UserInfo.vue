<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js'
import { userInfoUpdateService, userAvatarUpdateService } from '@/api/user.js'
import { ElMessage } from 'element-plus'
const userInfoStore = useUserInfoStore();

const userInfo = ref(JSON.parse(JSON.stringify(userInfoStore.info)));
const rules = {
  nickname: [
    { required: true, message: '请输入用户昵称', trigger: 'blur' },
    {
      pattern: /^\S{1,16}$/,
      message: '昵称必须是1-16位的非空字符串',
      trigger: 'blur'
    }
  ],
  department: [
    { required: true, message: '请选择所属单位', trigger: 'change' }
  ]
}

const userInfoForm = ref(
  { nickname: '', department: '' }
)

// 修改个人信息
let userInfoValid = false
const updateUserInfo = async () => {
  const form = userInfoForm.value
  form.validate(async (valid) => {
    userInfoValid = valid
    if(userInfoValid){
      let params = {
        nickname: userInfo.value.nickname,
        department: userInfo.value.department
      }
      // 调用接口
      let result = await userInfoUpdateService(params);
      ElMessage.success(result.message ? result.message : '修改成功');
      // 修改 pinia 中的个人信息
      //创建一个新的对象，避免直接修改userInfo.value
      userInfoStore.setInfo(JSON.parse(JSON.stringify(userInfo.value)))
      // userInfoStore.setInfo(userInfo.value)
    } else {
      ElMessage.error('请确保所有字段都填写正确!')
    }
  })
}

// 选择的文件
const file = ref(null)
const imageToUpload = ref(null)

// 在文件选择后保存文件
const handleFileChange = (fileToUpload) => {
  file.value = fileToUpload.raw

  const reader = new FileReader();
  reader.readAsDataURL(fileToUpload.raw);
  reader.onload = (arg)=> {
    imageToUpload.value = arg.target.result;
  }
}

// 触发上传
const submitUpload = async () => {
  if (!file.value) {
    ElMessage.error('请先选择一个文件')
    return
  }
  // 创建一个 FormData 对象来包装文件
  const formData = new FormData()
  formData.append('file', file.value)
  // 调用接口
  let result = await userAvatarUpdateService(formData)
  //输出result
  console.log(result)

  imageToUpload.value = null

  if (result.code === 0) {
    //修改pinia中的数据
    userInfo.value.url = result.data
    //创建一个新的对象，避免直接修改userInfo.value
    userInfoStore.setInfo(JSON.parse(JSON.stringify(userInfo.value)))
    // userInfoStore.setInfo(userInfo.value)

    ElMessage.success('头像上传成功')
  } else {
    ElMessage.error('头像上传失败')
  }
}
</script>

<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h1>基本资料</h1>
      </div>
    </template>
    <el-row>
      <el-col :span="10">
        <el-form ref="userInfoForm" :model="userInfo" :rules="rules" label-width="100px" size="large">
          <el-form-item label="用户名">
            <el-input v-model="userInfo.username" disabled></el-input>
          </el-form-item>
          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="userInfo.nickname"></el-input>
          </el-form-item>
          <el-form-item label="所属单位" prop="department">
            <el-select v-model="userInfo.department" placeholder="请选择所属单位">
              <el-option label="计算学部" value="计算学部"></el-option>
              <el-option label="数学学院" value="数学学院"></el-option>
              <el-option label="物理学院" value="物理学院"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-row>
          <el-col :span="24">
            <el-button style="margin-right: 10px; float: right" type="primary" @click="updateUserInfo">提交修改</el-button>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="4">
      </el-col>
      <el-col :span="6">
        <img :src="userInfo.url" class="user-avatar" :span="4">
        <el-upload
            class="avatar-uploader"
            :show-file-list="false"
            :auto-upload="false"
            :on-change="handleFileChange"
        >
          <template #trigger>
            <el-button type="primary">选择图片</el-button>
          </template>
          <el-button style="margin-left: 20px;" type="success" @click="submitUpload">
            上传头像
          </el-button>
        </el-upload>
      </el-col>
      <el-col v-if="imageToUpload" :span="4">
        <img :src="imageToUpload" class="user-avatar">
        <div style="color: gray">待上传的新头像</div>
      </el-col>
    </el-row>
  </el-card>
</template>

<style scoped>
.user-avatar {
  width: 180px;
  height: 180px;
}
</style>
