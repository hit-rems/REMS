import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

//科研设备分类列表查询
export const categoryListService = ()=>{
    return request.get('/category/list')
}

//科研设备分类添加
export const categoryAddService = (categoryData)=>{
    return request.get('/category/add?name='+categoryData)
}

//科研设备分类修改
export const categoryUpdateService = (nameOld, nameNew)=>{
    return  request.get('/category/edit',{params: {nameOld, nameNew}});
}

//科研设备分类删除
export const categoryDeleteService = (name)=>{
    return request.delete('/category/delete',{params: {name}})
}

//科研设备分页查询
export const categoryPageListService = (params)=>{
    return  request.post('/category/pagelist', params)
}