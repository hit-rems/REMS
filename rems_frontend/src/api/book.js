import request from '@/utils/request.js'


// 查询指定设备，指定日期的预约情况
export const bookQueryService = (params) => {
    return request.post('book/query', params)
}

export const bookAddService = (params) => {
    return request.post('book/add', params)
}