<template>
  <div class="equipment-card">
    <div class="equipment-info">
      <div class="equipment-row">
        <span class="label">编号</span>
        <span class="value">{{ equipmentModel.id }}</span>
      </div>
      <div class="equipment-row">
        <span class="label">名称</span>
        <span class="label">{{ equipmentModel.name }}</span>
      </div>
      <div class="equipment-row">
        <span class="label">类别</span>
        <span class="value">{{ equipmentModel.type }}</span>
      </div>
      <div class="equipment-row">
        <span class="label">品牌</span>
        <span class="value">{{ equipmentModel.brand }}</span>
      </div>
    </div>
    <div class="equipment-image">
      <img :src="equipmentModel.url" :alt="equipmentModel.name" />
    </div>
  </div>
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
  display: flex;
  flex-direction: column;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 16px;
  max-width: 300px;
  background-color: #fff;
}

.equipment-info {
  margin-bottom: 16px;
}

.equipment-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.label {
  font-weight: bold;
  color: #666;
}

.value {
  color: #333;
}

.equipment-image {
  display: flex;
  justify-content: center;
}

.equipment-image img {
  max-width: 100%;
  height: auto;
}
</style>