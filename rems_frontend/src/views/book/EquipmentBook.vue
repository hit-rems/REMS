<template>
  <div class="equipment-book">
    <el-row :gutter="16">
      <el-col :span="6" v-for="(item, index) in cardsThisPage" :key="index">
        <EquipmentCard :equipmentModel="item" />
      </el-col>
    </el-row>
    <Pager :pageNum="pageNum" :pageSize="pageSize" :total="total"
           @size-change="onSizeChange" @current-change="onCurrentChange"/>
  </div>
</template>

<script setup>
import EquipmentCard from '@/components/EquipmentCard.vue';
import Pager from "@/components/Pager.vue";
import { ref, onMounted } from 'vue'
import { equipmentListService } from "@/api/equipment.js";

const pageNum = ref(1)
const total = ref(20)
const pageSize = ref(10)
const cardsThisPage = ref([])

const fetchCardsPageList = async () => {
  try {
    const params = {
      pageNum: pageNum.value,
      pageSize: pageSize.value
    }
    const result = await equipmentListService(params)
    total.value = result.data.total
    cardsThisPage.value = result.data.items
  } catch (error) {
    console.error("Error fetching equipment list:", error)
  }
}

onMounted(fetchCardsPageList)

const onSizeChange = (size) => {
  pageSize.value = size
  fetchCardsPageList()
}

const onCurrentChange = (num) => {
  pageNum.value = num
  fetchCardsPageList()
}
</script>

<style scoped>
.equipment-book {
  padding: 16px;
}
</style>
