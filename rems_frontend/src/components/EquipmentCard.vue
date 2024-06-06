<template>
  <el-card class="equipment-card" shadow="hover">
    <el-descriptions :title="equipmentModel.name" :column="1">
      <el-descriptions-item label="编号">{{ equipmentModel.id }}</el-descriptions-item>
      <el-descriptions-item label="类别">{{ equipmentModel.type }}</el-descriptions-item>
      <el-descriptions-item label="品牌">{{ equipmentModel.brand }}</el-descriptions-item>
    </el-descriptions>
    <div class="equipment-image">
      <img :src="equipmentModel.url" :alt="equipmentModel.name" />
    </div>
  </el-card>
</template>

<script>
export default {
  name: 'EquipmentCard',
  props: {
    equipmentModel: {
      type: Object,
      required: true,
    },
  },
};
</script>

<style scoped>

.equipment-card {
  max-width: 300px;
  width: 240px;
  height: 340px;
  margin-bottom: 10px;
}

.equipment-image {
  display: flex;
  justify-content: center;
  width: 100%;
  height: 150px;
}
</style>