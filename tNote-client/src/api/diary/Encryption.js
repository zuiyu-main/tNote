// 加密js
import { request } from '@/utils/request/axios.js'
/**
 * {
 * targetId
 * password
 * }
 * @param {*} data
 */
export function encryptionItem (data) {
  return request('post', '/encryption/item', data)
}
