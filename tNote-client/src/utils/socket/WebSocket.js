var UUID = require('uuid')
var websock = null
var globalCallback = null
var serverPort = '2000'
var CLIENTID = UUID.v4()
// webSocket连接端口

function getWebIP () {
  var curIP = window.location.hostname
  return curIP
}
export function initWebSocket () { // 初始化weosocket
  // ws地址
  var wsuri = 'ws://' + getWebIP() + ':' + serverPort + '/websocket/' + CLIENTID
  websock = new WebSocket(wsuri)
  websock.onmessage = function (e) {
    websocketonmessage(e)
  }
  websock.onclose = function (e) {
    websocketclose(e)
  }
  websock.onopen = function () {
    websocketOpen()
  }

  // 连接发生错误的回调方法
  websock.onerror = function () {
    console.log('WebSocket连接发生错误')
  }
}

// 实际调用的方法
function sendSock (agentData, callback) {
  globalCallback = callback
  if (websock.readyState === websock.OPEN) {
    // 若是ws开启状态
    websocketsend(agentData)
  } else if (websock.readyState === websock.CONNECTING) {
    // 若是 正在开启状态，则等待1s后重新调用
    setTimeout(function () {
      sendSock(agentData, callback)
    }, 1000)
  } else {
    // 若未开启 ，则等待1s后重新调用
    setTimeout(function () {
      sendSock(agentData, callback)
    }, 1000)
  }
}

// 数据接收
function websocketonmessage (e) {
  if (globalCallback === null) {
    return
  }
  globalCallback(JSON.parse(e.data))
}

// 数据发送
function websocketsend (agentData) {
  websock.send(JSON.stringify(agentData))
}

// 关闭
function websocketclose (e) {
  console.log('connection closed (' + e.code + ')')
}

function websocketOpen (e) {
  console.log('连接成功,客户端id', CLIENTID)
  sessionStorage.setItem('wsId', CLIENTID)
}

initWebSocket()

export { sendSock }
