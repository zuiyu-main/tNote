<template>
  <el-table :data="diaryData" style="width: 100%">
    <el-table-column label="日期" width="180">
      <template slot-scope="scope">
        <i class="el-icon-time"></i>
        <span style="margin-left: 10px">{{ scope.row.gmtCreate }}</span>
      </template>
    </el-table-column>
    <el-table-column label="作者" width="180">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>姓名: {{ scope.row.author }}</p>
          <p>id: {{ scope.row.authorId }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.author }}</el-tag>
          </div>
        </el-popover>
      </template>
    </el-table-column>
    <el-table-column label="名称" min-width="280">
      <template slot-scope="scope">
        <span style="margin-left: 10px">{{ scope.row.title }}</span>

        <!-- <el-popover trigger="hover" placement="top">
          <p>姓名: {{ scope.row.name }}</p>
          <p>住址: {{ scope.row.address }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">{{ scope.row.name }}</el-tag>
          </div>
        </el-popover>-->
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
export default {
  props: {
    diaryData: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      // tableData: [
      //   {
      //     date: '2016-05-02',
      //     name: '王小虎',
      //     address: '上海市普陀区金沙江路 1518 弄',
      //     desc: '111'
      //   }
      // ]
    }
  },
  methods: {
    handleEdit (index, row) {
      console.log(index, row)
    },
    handleDelete (index, row) {
      console.log(index, row)
    }
  }
}
</script>
