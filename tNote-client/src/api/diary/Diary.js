import { request } from '@/utils/request/axios.js'
/**
 * 新建分类
 * @param {*} data
 */
export function addItem (data) {
  return request('post', '/user/login', data)
}
