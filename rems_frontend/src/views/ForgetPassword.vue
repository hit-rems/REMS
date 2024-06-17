<script setup>

import {ref} from "vue";
import { User } from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";

const registerData = ref({
  username: '',
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 4, max: 16, message: '长度为 4~16', trigger: 'blur' },
    { validator: (rule, value, callback) => {
        const reg = /^[a-zA-Z0-9_]{4,16}$/
        if (!reg.test(value)) {
          callback(new Error('用户名只能包含字母、数字和下划线'))
        } else {
          callback()
        }
      }, trigger: 'blur'}
  ]
}

import { findPasswordService } from '@/api/user.js'

// 点击按钮找回密码
const forgetPassword = async () => {
  console.log(registerData.value.username);
  let result = await findPasswordService(registerData.value.username);
  ElMessage.success('请查看邮件');
}

</script>

<template>
  <el-row class="login-page">
    <el-col :span="12" class="bg"></el-col>
    <el-col :span="6" :offset="3" class="form">
      <el-form :model="registerData" :rules="rules">
        <el-form-item>
          <h1>忘记密码</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button class="button" type="primary" auto-insert-space @click="forgetPassword">
            找回密码
          </el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>


<style lang="scss" scoped>
/* 样式 */
.login-page {
  height: 100vh;
  background-color: #fff;

  .bg {
    background: url('@/assets/logo2.png') no-repeat 60% center / 240px auto,
    url('@/assets/hit2.jpg') no-repeat center / cover;
    border-radius: 0 20px 20px 0;
  }

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .title {
      margin: 0 auto;
    }

    .button {
      width: 100%;
    }

    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>