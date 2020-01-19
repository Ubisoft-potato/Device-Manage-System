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
          <el-button type="primary"
                     :disabled="scope.row.state"
                     icon="el-icon-s-finance"
                     @click="handleCost(scope.$index, scope.row)" circle/>
          <el-button type="danger" icon="el-icon-delete"
                     :disabled="!scope.row.state"
                     @click="handleDelete(scope.$index, scope.row)" circle/>
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
    <el-dialog title="租用结算详情" :visible.sync="dialogVisible" width="510px">
      请及时联系设备管理员( {{currentManager.deviceManagerName}} ), 电话( {{currentManager.deviceManagerContact}} )进行结算
    </el-dialog>
  </el-card>
</template>

<script>
  export default {
    name: "CostSettlement",
    mounted() {
      let user = this.$store.state.user;
      if (user instanceof Object) {
      } else {
        user = JSON.parse(user)
      }
      this.queryCostPage(user.id)
    },
    data() {
      return {
        dialogVisible: false,
        search: "",
        costSettlements: [],
        total: 0,
        pageCondition: {
          current: 1,
          size: 5
        },
        currentManager: {}
      }
    },
    methods: {
      queryCostPage(userId) {
        this.$axios.post("costSettlement/page/" + userId, this.pageCondition)
          .then(res => {
            this.costSettlements = res.data.records
            this.total = res.data.total
          })
          .catch(error => {

          })
      },
      handleDelete(index, row) {
        this.$confirm('此操作将永久删除该结算记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.$axios.delete("/costSettlement/deleteDevice/" + row.id)
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
      handleCost(index, row) {
        this.currentManager = row
        this.dialogVisible = true
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
