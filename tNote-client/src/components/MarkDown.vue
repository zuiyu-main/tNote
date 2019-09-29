
 <template>
  <div id="base">
    <div id="editor">
      <mavon-editor ref="md" style="height:100%" v-model="content"></mavon-editor>
    </div>
    <!-- <div id="demo">@imgAdd="$imgAdd" @imgDel="$imgDel"</div> -->
  </div>
</template>
<script>
// Local Registration
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
export default {
  name: 'editor',
  props: ['value'],
  watch: {
    value (data) {
      this.content = data
    }
  },
  components: {
    mavonEditor
    // or 'mavon-editor': mavonEditor
  },
  data () {
    return {
      content: ''
    }
  },

  methods: {
    // 绑定@imgAdd event
    $imgAdd (pos, $file) {
      // 第一步.将图片上传到服务器.
      var formdata = new FormData()
      formdata.append('image', $file)
      // axios({
      //   url: 'server url',
      //   method: 'post',
      //   data: formdata,
      //   headers: { 'Content-Type': 'multipart/form-data' }
      // }).then(url => {
      // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
      /**
       * $vm 指为mavonEditor实例，可以通过如下两种方式获取
       * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
       * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
       */
      // $vm.$img2Url(pos, url)
      // })
    },
    getMDContent () {
      return this.content
    }
  }
}
</script>
    <style>
#editor {
  width: 100%;
  height: 580px;
}
#demo {
  width: 18%;
  height: 580px;
  background-color: aquamarine;
  float: left;
}
</style>
