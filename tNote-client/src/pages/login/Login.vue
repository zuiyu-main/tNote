<template>
  <div class="homepage-hero-module">
    <div class="video-container">
      <div :style="fixStyle" class="filter"></div>
      <video :style="fixStyle" autoplay loop class="fillWidth" v-on:canplay="canplay">
        <source src="@/video/MP4/Sunset-Siesta.mp4" type="video/mp4" />浏览器不支持 video 标签，建议升级浏览器。
        <source src="@/video/WEBM/Sunset-Siesta.webm" type="video/webm" />浏览器不支持 video 标签，建议升级浏览器。
      </video>
      <!-- 此处 v-if="！vedioCanPlay" 可以播放视频，但是贼热-->
      <div class="poster hidden" v-if="vedioCanPlay">
        <img :style="fixStyle" src="@/video/Snapshots/Sunset-Siesta.jpg" alt />
      </div>
      <div class="login-form">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="用户名">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.password" type="password"></el-input>
          </el-form-item>
          <el-form-item>
            <el-radio-group v-model="form.resource">
              <el-radio label="自动登录"></el-radio>
              <el-radio label="记住密码"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">登录</el-button>
            <el-button @click="register">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import { userLogin } from '@/api/user/login'
export default {
  name: 'login',
  components: {},
  data () {
    return {
      // 控制播放视频
      vedioCanPlay: true,
      fixStyle: '',
      form: {
        name:
          JSON.parse(localStorage.getItem('loginInfo')) === null
            ? ''
            : JSON.parse(localStorage.getItem('loginInfo')).userName,
        password:
          JSON.parse(localStorage.getItem('loginInfo')) === null
            ? ''
            : JSON.parse(localStorage.getItem('loginInfo')).password,
        resource:
          JSON.parse(localStorage.getItem('loginInfo')) === null
            ? ''
            : JSON.parse(localStorage.getItem('loginInfo')).resource
      },
      autoLogin: false
    }
  },
  methods: {
    /**
     * 视频背景页面播放
     */
    canplay () {
      this.vedioCanPlay = true
    },
    /**
     * 提交登录请求
     */
    onSubmit () {
      console.log('submit!', this.form)
      const params = {
        userName: this.form.name,
        password: this.form.password
      }
      userLogin(params).then(res => {
        if (res.code === 200) {
          localStorage.setItem('token', res.data.token)
          localStorage.setItem('userInfo', JSON.stringify(res.data.user))
          const loginInfo = {
            userName: this.form.name,
            password: this.form.password,
            resource: this.form.resource
          }
          localStorage.setItem('loginInfo', JSON.stringify(loginInfo))
          this.$router.push({ path: '/edit' })
        }
      })
    },
    /**
     * 注册跳转
     */
    register () {
      this.$router.push('/register')
    }
  },
  mounted: function () {
    window.onresize = () => {
      const windowWidth = document.body.clientWidth
      const windowHeight = document.body.clientHeight
      const windowAspectRatio = windowHeight / windowWidth
      let videoWidth
      let videoHeight
      if (windowAspectRatio < 0.5625) {
        videoWidth = windowWidth
        videoHeight = videoWidth * 0.5625
        this.fixStyle = {
          height: windowWidth * 0.5625 + 'px',
          width: windowWidth + 'px',
          'margin-bottom': (windowHeight - videoHeight) / 2 + 'px',
          'margin-left': 'initial'
        }
      } else {
        videoHeight = windowHeight
        videoWidth = videoHeight / 0.5625
        this.fixStyle = {
          height: windowHeight + 'px',
          width: windowHeight / 0.5625 + 'px',
          'margin-left': (windowWidth - videoWidth) / 2 + 'px',
          'margin-bottom': 'initial'
        }
      }
    }
    window.onresize()
    // 自动登录
    if (this.form.resource === '自动登录') {
      this.onSubmit()
    }
  }
}
</script>
<style >
.homepage-hero-module,
.video-container {
  position: relative;
  height: 100vh;
  overflow: hidden;
}

.video-container .poster img,
.video-container video {
  z-index: 0;
  position: absolute;
}

.video-container .filter {
  z-index: 1;
  position: absolute;
  background: rgba(0, 0, 0, 0.4);
}
.login-form {
  position: absolute;
  top: 33%;
  left: 37%;
  z-index: 1;
}
.el-form-item__label {
  color: aliceblue !important;
}
</style>
