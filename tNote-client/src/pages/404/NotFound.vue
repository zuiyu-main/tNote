<template>
  <div>
    <h1>404</h1>
    <h1>{{this.date}}</h1>
  </div>
</template>
<script>
export default {
  name: 'not',
  data () {
    return {
      date: this.formatDateTime(new Date())
    }
  },
  methods: {
    formatDateTime (inputTime) {
      var date = new Date(inputTime)
      var y = date.getFullYear()
      var m = date.getMonth() + 1
      m = m < 10 ? '0' + m : m
      var d = date.getDate()
      d = d < 10 ? '0' + d : d
      var h = date.getHours()
      h = h < 10 ? '0' + h : h
      var minute = date.getMinutes()
      var second = date.getSeconds()
      minute = minute < 10 ? '0' + minute : minute
      second = second < 10 ? '0' + second : second
      return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second
    }
  },
  mounted () {
    var _this = this
    this.timer = setInterval(function () {
      _this.date = _this.formatDateTime(new Date())
    }, 1000)
  },
  beforeDestroy () {
    if (this.timer) {
      clearInterval(this.timer) // 在Vue实例销毁前，清除定时器
    }
  }
}
</script>
<style scoped >
body {
  text-align: center;
  background-color: darkgray;
}
</style>
