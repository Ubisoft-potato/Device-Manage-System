<template>
  <el-card class="UserTable-container" v-loading="loading" element-loading-text="请求中...">
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
      :total="total">
    </el-pagination>
  </el-card>
</template>

<script>
  export default {
    name: "UserTable",
    props: {
      authority: String
    },
    data() {
      return {
        loading: false,
        total: 0,
        tableData: [],
        search: ''
      }
    },
    watch: {
      authority: function () {
        console.log(this.authority)
        this.$axios.post("/users/page/" + this.authority,
          {
            current: "1",
            size: "10"
          }).then(res => {
          let data = res.data;
          console.log(data)
          this.tableData = data["records"]
          this.total = data["total"]
        }).catch(error => {

        })
      }
    },
    methods: {
      handleEdit(index, row) {
        console.log(index, row);
        dialog = true
      },
      //删除用户
      handleDelete(index, row) {
        console.log(index, row, row.id);
        this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.$axios.delete("/users/delete/" + row.id)
            .then(res => {
              if (res.data === true) {
                this.$message({
                  showClose: true,
                  message: "删除成功",
                  type: "success"
                })
                delete this.tableData[index]
              } else {
                this.$message({
                  showClose: true,
                  message: "删除失败,请稍后再试",
                  type: "error"
                })
              }
              this.loading = false
            })
            .catch(error => {
              this.loading = false
            })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
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
