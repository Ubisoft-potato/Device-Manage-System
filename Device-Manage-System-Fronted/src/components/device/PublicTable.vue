<template>
  <el-card class="public-container">
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
        <template slot-scope="scope">
          <el-date-picker
            disabled
            style="width: 135px"
            v-model="scope.row.createTime"
            type="date">
          </el-date-picker>
        </template>
      </el-table-column>
      <el-table-column
        label="设备可用状态"
        align="center"
        prop="createTime">
        <template slot-scope="scope">
          <el-switch disabled
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
                      content="查看详情"
                      placement="top">
            <el-button type="primary" icon="el-icon-info" @click="handleShowInfo(scope.$index, scope.row)" circle/>
          </el-tooltip>
          <el-tooltip class="item"
                      effect="dark"
                      content="预约设备"
                      placement="top">
            <el-button type="success"
                       :disabled="!scope.row.availableState"
                       icon="el-icon-s-claim"
                       @click="handleDeviceOrder(scope.$index, scope.row)"
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
    <el-dialog
      title="设备详情"
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
      </span>
    </el-dialog>
  </el-card>
</template>

<script>
  export default {
    name: "PublicTable",
    mounted() {
      this.queryDevicePage()
    },
    data() {
      return {
        centerDialogVisible: false,
        loading: false,
        search: "",
        total: 0,
        pageCondition: {
          current: 1,
          size: 5
        },
        devices: [],
        currentDevice: {},
        currentManager: {}
      }
    },
    methods: {
      handleDeviceOrder(index, row) {
        console.log(row)
        this.$router.push({
          name: 'DeviceReservation',
          params: {
            device: row
          }
        })
      },
      handleShowInfo(index, row) {
        console.log(row)
        this.currentDevice = row
        this.currentManager = row.manager
        this.centerDialogVisible = true;
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
      },
      queryDevicePage() {
        this.$axios.post("device/page", this.pageCondition)
          .then(res => {
            this.devices = res.data.records
            this.total = res.data.total
          })
          .catch(error => {

          })
      }
    }
  }
</script>

<style>
  .public-container {
    position: relative;
    top: -10px;
  }

  .pagination {
    position: relative;
    top: 8px;
  }
</style>
