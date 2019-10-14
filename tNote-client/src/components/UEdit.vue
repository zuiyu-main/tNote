
<template>
  <div class="hei">
    <Uediter class="hei" :value="ueditor.value" :config="ueditor.config" ref="ue"></Uediter>
    <el-button @click="returnContent">显示编辑器内容</el-button>
    <div>{{dat.content}}</div>
  </div>
</template>
<script>
import Uediter from '@/components/UE.vue'

export default {
  data () {
    return {
      hei: '100%',
      dat: {
        content: ''
      },
      ueditor: {
        value: 'Please enter the content...'
        // config: {
        //   initialFrameWidth: 800,
        //   initialFrameHeight: 500
        // }
      }
    }
  },
  methods: {
    returnContent () {
      console.log(this.$refs.ue.getUEContent())
      this.dat.content = this.$refs.ue.getUEContent()
    }
  },
  components: {
    Uediter
  }
}
</script>
<style scoped>
#edui1,
#editor,
#edui1_iframeholder {
  height: 100% !important;
  width: 100% !important;
}
</style>
