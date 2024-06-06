<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore();

const userInfo = ref({...userInfoStore.info})
const rules = {
    nickname: [
        { required: true, message: '请输入用户昵称', trigger: 'blur' },
        {
            pattern: /^\S{2,10}$/,
            message: '昵称必须是2-10位的非空字符串',
            trigger: 'blur'
        }
    ],
    department: [
        { required: true, message: '请选择所属单位', trigger: 'change' }
    ]
}

//修改个人信息
import {userInfoUpdateService} from '@/api/user.js'
import {ElMessage} from 'element-plus'
const updateUserInfo = async ()=>{
    let params = {
        nickname: userInfo.value.nickname,
        department: userInfo.value.department
    }
    //调用接口
    let result = await userInfoUpdateService(params);
    ElMessage.success(result.msg? result.msg : '修改成功');
    
    //修改pinia中的个人信息
    userInfoStore.setInfo(userInfo.value)
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>基本资料</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
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
            </el-col>

            <el-col :span="12">
              <div class="avatar-container">
                <img :src="userInfo.userPic" alt="User Avatar" class="user-avatar">
              </div>
            </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-button type="primary" @click="updateUserInfo">提交修改</el-button>
          </el-col>
        </el-row>
    </el-card>
</template>

<style scoped>
.avatar-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.user-avatar {
  max-width: 100%;
  height: auto;
}
</style>