import request from '@/utils/request.js'

//预约列表分页查询
export const bookPageListService = (params)=>{
    return request.post('/audit/pagelist', params)
}

//修改审核状态
export const updateStatusService = (params)=>{
    return request.post('/audit/update', params)
}

export const updateStatusBatchService = (params)=>{
    return request.post('/audit/updatelist', params)
}