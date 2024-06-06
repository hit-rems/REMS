<template>
  <div class="equipment-book">
    <el-table :data="equipmentList" style="width: 100%">
      <el-table-column>
        <template #default="{ row }">
          <EquipmentCard :equipmentModel="row" />
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import EquipmentCard from '@/components/EquipmentCard.vue';

export default {
  name: 'EquipmentBook',
  components: {
    EquipmentCard,
  },
  data() {
    return {
      equipmentList: [],
    };
  },
  methods: {
    async fetchEquipmentList() {
      // Replace with your actual API call
      const response = await fetch('https://api.example.com/equipment');
      this.equipmentList = await response.json();
    },
  },
  created() {
    this.fetchEquipmentList();
  },
};
</script>

<style scoped>
.equipment-book {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}
</style>