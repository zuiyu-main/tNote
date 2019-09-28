import { request } from '@/utils/request/axios.js'
/**
 * 新建分类
 * @param {*} data
 */
export function save (data) {
  return request('post', '/note/save', data)
}
/**
 * 查看我的分类
 * @param {}} data
 */
export function getItem () {
  return request('get', '/note/getItem')
}
/**
 * 查看我的分类
 * @param {}} data
 */
export function getNoteByItem (data) {
  return request('get', '/note/getNoteByItem', data)
}
