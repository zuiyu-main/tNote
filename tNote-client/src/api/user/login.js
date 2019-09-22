import { request } from '@/utils/request/axios.js'
// 用户登录接口
/**
 * 用户登录
 * @param {*} data
 */
export function userLogin (data) {
  return request('post', '/user/login', data)
}
