import { createApp } from 'vue'
import { createPinia } from 'pinia'
import {createPersistedState} from "pinia-persistedstate-plugin";

import App from './App.vue'
import router from './router'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import locale from 'element-plus/dist/locale/zh-cn.js'

const app = createApp(App)

app.use(router)
const pinia = createPinia()
const persist = createPersistedState()
pinia.use(persist)
app.use(pinia)
app.use(ElementPlus, {locale})
app.mount('#app')
