<template>
  <el-row>
    <el-col>
      <img :src="equipmentModel.url" :alt="equipmentModel.name" />
    </el-col>
    <el-col>
      <el-descriptions :title="equipmentModel.name" :column="1" class="descriptions">
        <el-descriptions-item label="编号">{{ equipmentModel.id }}</el-descriptions-item>
        <el-descriptions-item label="类别">{{ equipmentModel.type }}</el-descriptions-item>
        <el-descriptions-item label="品牌">{{ equipmentModel.brand }}</el-descriptions-item>
        <el-descriptions-item label="所属单位">{{ equipmentModel.department }}</el-descriptions-item>
      </el-descriptions>
      <Tabs v-if="tabs" :tabs="tabs">
        <Table :content="currentContent" :title.sync="title" @update:title="title = $event" :columns="columns"/>
      </Tabs>
    </el-col>
  </el-row>
</template>

<script>
import Tabs from '@/components/Tabs.vue'
import Table from '@/components/Table.vue'
export default {
  props: {
    equipmentModel: {
      type: Object,
      required: true
    },
    currentContent: {
      type: Array,
      required: true
    },
  }
}
const tabs = [
  {name: '星期一', label: '星期一'},
  {name: '星期二', label: '星期二'},
  {name: '星期三', label: '星期三'},
  {name: '星期四', label: '星期四'},
  {name: '星期五', label: '星期五'},
  {name: '星期六', label: '星期六'},
  {name: '星期天', label: '星期天'},
];

const currentContent = ref([{
  startTime: '',
  endTime: '',
  status: '',
}]);

const columns = ref([
  { label: '预约开始时间', prop: 'startTime', width: '120', align: 'center' },
  { label: '预约结束时间', prop: 'endTime', width: '120', align: 'center' },
  { label: '审核状态', prop: 'status', width: '100', align: 'center' },
]);

</script>