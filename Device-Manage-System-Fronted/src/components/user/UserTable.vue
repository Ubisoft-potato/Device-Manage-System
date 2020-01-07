<template>
  <el-card class="UserTable-container">
    <el-table
      :data="tableData.filter(data => !search || data.realName.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%">
      <el-table-column
        label="用户名"
        prop="username">
      </el-table-column>
      <el-table-column
        label="姓名"
        prop="realName">
      </el-table-column>
      <el-table-column
        label="工号或学号"
        prop="workId">
      </el-table-column>
      <el-table-column
        label="所属学院"
        prop="institute">
      </el-table-column>
      <el-table-column
        label="联系电话"
        prop="telPhone">
      </el-table-column>
      <el-table-column
        label="创建日期"
        prop="createTime">
      </el-table-column>
      <el-table-column
        align="right">
        <template slot="header" slot-scope="scope">
          <el-input
            v-model="search"
            size="mini"
            placeholder="输入用户姓名搜索"/>
        </template>
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)" circle/>
          <el-button type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index, scope.row)" circle/>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
      background
      layout="prev, pager, next"
      :total="1000">
    </el-pagination>
  </el-card>
</template>

<script>
  export default {
    name: "UserTable",
    data() {
      return {
        tableData: [
          {
            "id": 1213814552303550465,
            "username": "admin1",
            "password": null,
            "workId": "1605441111",
            "realName": "李华",
            "telPhone": "1354253644",
            "institute": "材料学院",
            "createTime": "2020-01-05T21:28:36",
            "authorities": [
              "ADMIN",
              "USER"
            ],
            "isSuperAdmin": false
          }
        ],
        search: ''
      }
    },
    created() {

    },
    methods: {
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      }
    }
  }
</script>

<style>
  .UserTable-container {
    position: relative;
    top: -10px;
  }

  .pagination {
    position: relative;
    top: 8px;
  }
</style>
