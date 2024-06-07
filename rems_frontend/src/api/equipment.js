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

//科研设备批量添加
export const equipmentBatchAddService = (fileList)=>{
    // 创建一个新的 FormData 对象
    const formData = new FormData();

    // 遍历文件列表，将文件添加到 formData 中
    fileList.forEach((file) => {
        formData.append('files', file.raw);
    });

    return request.post('/equipment/uploadlist', formData, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}

//科研设备删除
export const equipmentDeleteService = (id)=>{
    return request.delete('/equipment/delete/'+id)
}

//科研设备修改
export const equipmentUpdateService = (equipmentData)=>{
    return request.put('/equipment/update', equipmentData)
}

//科研设备查询
export const equipmentQueryService = (id)=>{
    return request.get('/equipment/query?id=' + id)
}