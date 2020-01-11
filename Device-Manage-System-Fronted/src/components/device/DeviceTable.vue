<template>
  <div>
    <el-card class="deviceTable-container"
             v-loading="loading"
             element-loading-text="请求中...">
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
        </el-table-column>
        <el-table-column
          label="设备可用状态"
          align="center"
          prop="createTime">
          <template slot-scope="scope">
            <el-switch
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
                        content="确认修改设备状态"
                        placement="top">
              <el-button type="success"
                         icon="el-icon-check"
                         @click="handleConfirm(scope.$index, scope.row)"
                         circle/>
            </el-tooltip>
            <el-button type="primary" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)" circle/>
            <el-button type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index, scope.row)" circle/>
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
    <el-dialog title="编辑设备信息" :visible.sync="dialogFormVisible" width="510px" v-loading="dialogLoading">
      <el-form status-icon label-width=auto :model="device" ref="user" :rules="rules">
        <el-form-item label='设备名称' prop="name">
          <el-input placeholder="请输入设备名称" v-model="device.name"/>
        </el-form-item>
        <el-form-item label='设备类型' prop="type">
          <el-input placeholder="请输入设备类型" v-model="device.type"/>
        </el-form-item>
        <el-form-item label='设备序列号' prop="serialNumber">
          <el-input placeholder="请输入设备序列号" v-model="device.serialNumber"/>
        </el-form-item>
        <el-form-item label='设备描述' prop="description">
          <el-input placeholder="请输入设备描述" v-model="device.description"/>
        </el-form-item>
        <el-form-item label="负责人" prop="manager">
          <el-select style="left: 1px; width: 375px"
                     v-model="device.manager"
                     @change="selectGet"
                     clearable
                     filterable
                     placeholder="请选择负责人">
            <el-option
              v-for="(item,index) in admins"
              :key="index"
              :label="item.realName+' : '+ item.institute"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label='设备单价' prop="price">
          <el-input placeholder="请输入单价" v-model="device.price"/>
        </el-form-item>
        <el-form-item label="当前是否可用" prop="available">
          <el-switch
            style="position: relative;"
            v-model="device.availableState"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmEdit">确 定 修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "DeviceTable",
    mounted() {
      this.queryDevicePage()
      let user = this.$store.state.user;
      if (user instanceof Object) {
      } else {
        user = JSON.parse(user)
      }
      this.$axios.get("/users/list/" + user.institute)
        .then(res => {
          console.log(res.data)
          this.admins = res.data.map(item => {
            return {
              id: item.id,
              realName: item.realName,
              institute: item.institute
            }
          });

        })
        .catch(error => {

        })
    },
    data() {
      const validateNull = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('此为必填项'));
        } else {
          callback()
        }
      };
      return {
        dialogLoading: false,
        dialogFormVisible: false,
        loading: false,
        search: "",
        total: 0,
        pageCondition: {
          current: 1,
          size: 5
        },
        admins: [],
        devices: [],
        device: {},
        rules: {
          name: [{validator: validateNull, trigger: 'blur'}],
          type: [{validator: validateNull, trigger: 'blur'}],
          serialNumber: [{validator: validateNull, trigger: 'blur'}],
          manager: [{validator: validateNull, trigger: 'blur'}],
          price: [{validator: validateNull, trigger: 'blur'}],
          description: [{validator: validateNull, trigger: 'blur'}]
        },

      }
    },
    methods: {
      selectGet(userId) {
        console.log("userId : ", userId)
        this.device.manager = userId
      },
      queryDevicePage() {
        this.$axios.post("device/page", this.pageCondition)
          .then(res => {
            this.devices = res.data.records
            this.total = res.data.total
          })
          .catch(error => {

          })
      },
      confirmEdit() {
        console.log(this.device)
        this.dialogLoading = true
        if (/.*[\u4e00-\u9fa5]+.*$/.test(this.device.manager)) {
          console.log("未更新管理员")
          delete this.device.manager
        }
        this.$axios.put("/device/updateDevice", this.device)
          .then(res => {
            if (res.data) {
              this.$message({
                showClose: true,
                message: "更新成功",
                type: "success"
              })
            } else {
              this.$message({
                showClose: true,
                message: "更新失败，请稍后再试",
                type: "error"
              })
            }
            this.dialogLoading = false
          })
          .catch(error => {
            this.dialogLoading = false
          })
      },
      handleConfirm(index, row) {
        console.log(row);
        this.loading = true
        delete row.manager
        this.$axios.put("device/updateDevice", row)
          .then(res => {
            if (res.data === true) {
              this.$message({
                showClose: true,
                message: "修改成功",
                type: "success"
              })
            } else {
              this.$message({
                showClose: true,
                message: "修改失败，请稍后再试",
                type: "success"
              })
            }
            this.loading = false
          })
          .catch(error => {

          })
      },
      handleEdit(index, row) {
        console.log(index, row);
        this.dialogFormVisible = true
        this.$axios.get("/device/getDevice/" + row.id)
          .then(res => {
            this.device = res.data
            this.device.manager = this.device.manager.realName + " : " + this.device.manager.institute
            console.log(this.device)
          })
          .catch(error => {

          })
      },
      handleDelete(index, row) {
        console.log(index, row);
        this.$confirm('此操作将永久删除该设备, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.loading = true
          this.$axios.delete("/device/deleteDevice/" + row.id)
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
      }
    }
  }
</script>

<style>
  .deviceTable-container {
    position: relative;
    top: -10px;
  }

  .pagination {
    position: relative;
    top: 8px;
  }
</style>
