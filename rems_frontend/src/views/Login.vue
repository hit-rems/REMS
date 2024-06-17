<script setup>
import { Message, User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import CryptoJS from 'crypto-js';
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)
//定义数据模型
const registerData = ref({
    username: '',
    password: '',
    rePassword: '',
    nickname: '',
    department: '',
    type: '',
    email: ''
})

const loginData = ref({
    username: '',
    password: ''
})

const rememberMe = ref(false)

//校验密码的函数
const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次确认密码'))
    } else if (value !== registerData.value.password) {
        callback(new Error('请确保两次输入的密码一样'))
    } else {
        callback()
    }
}

//定义表单校验规则
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
    ],
    nickname: [
      { required: true, message: '请输入昵称', trigger: 'blur' },
      { min: 1, max: 16, message: '长度为 1~16', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 1, max: 16, message: '长度不超过16位非空字符', trigger: 'blur' }
    ],
    rePassword: [
        { validator: checkRePassword, trigger: 'blur' }
    ],
    department: [
      { required: true, message: '请选择所属单位', trigger: 'change' }
    ],
    type: [
      { required: true, message: '请选择类型', trigger: 'change' }
    ],
    email: [
      { required: true, message: '请输入邮箱', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
    ]
}

//调用后台接口,完成注册
import { userRegisterService, userLoginService} from '@/api/user.js'
const register = async () => {
  const form = registerForm.value
  form.validate(async (valid) => {
    registerValid = valid
  })
  if(registerValid){
    //registerData是一个响应式对象,如果要获取值,需要.value
    let result = await userRegisterService(registerData.value);
    ElMessage.success('注册成功，请查看邮件激活账户');
    isRegister.value = false
    clearRegisterData()
  }
  else{
    ElMessage.error('请确保所有字段都填写正确!')
  }
}

//绑定数据,复用注册表单的数据模型
//表单数据校验
//登录函数
import {useTokenStore} from '@/stores/token.js'
import {useRouter} from 'vue-router'
const router = useRouter()
const tokenStore = useTokenStore();
const login = async () => {
  const form = loginForm.value
  form.validate(async (valid) => {
    loginValid = valid
  })
  if(loginValid){
    //调用接口,完成登录
    let result =  await userLoginService(loginData.value);
    ElMessage.success('登录成功')
    //把得到的token存储到pinia中
    tokenStore.setToken(result.data)
    //跳转到首页 路由完成跳转
    router.push('/')
    if (rememberMe.value) {
      localStorage.setItem('username', loginData.value.username)
      localStorage.setItem('password', CryptoJS.AES.encrypt(loginData.value.password, 'secret key 123').toString())
    } else {
      localStorage.removeItem('username')
      localStorage.removeItem('password')
    }
  }
  else {
    ElMessage.error('请确保所有字段都填写正确!')
  }
}

//定义函数,清空数据模型的数据
const clearRegisterData = ()=>{
    registerData.value={
        username:'',
        password:'',
        rePassword:'',
        nickname: '',
        department: '',
        type: '',
        email: ''
    }
}

const clearLoginData = ()=>{
    loginData.value={
        username:'',
        password:''
    }
}

clearLoginData()
// 页面加载时,判断是否有记住密码
if (localStorage.getItem('username') && localStorage.getItem('password')) {
    loginData.value.username = localStorage.getItem('username')
    loginData.value.password = CryptoJS.AES.decrypt(localStorage.getItem('password'), 'secret key 123').toString(CryptoJS.enc.Utf8)
    rememberMe.value = true
}

//表单引用
const registerForm = ref(null)
const loginForm = ref(null)

let loginValid = true
let registerValid = true

</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- 注册表单 -->
            <el-form ref="registerForm" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>注册</h1>
                </el-form-item>
                <el-form-item prop="email">
                    <el-input :prefix-icon="Message" placeholder="请输入邮箱" v-model="registerData.email"></el-input>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="nickname">
                  <el-input :prefix-icon="User" placeholder="请输入昵称" v-model="registerData.nickname"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码"
                        v-model="registerData.password" show-password></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码"
                        v-model="registerData.rePassword" show-password></el-input>
                </el-form-item>

                <el-form-item prop="type">
                  <el-select v-model="registerData.type" placeholder="请选择类型(学生/管理员)">
                    <el-option label="学生" value="学生"></el-option>
                    <el-option label="管理员" value="管理员"></el-option>
                  </el-select>
                </el-form-item>

                <el-form-item prop="department">
                  <el-select v-model="registerData.department" placeholder="请选择所属单位">
                    <el-option label="计算学部" value="计算学部"></el-option>
                    <el-option label="数学学院" value="数学学院"></el-option>
                    <el-option label="物理学院" value="物理学院"></el-option>
                  </el-select>
                </el-form-item>

                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register">
                        注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false;clearRegisterData()">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="loginForm" size="large" autocomplete="off" v-else :model="loginData" :rules="rules">
                <el-form-item>
                    <h1>登录</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="loginData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="loginData.password" show-password></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox v-model="rememberMe">记住我</el-checkbox>
                        <el-link type="primary" :underline="false" href="/forgetPassword">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true;clearLoginData()">
                        注册 →
                    </el-link>
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