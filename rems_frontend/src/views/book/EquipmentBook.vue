<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h1>科研设备预约</h1>
      </div>
    </template>
    <!-- 搜索表单 -->
    <el-form inline>
      <el-form-item label="设备类型：">
        <el-select placeholder="请选择" v-model="type" style="width: 120px;">
          <el-option v-for="c in categories" :key="c.name" :label="c.name" :value="c.name">
          </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="所属单位：">
        <el-select placeholder="请选择" v-model="department" style="width: 120px;">
          <el-option label="计算学部" value="计算学部"></el-option>
          <el-option label="数学学院" value="数学学院"></el-option>
          <el-option label="物理学院" value="物理学院"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="设备名称：">
        <el-input v-model="name" placeholder="请输入设备名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="fetchCardsPageList">搜索</el-button>
        <el-button @click="type = ''; name = ''; department=''">重置</el-button>
      </el-form-item>
    </el-form>

    <div class="equipment-book">
      <el-row :gutter="30">
        <el-col :span="4.8" v-for="(item, index) in cardsThisPage" :key="index" class="equipment-col">
          <EquipmentCard :equipmentModel="item"/>
        </el-col>
      </el-row>

      <template v-if="!cardsThisPage || cardsThisPage.length === 0" >
        <el-empty description="没有数据"/>
      </template>

      <Pager :pageNum="pageNum" :pageSize="pageSize" :total="total"
             @size-change="onSizeChange" @current-change="onCurrentChange"/>
    </div>
  </el-card>

</template>

<script setup>
import EquipmentCard from '@/components/EquipmentCard.vue';
import Pager from "@/components/Pager.vue";
import { ref, onMounted } from 'vue'
import { equipmentCategoryListService, equipmentListService } from "@/api/equipment.js";

//设备分类数据模型
const categories = ref([])

//用户搜索时下拉框选中的设备类型
const type = ref('')

//用户搜索时输入的设备名称
const name = ref('')

const department = ref('')

//回显科研设备分类
const equipmentCategoryList = async () => {
  let result = await equipmentCategoryListService();
  categories.value = result.data;
}

equipmentCategoryList()

const pageNum = ref(1)
const total = ref(20)
const pageSize = ref(10)
const cardsThisPage = ref([])

const fetchCardsPageList = async () => {
  try {
    let params = {
      pageNum: pageNum.value,
      pageSize: pageSize.value      
    }
    if (type.value) {
      params.type = type.value;
    }
    if (name.value) {
      params.name = name.value;
    }
    if (department.value) {
      params.department = department.value;
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

<style lang="scss" scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;

  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}

.equipment-book {
  padding: 16px;
}
.equipment-col {
  width: calc(20%);
  padding: 10px 10px;
  box-sizing: border-box;
}
</style>
