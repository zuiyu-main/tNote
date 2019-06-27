<template>
<div class="pdf">
  <div class="pdf-tab">
      <i class="el-icon-top" @click.stop="prePage"> 上一页</i>
      <i class="el-icon-bottom" @click.stop="nextPage">下一页</i>
      <i class="el-icon-refresh-right" @click.stop="clock">顺时针</i>
  <i class="el-icon-refresh-left" @click.stop="counterClock">逆时针</i>
  <i class="el-icon-printer" @click.stop="pdfPrintAll">全部打印</i>
  <i class="el-icon-printer" @click.stop="pdfPrint">部分打印</i>
  </div>
  <div>{{pageNum}}/{{pageTotalNum}}</div>
  <div>进度：{{loadedRatio}}</div>
  <div>页面加载成功: {{curPageNum}}</div>
  <pdf 
    ref="pdf"
    :src="pdfUrl"
    :page="pageNum"
    :rotate="pageRotate"
    @password="password"
    @progress="loadedRatio = $event"
    @page-loaded="pageLoaded($event)"
    @num-pages="pageTotalNum=$event" 
    @error="pdfError($event)"
    @link-clicked="page = $event">
  </pdf>
</div>
</template>
<script>
import pdf from 'vue-pdf'
export default {
  name: 'Pdf',
  components:{
      pdf
  },
  data(){
      return {
          pdfUrl: this.pdfUrlContent,
          pageNum:1,
      pageTotalNum:1,
      pageRotate:0,
      // 加载进度
      loadedRatio:0,
      curPageNum:0,
      }
  },
  mounted: function() {
  },
  props: ['pdfUrlContent'],
  methods: {
    prePage(){
      var p = this.pageNum
      p = p>1?p-1:this.pageTotalNum
      this.pageNum = p
    },
    nextPage(){
      var p = this.pageNum
      p = p<this.pageTotalNum?p+1:1
      this.pageNum = p
    },
    clock(){
      this.pageRotate += 90 
    },
    counterClock(){
      this.pageRotate -= 90 
    },
    password(updatePassword, reason) {
      updatePassword(prompt('password is "123456"'))
      console.log('...reason...')
      console.log(reason)
      console.log('...reason...')
    },
    pageLoaded(e){
      this.curPageNum = e
    },
    pdfError(error){
      console.error(error)
    },
    pdfPrintAll(){
      this.$refs.pdf.print()
    },
    pdfPrint(){
      this.$refs.pdf.print(100,[1,2])
    },
  }
}
</script>