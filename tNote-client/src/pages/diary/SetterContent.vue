<template>
  <div>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="类别管理" name="first">
        <el-table :data="itemData" style="width: 100%">
          <el-table-column label="分类名称" width="180">
            <template slot-scope="scope">
              <span style="margin-left: 10px">{{ scope.row.title }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">加密</el-button>
              <el-button size="mini" @click="editPassword(scope.$index, scope.row)">重置密码</el-button>

              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="b" name="second">未完待续。。。</el-tab-pane>
      <el-tab-pane label="c" name="third">未完待续。。。</el-tab-pane>
      <el-tab-pane label="d" name="fourth">未完待续。。。</el-tab-pane>
    </el-tabs>
    <el-dialog title="重置密码" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="登录密码" :label-width="formLabelWidth">
          <el-input type="password" v-model="form.lPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新访问密码" :label-width="formLabelWidth">
          <el-input type="password" v-model="form.newPass" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="editPasswordEnd">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import * as DiaryApi from '@/api/diary/Diary'
import { cryptPwd } from '@/utils/Check'
import * as EncryptionApi from '@/api/diary/Encryption'
export default {
  data () {
    return {
      activeName: 'first',
      itemData: [], // 文件夹list
      dialogFormVisible: false,
      form: {
        lPass: '',
        newPass: '',
        targetId: ''
      },
      formLabelWidth: '120px'
    }
  },
  methods: {
    handleClick (tab, event) {
      console.log(tab, event)
    },
    getItem () {
      this.itemData = []
      DiaryApi.getItem().then(res => {
        if (res.code === 200) {
          this.itemData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleEdit (index, row) {
      console.log(index, row)
      this.$prompt('请输入加密密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        // inputPattern: /^[A-Za-z][A-Za-z0-9]{7,99}$/,
        // inputErrorMessage:
        // '密码必须有最少8位大小写数字混合组成，不能以数字开头',
        inputType: 'password'
      })
        .then(({ value }) => {
          this.$confirm('您的密码是' + value + '是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
            .then(() => {
              const params = {
                targetId: row.id,
                password: cryptPwd(value)
              }
              EncryptionApi.encryptionItem(params).then(res => {
                if (res.code === 200) {
                  this.$message({
                    type: 'success',
                    message: '添加密码成功!'
                  })
                } else {
                  this.$message({
                    type: 'error',
                    message: res.msg
                  })
                }
              })
            })
            .catch(() => {
              this.$message({
                type: 'info',
                message: '已取消加密操作'
              })
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          })
        })
    },
    handleDelete (index, row) {
      console.log(index, row)
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          DiaryApi.deleteDiary(row.id).then(res => {
            if (res.code === 200) {
              console.log('删除数据返回', res)
              this.getItem()
              this.$message({
                message: res.msg,
                type: 'success'
              })
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    /**
     * 修改密码
     */
    editPassword (index, row) {
      this.dialogFormVisible = true
      this.form.targetId = row.id
    },
    editPasswordEnd () {
      console.log(this.form)
      this.dialogFormVisible = false
      const parmas = {
        lPass: cryptPwd(this.form.lPass),
        newPass: cryptPwd(this.form.newPass),
        targetId: this.form.targetId
      }
      EncryptionApi.resetPassword(parmas).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.$message({
            message: res.msg,
            type: 'success'
          })
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          })
        }
      })
    }
  },
  mounted () {
    this.getItem()
  }
}
</script>
