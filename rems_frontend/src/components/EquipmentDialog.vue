<template>
  <el-dialog :model-value="dialogVisible" @update:model-value="$emit('update:dialogVisible', $event)"
             :title="批量添加设备"
             width="50%">
    <el-form label-width="auto" style="padding-left: 50px; padding-right: 50px"
             @submit.native.prevent="batchAddEquipment(fileList)">
      <el-form-item>
        <h3>选择多张图片</h3>
        <el-upload
          v-model:file-list="fileList"
          class="upload-demo"
          action="/equipment/uploadlist"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          list-type="picture"
          :auto-upload="false"
          multiple="true"
        >
          <el-button type="primary">点击选择</el-button>
          <template #tip>
            <div class="el-upload__tip">
              <p style="line-height: 1.5em">
                支持上传多个文件，文件命名为：
                <span style="color: #f56c6c">"编号_类别_名称_部门_品牌.jpg"</span>
              </p>
            </div>
          </template>
        </el-upload>
      </el-form-item>
    </el-form>
    <template #footer>
        <span class="dialog-footer">
          <el-button @click="$emit('update:dialogVisible', false)">取消</el-button>
          <el-button type="primary"
                     @click="batchAddEquipment(fileList)"> 确认 </el-button>
        </span>
    </template>
  </el-dialog>
</template>

<script>
export default {
  props: {
    dialogVisible: Boolean,
    title: String,
    batchAddEquipment: Function
  },
  methods: {
    handlePreview(file) {
      console.log(file);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    }
  }
}
</script>

<style scoped>
.upload-demo {
  width: 100%;
}
</style>