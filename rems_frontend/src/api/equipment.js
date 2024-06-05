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
    //借助于UrlSearchParams完成传递
    // const params = new URLSearchParams()
    // for(let key in equipmentData){
    //     params.append(key,equipmentData[key]);
    // }
    // return request.post('/equipment/add', params);

    return request.post('/equipment/add', equipmentData);
}

//科研设备删除
export const equipmentDeleteService = (id)=>{
    return request.delete('/equipment/delete/'+id)
}

//科研设备修改
export const equipmentUpdateService = (equipmentData)=>{
    return request.put('/equipment/update', equipmentData)
}