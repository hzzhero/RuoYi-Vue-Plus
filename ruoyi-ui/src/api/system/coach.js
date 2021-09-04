import request from '@/utils/request'

// 查询VIEW列表
export function listCoach(query) {
  return request({
    url: '/system/coach/list',
    method: 'get',
    params: query
  })
}

// 查询VIEW详细
export function getCoach(userId) {
  return request({
    url: '/system/coach/' + userId,
    method: 'get'
  })
}

// 新增VIEW
export function addCoach(data) {
  return request({
    url: '/system/coach',
    method: 'post',
    data: data
  })
}

// 修改VIEW
export function updateCoach(data) {
  return request({
    url: '/system/coach',
    method: 'put',
    data: data
  })
}

// 删除VIEW
export function delCoach(userId) {
  return request({
    url: '/system/coach/' + userId,
    method: 'delete'
  })
}
