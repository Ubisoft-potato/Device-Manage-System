<template>
  <div>
    <el-card class="deviceCheck">
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
              style="width: 160px"
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
              style="width: 158px"
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
              style="width: 158px"
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
            <el-button type="success" :disabled="scope.row.state!=='CHECKING'" icon="el-icon-check"
                       @click="handleAgree(scope.$index, scope.row)" circle/>
            <el-button type="danger" :disabled="scope.row.state!=='CHECKING'" icon="el-icon-close"
                       @click="handleDisagree(scope.$index, scope.row)" circle/>
            <el-button type="primary" icon="el-icon-info"
                       @click="handleShowInfo(scope.$index, scope.row)" circle/>
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
      title="预约详情"
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
      </span>
    </el-dialog>
    <el-dialog
      title="拒绝租用申请"
      :visible.sync="rejectDialogVisible"
      width="30%"
      center>
      <el-form>
        <el-form-item label="拒绝理由:">
          <el-input
            type="textarea"
            aria-required="true"
            :autosize="{ minRows: 2, maxRows: 4}"
            v-model="failReason"/>
        </el-form-item>
        <el-form-item style="position: relative; top: 20px">
          <el-button type="primary" @click="confirmReject">确认拒绝
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "DeviceCheck",
    mounted() {
      this.queryReservationPage()
    },
    data() {
      return {
        centerDialogVisible: false,
        rejectDialogVisible: false,
        reservationForm: {},
        total: 0,
        pageCondition: {
          current: 1,
          size: 5
        },
        failReason: "",
        reservations: [],
        search: "",
        currentDevice: {},
        currentManager: {},
        currentUser: {},
        currentReservation: {}
      }
    },
    methods: {
      queryReservationPage() {
        this.$axios.post("reservationDevice/page", this.pageCondition)
          .then(res => {
            this.reservations = res.data.records
            this.total = res.data.total
          })
          .catch(error => {

          })
      },
      confirmReject() {
        this.reservationForm.state = "CHECK_FAIL"
        this.reservationForm.failReason = this.failReason
        this.$axios.put("/reservationDevice/updateReservation", this.reservationForm)
          .then(res => {
            if (res.data) {
              this.$message({
                showClose: true,
                message: "审核成功",
                type: "success"
              })
            } else {
              this.$message({
                showClose: true,
                message: "审核失败，请稍后再试",
                type: "error"
              })
            }
            this.rejectDialogVisible = false
          })
          .catch(error => {

          })
      },
      handleAgree(index, row) {
        row.state = "CHECK_SUCCESS"
        row.deviceId = row.device.id
        row.userId = row.user.id
        // console.log(row)
        this.$axios.put("/reservationDevice/updateReservation", row)
          .then(res => {
            if (res.data) {
              this.$message({
                showClose: true,
                message: "审核成功",
                type: "success"
              })
            } else {
              this.$message({
                showClose: true,
                message: "审核失败，请稍后再试",
                type: "error"
              })
            }
          })
          .catch(error => {

          })
      },
      handleShowInfo(index, row) {
        this.currentDevice = row.device
        this.currentManager = row.device.manager
        this.currentUser = row.user
        this.currentReservation = row
        this.centerDialogVisible = true
      },
      handleDisagree(index, row) {
        this.reservationForm = row
        this.rejectDialogVisible = true
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
  .deviceCheck {
    position: relative;
    top: -10px;
  }

  .pagination {
    position: relative;
    top: 8px;
  }
</style>
