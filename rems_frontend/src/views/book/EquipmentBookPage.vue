<script>
import BookPage from "@/components/BookPage.vue";

import {equipmentQueryService} from '@/api/equipment.js'
import {} from '@/api/book.js'

const equipmentModel = ref({
  id: 0,
  type: '',
  name: '',
  department: '',
  discard: '',
  brand: '',
  createTime: '',
  updateTime: '',
  file: null,
  url: '',
})

// 获取指定id的设备信息
const getEquipmentInfo = async () => {
  const id = 1;
  let result = await equipmentQueryService(id);
  equipmentModel.value = result.data;
}

// 获取指定设备、指定日期的
const getEquipmentBookStatus = async () => {

}

getEquipmentInfo();
getEquipmentBookStatus();

</script>

<template>
  <BookPage :equipmentModel="equipmentModel" :currentContent="currentContent">
  </BookPage>
</template>