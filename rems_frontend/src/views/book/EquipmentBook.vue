<template>
  <div class="equipment-book">
    <EquipmentCard
        v-for="equipment in equipmentList"
        :key="equipment.id"
        :equipmentModel="equipment"
    />
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
      equipmentList: [
        {
          id: 'NO.GW421150300',
          name: '100V3仿真器',
          type: '开发板类/开发套件',
          brand: '南京研旭电气科技',
          url: '', // Replace with the actual image path
        },
        // Add more equipment objects as needed
      ],
    };
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