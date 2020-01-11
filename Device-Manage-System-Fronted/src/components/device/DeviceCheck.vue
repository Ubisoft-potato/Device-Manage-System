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
          prop="reservationReason">
        </el-table-column>
        <el-table-column
          label="预约使用开始时间"
          align="center"
          prop="startTime">
        </el-table-column>
        <el-table-column
          label="预约使用结束时间"
          align="center"
          prop="stopTime">
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
            <el-button type="success" icon="el-icon-check" @click="handleEdit(scope.$index, scope.row)" circle/>
            <el-button type="primary" icon="el-icon-info" @click="handleDelete(scope.$index, scope.row)" circle/>
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
    name: "DeviceCheck",
    mounted() {
      this.queryReservationPage()
    },
    data() {
      return {
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
      queryReservationPage() {
        this.$axios.post("reservationDevice/page", this.pageCondition)
          .then(res => {
            this.reservations = res.data.records
            this.total = res.data.total
          })
          .catch(error => {

          })
      },
      handleEdit(index, row) {

      },
      handleDelete(index, row) {

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
