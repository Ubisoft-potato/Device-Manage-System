<template>
  <div>
    <el-card class="deviceCheck" v-loading="loading" element-loading-text="请求中...">
      <el-table
        :data="reservations.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
        style="width: 100%">
        <el-table-column
          label="预约时间"
          align="center"
          prop="createTime">
          <template slot-scope="scope">
            <el-date-picker
              disabled
              style="width: 159px"
              v-model="scope.row.createTime"
              type="date">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column
          label="设备名称"
          align="center"
          show-overflow-tooltip>
          <template slot-scope="scope">
            {{scope.row.device.name}}
          </template>
        </el-table-column>
        <el-table-column
          label="设备管理员"
          align="center"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button
              disabled
              class="el-icon-user-solid">
              {{scope.row.device.manager.realName}}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          label="预约用户"
          align="center"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <el-button
              disabled
              class="el-icon-user-solid">
              {{scope.row.user.realName}}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          label="预约原因"
          align="center"
          show-overflow-tooltip
          prop="reservationReason">
        </el-table-column>
        <el-table-column
          label="预约使用开始时间"
          align="center"
          prop="startTime">
          <template slot-scope="scope">
            <el-date-picker
              disabled
              style="width: 157px"
              v-model="scope.row.startTime"
              type="date">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column
          label="预约使用结束时间"
          align="center"
          prop="stopTime">
          <template slot-scope="scope">
            <el-date-picker
              disabled
              style="width: 157px"
              v-model="scope.row.stopTime"
              type="date">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column
          label="审核状态"
          align="center"
          prop="state">
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
                        content="查看审核详情"
                        placement="top">
              <el-button type="primary"
                         icon="el-icon-info"
                         @click="handleShowInfo(scope.$index, scope.row)"
                         circle/>
            </el-tooltip>
            <el-tooltip class="item"
                        effect="dark"
                        content="删除预约记录"
                        placement="top">
              <el-button type="danger"
                         icon="el-icon-delete"
                         @click="handleDelete(scope.$index, scope.row)"
                         circle/>
            </el-tooltip>
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
    name: "ReservationTable",
    props: {
      state: String
    },
    watch: {
      state: function () {
        this.queryReservationPage(this.user.id)
      }
    },
    mounted() {
      let user = this.$store.state.user;
      if (user instanceof Object) {
      } else {
        user = JSON.parse(user)
      }
      this.user = user
    },
    data() {
      return {
        loading: false,
        user: {},
        total: 0,
        pageCondition: {
          current: 1,
          size: 5
        },
        reservations: [],
        search: ""
      }
    },
    methods: {
      queryReservationPage(userId) {
        this.$axios.post("reservationDevice/page/" + this.state + "/" + userId, this.pageCondition)
          .then(res => {
            this.reservations = res.data.records
            this.total = res.data.total
          })
          .catch(error => {

          })
      },
      handleShowInfo(index, row) {

      },
      handleDelete(index, row) {
        this.$confirm('此操作将永久删除预约记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.$axios.delete("/reservationDevice/deleteReservation/" + row.id)
            .then(res => {
              if (res.data === true) {
                this.$message({
                  showClose: true,
                  message: "删除成功",
                  type: "success"
                })
                delete this.reservations[index]
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
        this.queryReservationPage()
      },
      currentChange(current) {
        this.pageCondition.current = current
        this.queryReservationPage()
      },
      prevPage() {
        this.pageCondition.current--
        this.queryReservationPage()
      },
      handleSizeChange(size) {
        this.pageCondition.size = size
        this.queryReservationPage()
      }
    }

  }
</script>

<style>

</style>
