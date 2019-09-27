<template>
  <div class="hei">
    <el-container class="hei">
      <el-header style="text-align: right; font-size: 12px">
        <el-link class="addNote" icon="el-icon-folder-add" :underline="false" @click="open">新建分类</el-link>
        <el-link
          class="addNote"
          icon="el-icon-document-add"
          :underline="false"
          @click="showEdit = true"
        >新建日记</el-link>
        <el-dropdown>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>查看</el-dropdown-item>
            <el-dropdown-item>新增</el-dropdown-item>
            <el-dropdown-item>删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

        <el-dropdown @command="handleCommand">
          <span class="el-dropdown-link">
            {{loginUser}}
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="a">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-container class="hei">
        <el-aside width="200px">
          <li v-for="(item,index) in itemData " :key="index">
            <el-button type="text">{{item.label}}</el-button>
          </li>
        </el-aside>
        <el-main>
          <div v-if="showEdit">
            <el-input v-model="form.name" placeholder="日记名称">
              <el-button slot="append" title="保存" icon="el-icon-check" @click="submit"></el-button>
            </el-input>
            <uEdit v-if="noteType === 'html'" class="hei" ref="ue" :value="uedit.content"></uEdit>
            <mark-down v-if="noteType === 'md'" class="hei"></mark-down>
            <el-drawer
              title="请选择详情设置"
              :before-close="handleClose"
              :visible.sync="drawer"
              direction="rtl"
              custom-class="demo-drawer"
              ref="drawer"
            >
              <div class="demo-drawer__content">
                <el-form :model="form">
                  <el-form-item label="日记名称" :label-width="formLabelWidth">
                    <el-input disabled v-model="form.name" autocomplete="off"></el-input>
                  </el-form-item>
                  <el-form-item label="日记分类" :label-width="formLabelWidth">
                    <el-select v-model="form.region" placeholder="请选择分类">
                      <el-option
                        v-for="(item,index) in itemData"
                        :key="index"
                        :label="item.label"
                        :value="item.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-form>
                <div class="demo-drawer__footer">
                  <el-button @click="drawer = false">取 消</el-button>
                  <el-button
                    type="primary"
                    @click="$refs.drawer.closeDrawer()"
                    :loading="loading"
                  >{{ loading ? '提交中 ...' : '确 定' }}</el-button>
                </div>
              </div>
            </el-drawer>
          </div>
          <div v-else>
            <show-diary></show-diary>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import { mapState, mapActions } from 'vuex'
import uuid from 'uuid'
import MarkDown from '@/components/MarkDown'
import UEdit from '@/components/UE'
import ShowDiary from '@/pages/diary/ShowDiaryList'
export default {
  name: 'index',
  components: {
    MarkDown,
    UEdit,
    ShowDiary
  },
  data () {
    return {
      itemData: [], // 分类数据
      diaryData: [], // 当前日记数据
      ss: this.$store.state.diary.data,
      showEdit: false, // 显示编辑器
      uedit: {
        content: 'jjjj'
      }, // ue内容
      form: {
        name: '',
        region: ''
      }, // form表单内容
      drawer: false, // 显示抽屉
      loading: false, // 加载
      formLabelWidth: '80px', // 抽屉label
      noteType: 'md',
      loginUser: JSON.parse(localStorage.getItem('userInfo')).realName
    }
  },
  computed: {
    ...mapState('diary', {
      count: state => state.count
    })
  },
  methods: {
    ...mapActions('diary', ['setData']),
    open () {
      this.$prompt('请输入新分类名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      })
        .then(({ value }) => {
          this.itemData.push({ id: uuid.v1(), label: value })
          this.$message({
            type: 'success',
            message: '创建分类成功'
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '操作取消'
          })
        })
    },
    submit () {
      console.log('提交内容')
      this.drawer = true
    },
    handleClose (done) {
      this.$confirm('确定要提交表单吗？')
        .then(_ => {
          this.loading = true
          setTimeout(() => {
            this.loading = false
            done()
          }, 2000)
        })
        .catch(_ => {})
    },
    /**
     * websocket
     */
    getConfigResult (data) {
      console.log(data)
      const json = JSON.parse(data)
      if (json.status === '200' && json.msg === 'logout') {
        this.$message('这是一条消息提示')
      }
    },
    /**
     * 用户下啦
     */
    handleCommand (command) {
      this.$message('退出成功')
      if (command === 'a') {
        localStorage.removeItem('userInfo')
        localStorage.removeItem('loginInfo')
        localStorage.removeItem('token')
        this.$router.push({ path: '/' })
      }
    }
  },
  mounted () {
    this.socketApi.sendSock("{'msg':'测试websocket'}", this.getConfigResult)
  }
}
</script>
<style >
html,
body,
#app {
  height: 100%;
}
.el-header,
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  padding: 0px;
  /* line-height: 160px; */
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.tree-col {
  background-color: #d3dce6;
  color: #333;
}
li {
  list-style: none;
  line-height: 30px;
}
.addNote {
  position: relative;
  left: -1%;
}
.hei {
  height: 100% !important;
}
#edui1,
#editor,
#edui1_iframeholder {
  height: 100% !important;
}
</style>
