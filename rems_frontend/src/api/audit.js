import request from '@/utils/request.js'

//预约列表分页查询
export const bookPageListService = (params)=>{
    return request.post('/audit/pagelist', params)
}