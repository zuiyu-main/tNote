<template>
  <el-container style="height: 500px; border: 1px solid #eee">
    <el-header style="text-align: right; font-size: 12px">
      <el-dropdown>
        <i class="el-icon-setting" style="margin-right: 15px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>查看</el-dropdown-item>
          <el-dropdown-item>新增</el-dropdown-item>
          <el-dropdown-item>删除</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <span>tz</span>
    </el-header>
    <el-container>
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <!-- :default-openeds="['1', '3']" 默认开启列表 -->
        <el-menu :default-openeds="['1']">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-message"></i>文本编辑
            </template>
            <el-menu-item-group>
              <!-- <template slot="title" ref="Uedit">编辑模式</template> -->
              <el-menu-item index="1-1" @click="goFullText()">富本文</el-menu-item>
              <el-menu-item index="1-2" @click="goMarkDown()">markdown</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group>
              <el-menu-item index="1-3" @click="goSocket()">socket</el-menu-item>
            </el-menu-item-group>
            <!-- <el-submenu index="1-4">
              <template slot="title">选项4</template>
              <el-menu-item index="1-4-1">选项4-1</el-menu-item>
            </el-submenu>-->
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-menu"></i>我的文件
            </template>
            <el-menu-item-group>
              <!-- <template slot="title">分组一</template> -->
              <el-menu-item @click="goPrivateDiary()" index="2-1">私密日记</el-menu-item>
              <!-- <el-menu-item @click="goFullText()" index="2-2">私密日记</el-menu-item> -->
            </el-menu-item-group>
            <!-- <el-menu-item-group title="分组2">
              <el-menu-item index="2-3">选项3</el-menu-item>
            </el-menu-item-group>-->
            <!-- <el-submenu index="2-4">
              <template slot="title">选项4</template>
              <el-menu-item index="2-4-1">选项4-1</el-menu-item>
            </el-submenu>-->
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-setting"></i>导航三
            </template>
            <el-menu-item-group>
              <template slot="title">分组一</template>
              <el-menu-item index="3-1">选项1</el-menu-item>
              <el-menu-item index="3-2">选项2</el-menu-item>
            </el-menu-item-group>
            <el-menu-item-group title="分组2">
              <el-menu-item index="3-3">选项3</el-menu-item>
            </el-menu-item-group>
            <el-submenu index="3-4">
              <template slot="title">选项4</template>
              <el-menu-item index="3-4-1">选项4-1</el-menu-item>
            </el-submenu>
          </el-submenu>
        </el-menu>
      </el-aside>

      <el-main class="mainid">
        <router-view />
      </el-main>
    </el-container>
    <!-- <el-footer>2222</el-footer> -->
  </el-container>
</template>
<script>
export default {
  data () {
    return {}
  },
  methods: {
    goFullText () {
      this.$router.push({ path: '/uedit/edit' })
    },
    goMarkDown () {
      this.$router.push({ path: '/markdown' })
    },
    goSocket () {
      // this.$router.push({ path: '/socket' })
    },
    /**
     * websocket 会掉函数
     */
    getConfigResult (message) {
      console.log('回调函数', message)
    },
    goPrivateDiary () {
      this.$router.push({ path: '/privateDiary' })
    }
  },
  mounted () {
    // this.socketApi.sendSock('测试websocket', this.getConfigResult)
  },
  destroyed () {}
}
</script>
<style>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
  height: 830px !important;
}
.el-container {
  height: 1000px !important;
}
#editor {
  height: 100% !important;
}
.el-menu {
  height: 100%;
}
#base {
  height: 750px;
}
/* 编辑器全凭 */
.mainid {
  padding: 0;
}
/* 菜单树艰巨 */
.el-menu-item-group__title {
  padding: 0;
}
</style>
