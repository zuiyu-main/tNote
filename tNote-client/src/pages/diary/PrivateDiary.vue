<template>
  <div>
    <el-container style="height:1000px">
      <el-header style="text-align: right; font-size: 12px">
        <el-dropdown>
          <i class="el-icon-setting" style="margin-right: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>查看</el-dropdown-item>
            <el-dropdown-item>新增</el-dropdown-item>
            <el-dropdown-item>删除</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <span>王小虎</span>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <!-- <ol> -->
          <li v-for="(item,index) in data " :key="index">
            <el-button type="text">{{item.label}}</el-button>
          </li>
          <!-- </ol> -->
        </el-aside>
        <el-main>Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
export default {
  name: 'diary',
  data () {
    return {
      data: [
        {
          label: 'test1'
        },
        {
          label: 'test12'
        },
        {
          label: 'test13'
        }
      ]
    }
  },
  methods: {}
}
</script>
<style >
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
  line-height: 160px;
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
</style>
