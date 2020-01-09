<template>
  <div>
    <el-card class="deviceTable-container"
             v-loading="loading"
             element-loading-text="请求中...">
      <el-table
        :data="devices.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%">
        <el-table-column
          label="设备名称"
          align="center"
          show-overflow-tooltip
          prop="name">
        </el-table-column>
        <el-table-column
          label="设备编号"
          align="center"
          show-overflow-tooltip
          prop="serialNumber">
        </el-table-column>
        <el-table-column
          label="设备类型"
          align="center"
          show-overflow-tooltip
          prop="type">
        </el-table-column>
        <el-table-column
          align="center"
          label="设备负责人">
          <template slot-scope="scope">
            <el-button
              disabled
              class="el-icon-user-solid">
              {{scope.row.manager.realName}}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          label="设备描述"
          align="center"
          show-overflow-tooltip
          prop="description">
        </el-table-column>
        <el-table-column
          label="设备单价"
          align="center"
          prop="price">
        </el-table-column>
        <el-table-column
          label="设备发布时间"
          align="center"
          prop="createTime">
        </el-table-column>
        <el-table-column
          label="设备可用状态"
          align="center"
          prop="createTime">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.availableState"
              active-color="#13ce66"
              inactive-color="#ff4949">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          align="right">
          <template slot="header" slot-scope="scope">
            <el-input
              v-model="search"
              size="mini"
              placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
            <el-tooltip class="item"
                        effect="dark"
                        content="确认修改设备状态"
                        placement="top">
              <el-button type="success"
                         icon="el-icon-check"
                         @click="handleConfirm(scope.$index, scope.row)"
                         circle/>
            </el-tooltip>
            <el-button type="primary" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)" circle/>
            <el-button type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index, scope.row)" circle/>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        class="pagination"
        background
        layout="total, sizes,prev, pager, next"
        :current-page="pageCondition.current"
        :page-size="pageCondition.size"
        :total="total"
        :page-sizes="[5, 10, 15, 20]"
        @next-click="nextPage"
        @prev-click="prevPage"
        @current-change="currentChange"
        @size-change="handleSizeChange">

      </el-pagination>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "DeviceTable",
    mounted() {
      this.queryDevicePage()
    },
    data() {
      return {
        loading: false,
        search: "",
        total: 0,
        pageCondition: {
          current: 1,
          size: 5
        },
        devices: []
      }
    },
    methods: {
      queryDevicePage() {
        this.$axios.post("device/page", this.pageCondition)
          .then(res => {
            this.devices = res.data.records
            this.total = res.data.total
          })
          .catch(error => {

          })
      },
      handleConfirm(index, row) {
        console.log(row);
        this.loading = true
        delete row.manager
        this.$axios.put("device/updateDevice", row)
          .then(res => {
            if (res.data === true) {
              this.$message({
                showClose: true,
                message: "修改成功",
                type: "success"
              })
            } else {
              this.$message({
                showClose: true,
                message: "修改失败，请稍后再试",
                type: "success"
              })
            }
            this.loading = false
          })
          .catch(error => {

          })
      },
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
        this.$confirm('此操作将永久删除该设备, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.$axios.delete("/device/deleteDevice/" + row.id)
            .then(res => {
              if (res.data === true) {
                this.$message({
                  showClose: true,
                  message: "删除成功",
                  type: "success"
                })
                delete this.devices[index]
                this.total--
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
      },
      nextPage() {
        this.pageCondition.current++
        this.queryDevicePage()
      },
      currentChange(current) {
        this.pageCondition.current = current
        this.queryDevicePage()
      },
      prevPage() {
        this.pageCondition.current--
        this.queryDevicePage()
      },
      handleSizeChange(size) {
        this.pageCondition.size = size
        this.queryDevicePage()
      }
    }
  }
</script>

<style>
  .deviceTable-container {
    position: relative;
    top: -10px;
  }

  .pagination {
    position: relative;
    top: 8px;
  }
</style>
