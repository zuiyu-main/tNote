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
/**
 * 删除分类或者笔记
 *  { 'Content-Type': 'application/x-www-form-urlencoded' }
 * @param {*} data
 */
export function deleteDiary (data) {
  return request('delete', '/note/delete', { 'contentId': data })
}
/**
 * 更新标签
 * @param {contentId：123，
 * tagList：[{},{}]} data
 */
export function updateNoteTag (data) {
  return request('put', '/note/updateNoteTag', data)
}
