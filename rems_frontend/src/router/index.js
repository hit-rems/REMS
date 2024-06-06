import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

// import ArticleCategoryVue from '@/views/article/ArticleCategory.vue'
// import ArticleManageVue from '@/views/article/ArticleManage.vue'

import EquipmentCategoryVue from '@/views/equipment/EquipmentCategory.vue'
import EquipmentManageVue from '@/views/equipment/EquipmentManage.vue'

import UserAvatarVue from '@/views/user/UserAvatar.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'

import EquipmentBook from '@/views/book/EquipmentBook.vue'
import EquipmentAudits from "@/views/audits/EquipmentAudits.vue";

//定义路由关系
const routes = [
    { path: '/login', component: LoginVue},
    // { path: '/', component: LayoutVue},
    {
      path: '/',
      component: LayoutVue,
      redirect:'/equipment',
      children: [
          { path: '/category', component: EquipmentCategoryVue},
          { path: '/equipment', component: EquipmentManageVue},
          { path: '/user/info', component: UserInfoVue },
          { path: '/user/avatar', component: UserAvatarVue },
          { path: '/user/resetPassword', component: UserResetPasswordVue },
          { path: '/book', component: EquipmentBook},
          { path: '/audits', component: EquipmentAudits},
      ]
    }
]


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: routes
})

export default router
