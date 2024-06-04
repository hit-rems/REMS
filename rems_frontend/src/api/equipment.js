import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'


//科研设备分类列表查询
export const equipmentCategoryListService = ()=>{
    return request.get('category/list')
}

//科研设备分类添加
export const equipmentCategoryAddService = (categoryData)=>{
    return request.get('/category/add',categoryData)
}

//科研设备分类修改
export const equipmentCategoryUpdateService = (categoryData)=>{
    return  request.put('equipment/category',categoryData)
}

//科研设备分类删除
export const equipmentCategoryDeleteService = (id)=>{
    return request.delete('equipment/category?id='+id)
}

//科研设备列表查询
export const equipmentListService = (params)=>{
    return  request.post('/equipment/pagelist', params)
}

//科研设备添加
export const equipmentAddService = (equipmentData)=>{
    return request.post('/equipment/add',equipmentData);
}