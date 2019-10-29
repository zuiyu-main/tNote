// 加密js
import { request } from '@/utils/request/axios.js'
/**
 * 加密
 * {
 * targetId
 * password
 * }
 * @param {*} data
 */
export function encryptionItem (data) {
  return request('post', '/encryption/item', data)
}
/**
 * 重置密码
 * @param {*} data
 */
export function resetPassword (data) {
  return request('post', '/encryption/update', data)
}
/**
 * 检测是否加密
 * @param {*} data
 */
export function check (data) {
  return request('get', '/encryption/check', data)
}
/**
 * 校验加密密码
 * @param {*} data
 */
export function verify (data) {
  console.log(data)
  return request('post', '/encryption/verify', data)
}
