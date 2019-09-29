/**
 * 检测json数据
 * @param {} str
 */
export function isJSON (str) {
  if (typeof str === 'string') {
    try {
      var obj = JSON.parse(str)
      if (typeof obj === 'object' && obj) {
        console.log('是JSON')
        return true
      } else {
        return false
      }
    } catch (e) {
      console.log('error：' + str + '!!!' + e)
      return false
    }
  }
}
/**
 * 检测token
 */
export function checkToken () {
  const token = localStorage.getItem('token')
  if (token) {
    return true
  } else {
    return false
  }
}