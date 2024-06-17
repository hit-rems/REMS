import request from '@/utils/request.js'


// 查询管理员申请授权列表
export const superPageListService = (params) => {
    return request.post('/super/pagelist', params)
}

// 修改管理员申请授权状态
export const superUpdateService = (params) => {
    return request.post('/super/update', params)
}