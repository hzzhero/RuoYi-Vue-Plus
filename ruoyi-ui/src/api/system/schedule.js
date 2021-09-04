import request from '@/utils/request'

// 查询班次列表
export function listSchedule(query) {
  return request({
    url: '/system/schedule/list',
    method: 'get',
    params: query
  })
}

// 查询班次详细
export function getSchedule(scheduleId) {
  return request({
    url: '/system/schedule/' + scheduleId,
    method: 'get'
  })
}

// 新增班次
export function addSchedule(data) {
  return request({
    url: '/system/schedule',
    method: 'post',
    data: data
  })
}

// 修改班次
export function updateSchedule(data) {
  return request({
    url: '/system/schedule',
    method: 'put',
    data: data
  })
}

// 删除班次
export function delSchedule(scheduleId) {
  return request({
    url: '/system/schedule/' + scheduleId,
    method: 'delete'
  })
}
