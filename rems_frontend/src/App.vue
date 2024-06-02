<script setup>
import {User, Lock} from '@element-plus/icons-vue'
</script>

<template>
  <div style="width: 100vw; height: 100vh; overflow: hidden; display: flex">
    <div style="flex: 1">
      <el-image style="width: 100%; height: 100%"  fit="cover"
                src="https://wallpaperm.cmcm.com/88fcf0ac8b5ae0bae9672eb528e0bea2.jpg"/>
    </div>
    <div style="width: 600px; background-color: white">
      <div style="text-align: center; margin: 0 40px">
        <div style="margin-top: 150px">
          <div style="font-size: 25px;">登录</div>
          <div style="margin-top: 30px">

            <el-input type="text" placeholder="用户名">
              <template #prefix>
                <el-icon><User /></el-icon>
              </template>
            </el-input>

            <el-input type="password" style="margin-top: 20px" placeholder="密码">
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
            </el-input>
          </div>

          <el-row style="margin-top: 10px">
            <el-col :span="12" style="text-align: left;">
              <el-checkbox v-model="checked1" label="记住我"></el-checkbox>
            </el-col>
            <el-col :span="12" style="text-align: right">
              <el-link>忘记密码？</el-link>
            </el-col>
          </el-row>

          <div style="margin-top: 30px">
            <el-button style="width:200px" type="success">立即登录</el-button>
          </div>

          <el-divider>
            <span style="color: grey; font-size: 14px">没有账号</span>
          </el-divider>
          <div>
            <el-button style="width:200px" type="warning">注册账号</el-button>
          </div>

        </div>
      </div>
    </div>

  </div>
</template>

<style scoped>

</style>
