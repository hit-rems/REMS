<script setup>
import {
    PieChart,
    Management,
    Promotion,
    Stamp,
    List,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom,
    Odometer,
    Fold
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'

import {userInfoService} from '@/api/user.js'
import useUserInfoStore from '@/stores/userInfo.js'
import {useTokenStore} from '@/stores/token.js'
import {ref, watch, inject} from 'vue'
import { nextTick } from 'vue'
import { computed } from 'vue'
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();

// 调用函数,获取用户详细信息
const getUserInfo = async()=>{
    //调用接口
    let result = await userInfoService();
    //数据存储到pinia中
    userInfoStore.setInfo(result.data);
}

getUserInfo();
// console.log(userInfoStore.info)

//条目被点击后,调用的函数
import {useRouter} from 'vue-router'
const router = useRouter();

if(!userInfoStore.info){
  router.push('/login')
}

import {ElMessage,ElMessageBox} from 'element-plus'
const handleCommand = (command)=>{
    //判断指令
    if(command === 'logout'){
        //退出登录
        ElMessageBox.confirm(
        '您确认要退出吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //退出登录
            //1.清空pinia中存储的token以及个人信息
            tokenStore.removeToken()
            userInfoStore.removeInfo()

            //2.跳转到登录页面
            router.push('/login')
            ElMessage({
                type: 'success',
                message: '已退出登录',
            })
            
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用户取消了退出登录',
            })
        })
    }else{
        //路由
        router.push('/user/'+command)
    }
}


// 设置菜单栏可折叠
const isCollapse = ref(false);
const handleOpen = (key, keyPath) => {
  isCollapse.value = false;
};

const logoClass = ref('el-aside__logo_big')

const collapseNavigator = () =>{
  isCollapse.value = !isCollapse.value;
  // 动态改变logo的大小
  if(isCollapse.value){
    logoClass.value = 'el-aside__logo_small';
  } else {
    logoClass.value = 'el-aside__logo_big';
  }
}
</script>

<template>
    <!-- element-plus中的容器 -->
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="collapse">
            <div :class="logoClass"></div>
            <!-- element-plus的菜单标签 -->
            <el-menu
                active-text-color="#ffd04b" background-color="#014561"  text-color="#fff"
                class="el-menu-vertical-demo"
                :collapse="isCollapse" @open="handleOpen"
                router>
                <el-menu-item index="/category" v-if='userInfoStore.info.type!=="学生"'>
                    <el-icon><PieChart /></el-icon>
                    <span>科研设备分类</span>
                </el-menu-item>
                <el-menu-item index="/equipment" v-if='userInfoStore.info.type!=="学生"'>
                    <el-icon><Promotion /></el-icon>
                    <span>科研设备管理</span>
                </el-menu-item>

                <el-menu-item index="/book">
                  <el-icon><Management /></el-icon>
                  <span>科研设备预约</span>
                </el-menu-item>

                <el-menu-item index="/approvals">
                  <el-icon><List /></el-icon>
                  <span>个人预约详情</span>
                </el-menu-item>


                <el-menu-item index="/audits" v-if='userInfoStore.info.type!=="学生"'>
                  <el-icon><Stamp /></el-icon>
                  <span>设备预约审核</span>
                </el-menu-item>

                <el-menu-item index="/superadmin" v-if='userInfoStore.info.type==="超级管理员"'>
                  <el-icon><Odometer /></el-icon>
                  <span>用户管理</span>
                </el-menu-item>

                <el-sub-menu >
                    <template #title>
                        <el-icon><UserFilled /></el-icon>
                        <span>个人中心</span>
                    </template>
                    <el-menu-item index="/user/info">
                        <el-icon><User /></el-icon>
                        <span>基本资料</span>
                    </el-menu-item>
<!--                    <el-menu-item index="/user/avatar">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>更换头像</span>
                    </el-menu-item>-->
                    <el-menu-item index="/user/resetPassword">
                        <el-icon><EditPen /></el-icon>
                        <span>重置密码</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>

        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 -->
            <el-header>
                <div class="collapse-btn" @click="collapseNavigator">
                  <el-icon>
                    <Fold />
                  </el-icon>
                </div>
                <div>
                    <p>欢迎来自 <strong>{{ userInfoStore.info ? userInfoStore.info.department : '' }}</strong> 的
                        <strong>{{userInfoStore.info.type}}</strong>：<strong>{{ userInfoStore.info ? userInfoStore.info.nickname : '' }}</strong></p>
                </div>
                <!-- 下拉菜单 -->
                <!-- command: 条目被点击后会触发,在事件函数上可以声明一个参数,接收条目对应的指令 -->
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar v-if="userInfoStore.info" :src="userInfoStore.info.url"/>
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
<!--                            <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>-->
                            <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <router-view></router-view>
                <el-footer>科研设备管理平台 ©2024 Created by&nbsp;<a href="http://github.com/hit-rems" target="_blank">HIT-REMS team</a></el-footer>
            </el-main>
            <!-- 底部区域 -->
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 98vh;

    .el-aside {
        margin-top: -10px;
        margin-left: -10px;
        margin-bottom: -10px;
        background-color: #005375;

        &__logo_big {
            margin-top: 20px;
            height: 120px;
            background: url('@/assets/logo2.png') no-repeat center / 120px auto;
        }

        &__logo_small {
          margin-top: 20px;
          height: 120px;
          background: url('@/assets/logo2.png') no-repeat center / 60px auto;
        }

        .el-menu {
            margin-top: 20px;
            border-right: none;
        }

        .el-menu-vertical-demo:not(.el-menu--collapse) {
          width: 190px;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .collapse-btn {
          font-size: 24px;
          margin-right: 10px;
          color: #545c64;
          padding-top: 6px;
        }

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}


</style>