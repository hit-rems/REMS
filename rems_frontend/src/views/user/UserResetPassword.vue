<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h1>重置密码</h1>
      </div>
    </template>
    <el-row>
      <el-col :span="10">
        <el-form :model="form" :rules="rules" label-width="100px" size="large" ref="formRef" @submit.native.prevent="resetPassword">
          <el-form-item label="旧密码:" prop="oldPassword" :rules="{ required: true, message: '请输入旧密码', trigger: 'blur' }">
            <el-input v-model="form.oldPassword" type="password" required show-password></el-input>
          </el-form-item>
          <el-form-item label="新密码:" prop="newPassword" :rules="{ required: true, message: '请输入新密码', trigger: 'blur' }">
            <el-input v-model="form.newPassword" type="password" required show-password></el-input>
          </el-form-item>
          <el-form-item label="确认新密码:" prop="confirmPassword" :rules="{ required: true, message: '请再次输入新密码', trigger: 'blur' }">
            <el-input v-model="form.confirmPassword" type="password" required show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" native-type="submit" style="margin-right: 10px; float: right">重置密码</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { ElForm, ElFormItem, ElInput, ElButton, ElMessage } from 'element-plus';
import { userResetPasswordService } from '@/api/user.js';

let formRef = ref(null);
let form = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
});

let rules = reactive({
  confirmPassword: [
    { validator: (rule, value, callback) => {
      if (value !== form.newPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    }, trigger: 'blur' }
  ]
});

const resetPassword = () => {
  console.log('form', form);
  formRef.value.validate(async (valid) => {
    if (valid) {
      let params = {
        old_pwd: form.oldPassword,
        new_pwd: form.newPassword
      };
      let result = await userResetPasswordService(params);
      if (result.code === 0) {
        ElMessage.success('密码重置成功');
        form.oldPassword = '';
        form.newPassword = '';
        form.confirmPassword = '';
      } else {
        ElMessage.error('密码重置失败，请检查旧密码是否正确');
        return false;
      }
    } else {
      console.log('error submit!!');
      return false;
    }
  });
};
</script>

<style scoped>
/* 在这里添加样式 */
</style>