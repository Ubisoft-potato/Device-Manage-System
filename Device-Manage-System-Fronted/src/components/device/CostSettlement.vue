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
      queryCostPage(userId) {
        this.$axios.post("costSettlement/page/" + userId, this.pageCondition)
          .then(res => {
            this.costSettlements = res.data.records
            this.total = res.data.total
          })
          .catch(error => {

          })
      },
      handleShowInfo(index, row) {
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
