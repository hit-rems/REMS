import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'


//科研设备分类列表查询
export const equipmentCategoryListService = ()=>{
    return request.get('/category/list')
}

//科研设备列表查询
export const equipmentListService = (params)=>{
    return  request.post('/equipment/pagelist', params)
}

//科研设备添加
export const equipmentAddService = (equipmentData)=>{
    return request.post('/equipment/add', equipmentData);
}