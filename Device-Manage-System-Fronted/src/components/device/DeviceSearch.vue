<template>
  <div>
    <el-card>
      <el-autocomplete placeholder="请输入内容"
                       style="width: 500px"
                       :fetch-suggestions="querySuggestions"
                       clearable
                       v-model="input"
                       class="input-with-select">
        <el-select v-model="select"
                   clearable
                   slot="prepend" placeholder="请选择查询条件">
          <el-option v-for="(item,index) in searchOptions"
                     :key="index"
                     :label="item.label"
                     :value="item.searchOption"/>
        </el-select>
        <el-button slot="append"
                   icon="el-icon-search"
                   @click="searchDevices"/>
      </el-autocomplete>
    </el-card>
    <el-card>
      <el-table :data="devices.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
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
              style="width: 155px"
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
          <!--          <template slot="header" slot-scope="scope">-->
          <!--            <el-input-->
          <!--              v-model="search"-->
          <!--              size="mini"-->
          <!--              placeholder="输入关键字搜索"/>-->
          <!--          </template>-->
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
  </div>
</template>

<script>
  export default {
    name: "DeviceSearch",
    data() {
      return {
        input: '',
        devices: [],
        searchOptions: [
          {
            label: "设备名",
            searchOption: "deviceName"
          },
          {
            label: "设备管理员",
            searchOption: "deviceManager"
          }
        ],
        select: '',
        centerDialogVisible: false,
        loading: false,
        search: "",
        total: 0,
        pageCondition: {
          current: 1,
          size: 5
        },
        currentDevice: {},
        currentManager: {},
      };
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
        this.searchDevices()
      },
      currentChange(current) {
        this.pageCondition.current = current
        this.searchDevices()
      },
      prevPage() {
        this.pageCondition.current--
        this.searchDevices()
      },
      handleSizeChange(size) {
        this.pageCondition.size = size
        this.searchDevices()
      },
      searchDevices() {
        if (this.select === "" || this.input === "") {
          this.$message({
            showClose: true,
            message: "请输入查询条件",
            type: "warning"
          })
          return
        }
        this.$axios.post("device/search/" + this.select + "/" + this.input, this.pageCondition)
          .then(res => {
            this.devices = res.data.records
            this.total = res.data.total
          })
          .catch(error => {

          });
      },
      querySuggestions(queryString, cb) {
        if (this.select === "deviceManager") {
          if (queryString === "")
            queryString = "all"
          this.$axios.get("users/nameList/" + queryString)
            .then(res => {
              let nameList = []
              res.data.forEach(ele => {
                nameList.push({
                  value: ele
                })
              })
              cb(nameList)
            })
            .catch(error => {

            })
        }
        if (this.select === "deviceName") {
          if (queryString === "")
            queryString = "all"
          this.$axios.get("device/deviceNameList/" + queryString)
            .then(res => {
              let nameList = []
              res.data.forEach(ele => {
                nameList.push({
                  value: ele
                })
              })
              cb(nameList)
            })
            .catch(error => {

            })
        }
        return
      }
    }
  }
</script>

<style>
  .el-select .el-input {
    width: 155px;
  }

  .input-with-select .el-input-group__prepend {
    background-color: #fff;
  }

  .pagination {
    position: relative;
    top: 8px;
  }
</style>
