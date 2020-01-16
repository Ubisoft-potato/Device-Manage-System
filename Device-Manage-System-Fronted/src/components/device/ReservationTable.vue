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
          <template slot-scope="scope">
            <span v-if="scope.row.state === 'CHECK_FAIL'">
              审核未通过
            </span>
            <span v-else-if="scope.row.state === 'CHECK_SUCCESS'">
              审核通过
            </span>
            <span v-else="scope.row.state ==='CHECKING'">
              审核中
            </span>
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
    <el-dialog
      title="预约状态详情"
      :visible.sync="centerDialogVisible"
      width="30%"
      center>
        <span>
          <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">设备名称</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentDevice.name}}</el-col>
          </el-row>
          <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">设备类型</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentDevice.type}}</el-col>
          </el-row>
            <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">设备序列号</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentDevice.serialNumber}}</el-col>
          </el-row>
          <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">设备租用单价</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentDevice.price}} 元/天</el-col>
          </el-row>
          <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">设备描述</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentDevice.description}}</el-col>
          </el-row>
          <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">设备管理员</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentManager.realName}}</el-col>
          </el-row>
          <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">管理员联系电话</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentManager.telPhone}}</el-col>
          </el-row>
          <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">管理员所在院系</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentManager.institute}}</el-col>
          </el-row>
          <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">申请人</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentUser.realName}}</el-col>
          </el-row>
          <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">申请人所在院系</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentUser.institute}}</el-col>
          </el-row>
          <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">申请人联系电话</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentUser.telPhone}}</el-col>
          </el-row>
           <el-row :gutter="10" align="center">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">申请原因</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentReservation.reservationReason}}</el-col>
          </el-row>
          <el-row :gutter="10" align="center" v-if="this.state==='CHECK_FAIL'">
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">拒绝申请原因</el-col>
            <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{currentReservation.failReason}}</el-col>
          </el-row>

      </span>
    </el-dialog>
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
        centerDialogVisible: false,
        loading: false,
        user: {},
        total: 0,
        pageCondition: {
          current: 1,
          size: 5
        },
        reservations: [],
        search: "",
        currentManager: {},
        currentUser: {},
        currentReservation: {},
        currentDevice: {}
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
        this.centerDialogVisible = true
        this.currentReservation = row
        this.currentManager = row.device.manager
        this.currentDevice = row.device
        this.currentUser = row.user
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
