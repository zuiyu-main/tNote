import axios from 'axios'

// axios 配置
var instance = axios.create({
  headers: {
    'Content-Type': 'application/json'
  },
  timeout: 30000,
  baseURL: process.env.API_ROOT // 接口请求地址
})

// 可以在这先申明错误代码表示的含义

// 添加请求拦截器
instance.interceptors.request.use(config => {
  // 在发送请求之前做些什么，比如传token
  const token = localStorage.getItem('token')
  const wsId = sessionStorage.getItem('wsId')
  if (token) {
    config.headers.Authorization = token
  }
  if (wsId) {
    config.headers.wsId = wsId
  }
  config.data = JSON.stringify(config.data)
  return config
}, error => {
  // 对请求错误做些什么
  console.log(error) // for debug
  return Promise.reject(error)
})

// 添加响应拦截器
instance.interceptors.response.use(response => {
  // 对响应数据做点什么
  // const res = response.data
  // 对错误代码做处理
  return response
}, error => {
  // 对响应错误做点什么
  console.log('err' + error)// for debug
  this.$message.error(error)
  return Promise.reject(error)
})

export default instance

/**
 * post 请求方法
 * @param url
 * @param data
 * @returns {Promise}
 */
export function post (url, data = {}) {
  return new Promise((resolve, reject) => {
    instance.post(url, data).then(response => {
      // 对接口错误码做处理
      resolve(response.data)
    }, err => {
      reject(err)
    })
  })
}

/**
 * get 请求方法
 * @param url
 * @param data
 * @returns {Promise}
 */
export function get (url, data = {}) {
  return new Promise((resolve, reject) => {
    instance.get(url, {
      params: data
    })
      .then(response => {
        resolve(response)
      })
      .catch(err => {
        reject(err)
      })
  })
}

/**
 * 封装所有请求
 * @param methed
 * @param url
 * @param data
 * @param headers
 * @returns {Promise}
 */
export function request (methed, url, data = {}, headers) {
  return new Promise((resolve, reject) => {
    instance({
      method: methed || 'post',
      url: url,
      params: methed === 'get' ? data : '',
      data: methed !== 'get' ? data : '',
      headers: headers || { 'Content-Type': 'application/json' }
    })
      .then(response => {
        // 对接口错误码做处理
        resolve(response.data)
      })
      .catch(err => {
        reject(err)
      })
  })
}
