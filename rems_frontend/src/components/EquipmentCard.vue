<template>
  <el-card class="equipment-card" shadow="hover" @click="handleCardClick(equipmentModel.id)">
    <el-descriptions :title="equipmentModel.name" :column="1" class="descriptions">
      <el-descriptions-item label="编号">{{ equipmentModel.id }}</el-descriptions-item>
      <el-descriptions-item label="类别">{{ equipmentModel.type }}</el-descriptions-item>
      <el-descriptions-item label="品牌">{{ equipmentModel.brand }}</el-descriptions-item>
    </el-descriptions>
    <div class="equipment-image">
      <img :src="equipmentModel.url" :alt="equipmentModel.name" />
    </div>

    <!-- <el-button class="hover-button" v-show="isHovering" @click.stop="handleButtonClick">Hover Button</el-button>

    <el-dialog :visible.sync="isDialogVisible" title="Hello world">
      <p>Some contents...</p>
    </el-dialog> -->
  </el-card>
</template>

<script setup>
import {useRouter} from 'vue-router';
import { defineProps } from 'vue';

const props = defineProps({
  equipmentModel: {
    type: Object,
    required: true,
  },
});

// 点击卡片实现路由跳转
const router = useRouter();
const handleCardClick = (id) => {
  router.push({ name: 'equipmentbookpage', params: { id: id }});
};

// export default {
//   name: 'EquipmentCard',
//   props: {
//     equipmentModel: {
//       type: Object,
//       required: true,
//     },
//   },
//   methods: {
//     handleCardClick() {
//       console.log("hahahello");
//       router.push('/');
//     }
//   }
// };

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

:deep(.descriptions .el-descriptions__title) {
  white-space: nowrap; /* Prevents the text from wrapping */
  overflow: hidden; /* Hides the text that overflows the container */
  text-overflow: ellipsis; /* Displays an ellipsis (...) when the text overflows */
}
</style>