<template>
  <el-card>
    <el-table
      :data="costSettlements.filter(data => !search || data.deviceName.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%">
      <el-table-column
        label="设备名称"
        prop="deviceName"
        align="center">
      </el-table-column>
      <el-table-column
        label="设备序列号"
        prop="deviceSerialNumber"
        align="center">
      </el-table-column>
      <el-table-column
        label="设备类型"
        prop="deviceType"
        align="center">
      </el-table-column>
      <el-table-column
        label="设备使用开始时间"
        align="center"
        prop="createTime">
        <template slot-scope="scope">
          <el-date-picker
            disabled
            style="width: 160px"
            v-model="scope.row.reservationStartTime"
            type="date">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column
        label="设备使用结束时间"
        align="center"
        prop="createTime">
        <template slot-scope="scope">
          <el-date-picker
            disabled
            style="width: 160px"
            v-model="scope.row.reservationStopTime"
            type="date">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column
        label="费用"
        align="center"
        prop="costSum">
        <template slot-scope="scope">
          {{scope.row.costSum}} 元
        </template>
      </el-table-column>
      <el-table-column
        label="结算状态"
        prop="state"
        align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.state"> 已结算 </span>
          <span v-else> 未结算</span>
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
                      content="确认已结算"
                      placement="top">
            <el-button type="success" icon="el-icon-check"
                       @click="handleConfirm(scope.$index, scope.row)" circle/>
          </el-tooltip>
          <el-tooltip class="item"
                      effect="dark"
                      content="查看租用人信息"
                      placement="top">
            <el-button type="primary" icon="el-icon-info"
                       @click="handleShowInfo(scope.$index, scope.row)" circle/>
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
    <el-dialog title="设备租用人信息" :visible.sync="dialogVisible" width="510px">
      <el-row :gutter="10" align="center">
        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">租用人姓名</el-col>
        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{rentUser.realName}}</el-col>
      </el-row>
      <el-row :gutter="10" align="center">
        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">租用人所在学院</el-col>
        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{rentUser.institute}}</el-col>
      </el-row>
      <el-row :gutter="10" align="center">
        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">租用人联系电话</el-col>
        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{rentUser.telPhone}}</el-col>
      </el-row>
      <el-row :gutter="10" align="center">
        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11" class="">租用人学号或工号</el-col>
        <el-col :xs="4" :sm="6" :md="8" :lg="9" :xl="11">{{rentUser.workId}}</el-col>
      </el-row>
    </el-dialog>
  </el-card>
</template>

<script>
  export default {
    name: "CostSettlementManage",
    mounted() {
      this.queryCostPage()
    },
    data() {
      return {
        dialogVisible: false,
        rentUser: {},
        search: "",
        costSettlements: [],
        total: 0,
        pageCondition: {
          current: 1,
          size: 5
        },
      }
    },
    methods: {
      queryCostPage() {
        this.$axios.post("costSettlement/page/all", this.pageCondition)
          .then(res => {
            this.costSettlements = res.data.records
            this.total = res.data.total
          })
          .catch(error => {

          })
      },
      handleConfirm(index, row) {
        row.state = true
        this.$axios.put("costSettlement/update", row)
          .then(res => {
            if (res.data === true) {
              this.$message({
                showClose: true,
                message: "确认结算成功",
                type: "success"
              })
            } else {
              this.$message({
                showClose: true,
                message: "结算失败，请稍后再试",
                type: "error"
              })
            }
          })
          .catch(error => {

          })
      },
      handleShowInfo(index, row) {
        this.$axios.get("users/info/" + row.userId)
          .then(res => {
            this.rentUser = res.data
            this.dialogVisible = true
          })
          .catch(error => {

          });

      },
      nextPage() {
        this.pageCondition.current++
        this.queryCostPage()
      },
      currentChange(current) {
        this.pageCondition.current = current
        this.queryCostPage()
      },
      prevPage() {
        this.pageCondition.current--
        this.queryCostPage()
      },
      handleSizeChange(size) {
        this.pageCondition.size = size
        this.queryCostPage()
      }
    }
  }
</script>

<style>
  .pagination {
    position: relative;
    top: 8px;
  }
</style>
