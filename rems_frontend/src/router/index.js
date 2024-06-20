import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'
import ForgetPasswordVue from '@/views/ForgetPassword.vue'

import EquipmentCategoryVue from '@/views/equipment/EquipmentCategory.vue'
import EquipmentManageVue from '@/views/equipment/EquipmentManage.vue'

import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'

import EquipmentBook from '@/views/book/EquipmentBook.vue'
import EquipmentAudits from "@/views/audits/EquipmentAudits.vue";
import EquipmentBookPage from '@/views/book/EquipmentBookPage.vue';
import Approvals from '@/views/approvals/Approvals.vue';
import SuperAdmin from '@/views/super/SuperAdmin.vue';


//定义路由关系
const routes = [
    { path: '/login', component: LoginVue},
    { path: '/forgetPassword', component: ForgetPasswordVue},
    // { path: '/', component: LayoutVue},
    {
      path: '/',
      component: LayoutVue,
      redirect:'/book',
      children: [
          { path: '/category', component: EquipmentCategoryVue},
          { path: '/equipment', component: EquipmentManageVue},
          { path: '/user/info', component: UserInfoVue },
          { path: '/user/avatar', component: UserAvatarVue },
          { path: '/user/resetPassword', component: UserResetPasswordVue },
          { path: '/book', component: EquipmentBook},
          { path: '/audits', component: EquipmentAudits},
          { path: 'bookpage/:id', name: 'equipmentbookpage',component: EquipmentBookPage},
          { path: '/approvals', component: Approvals},
          { path: '/superadmin', component: SuperAdmin},
      ]
    },
]


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: routes
})

router.beforeEach((to, from, next) => {
    const userHasPermission = checkUserPermission(to)
    if (userHasPermission) {
        // 如果有权限访问，则直接进入
        next()
    } else {
        // 如果没有权限访问，则跳转到登录页面
        next('/login')
    }
})

function checkUserPermission(to) {
    if (to.path === '/login') {
        return true
    }
    // 判断是否存在const userInfo = JSON.parse(localStorage.getItem('pinia-userInfo'))
    try {
        var userInfo = JSON.parse(localStorage.getItem('pinia-userInfo'))
    }catch (e) {
        return false
    }
    if (!userInfo) {
        return false
    }
    //判断userinfo是否有info属性
    if (!userInfo.info||!userInfo.info.type) {
        return false
    }
    if (userInfo.info.type === '管理员' && to.path === '/superadmin') {
        return false
    }
    if (userInfo.info.type === '学生' && (to.path === '/superadmin' || to.path === '/audits' || to.path === '/equipment')) {
        return false
    }
    return true
}

export default router
