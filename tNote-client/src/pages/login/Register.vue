<template>
  <div>
    <div class="el-form-regis">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleForm"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="登录名" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
          <el-tag
            class="close-tag"
            v-if="showTag"
            closable
            type="danger"
            @close="handleClose()"
            :disable-transitions="false"
          >用户名重复</el-tag>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="ruleForm.realName"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
          <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import * as UserService from '@/api/user/login'
export default {
  data () {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      // 表单数据
      ruleForm: {
        name: '',
        pass: '',
        checkPass: '',
        realName: ''
      },
      rules: {
        name: [
          {
            required: true,
            message: '用户名必须为英文或者数字',
            trigger: 'blur',
            pattern: /^[A-Za-z|0-9]+$/
          },
          { min: 5, max: 11, message: '长度在 5 到 11 个字符', trigger: 'blur' }
        ],
        pass: [
          { required: true, validator: validatePass, trigger: 'blur' },
          { min: 6, message: '长度在最少6个字节', trigger: 'blur' }
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ],
        realName: [
          {
            required: true,
            message: '真实姓名必须全部为中文字符，长度在4个字符之内',
            pattern: /^[\u4E00-\u9FA5]{1,4}$/,
            trigger: 'blur'
          }
        ]
      },
      showTag: false
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          const params = {
            userName: this.ruleForm.name,
            password: this.ruleForm.pass,
            password2: this.ruleForm.checkPass,
            realName: this.ruleForm.realName,
            orgId: 10010
          }
          UserService.userRegister(params).then(res => {
            if (res.code === 200) {
              this.$message({
                message: '注册成功',
                type: 'success'
              })
              const loginInfo = {
                userName: this.ruleForm.name,
                password: this.ruleForm.pass,
                resource: '记住密码'
              }
              localStorage.setItem('loginInfo', JSON.stringify(loginInfo))
              this.$router.push({ path: '/' })
            } else if (res.code === 500) {
              this.$message({
                message: res.msg,
                type: 'warning'
              })
            } else if (res.code === 205) {
              this.$message({
                message: res.msg,
                type: 'warning'
              })
              this.showTag = true
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    /**
     * 验证用户名弹出提示
     */
    handleClose () {
      this.showTag = false
    }
  }
}
</script>
<style >
/**label颜色 */
.el-form-item__label {
  color: #606266 !important;
}
.el-form-regis {
  width: 50%;
}
/**.close-tag提示tag */
.close-tag {
  position: absolute;
  top: 9%;
}
</style>
