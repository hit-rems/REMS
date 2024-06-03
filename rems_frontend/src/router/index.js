import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import LayoutVue from '@/views/Layout.vue'

//定义路由关系
const routes = [
    // { path: '/login', component: LoginVue },
    // {
    //   path: '/', component: LayoutVue,
      // redirect:'/article/manage',
      // children: [
      //   { path: '/article/category', component: ArticleCategoryVue },
      //   { path: '/article/manage', component: ArticleManageVue },
      //   { path: '/user/info', component: UserInfoVue },
      //   { path: '/user/avatar', component: UserAvatarVue },
      //   { path: '/user/resetPassword', component: UserResetPasswordVue }
      // ]
    // }
]


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [

  ]
})

export default router
