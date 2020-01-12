<template>
  <div>
    <el-container class="history-container">
      <el-aside>
        <el-menu router>
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-document"/>
              <span>设备审核状态</span>
            </template>
            <el-menu-item @click="showCheckingTable">
              <i class="el-icon-loading"/>审核中
            </el-menu-item>
            <el-menu-item @click="showCheckSuccessTable">
              <i class="el-icon-check"/>审核通过
            </el-menu-item>
            <el-menu-item @click="showCheckFailTable">
              <i class="el-icon-close"/>审核未通过
            </el-menu-item>
          </el-submenu>
          <el-menu-item @click="costSettlement">
            <i class="el-icon-s-finance"/>
            <span slot="title">设备费用结算</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <el-main>
        <ReservationTable :state="state" v-show="showReservationTable"/>
        <cost-settlement v-show="showCostSettlement"/>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import ReservationTable from "./ReservationTable";
  import CostSettlement from "./CostSettlement";

  export default {
    name: "ReservationHistory",
    components: {CostSettlement, ReservationTable},
    data() {
      return {
        state: "",
        showReservationTable: true,
        showCostSettlement: false
      }
    },
    methods: {
      costSettlement() {
        this.showReservationTable = false
        this.showCostSettlement = true
      },
      showCheckingTable() {
        this.showReservationTable = true
        this.showCostSettlement = false
        this.state = "CHECKING"
      },
      showCheckSuccessTable() {
        this.showReservationTable = true
        this.showCostSettlement = false
        this.state = "CHECK_SUCCESS"
      },
      showCheckFailTable() {
        this.showReservationTable = true
        this.showCostSettlement = false
        this.state = "CHECK_FAIL"
      }
    }
  }
</script>

<style>
  .history-container {
    position: relative;
    top: -55px;
  }
</style>
