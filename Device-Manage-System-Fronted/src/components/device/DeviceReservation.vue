<template>
  <div>
    <el-card class="reservation-container" :loading="reservationLoading" element-loading-text="预约中...">
      <h3 class="reservation-title">预约设备</h3>
      <el-form :model="reservation" :rules="rules" ref="reservation" label-width="100px">
        <el-form-item label="设备名称">
          <el-input v-model="device.name" disabled/>
        </el-form-item>
        <el-form-item label="设备序列号">
          <el-input v-model="device.serialNumber" disabled/>
        </el-form-item>
        <el-form-item label="设备类型">
          <el-input v-model="device.type" disabled/>
        </el-form-item>
        <el-form-item label="租用单价" prop="price">
          <el-input v-model="device.price" disabled>
            <template slot="append">元/天</template>
          </el-input>
        </el-form-item>
        <el-form-item label="租用原因" prop="reservationReason">
          <el-input placeholder="请输入租用原因" v-model="reservation.reservationReason">
          </el-input>
        </el-form-item>
        <el-form-item label="租用时间段" prop="dateRange">
          <div>
            <el-date-picker
              style="width: 372px"
              v-model="reservation.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item style="position: relative; top: 20px">
          <el-button style="position:relative; left: -80px" type="primary" @click="submitForm('reservation')">立即创建
          </el-button>
          <el-button type="danger" @click="resetForm('reservation')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "DeviceReservation",
    mounted() {
      let user = this.$store.state.user;
      if (user instanceof Object) {
      } else {
        user = JSON.parse(user)
      }
      this.device = this.$route.params.device
      this.reservation.deviceId = this.device.id
      this.reservation.userId = user.id
      console.log(this.reservation.userId)
    },
    data() {
      return {
        reservationLoading: false,
        device: {
          name: "",
          serialNumber: "",
          type: "",
          price: ""
        },
        reservation: {
          deviceId: "",
          userId: "",
          reservationReason: "",
          startTime: "",
          stopTime: "",
          dateRange: []
        },
        rules: {
          reservationReason: [{required: true, message: '请输入租用原因', trigger: 'blur'}],
          dateRange: [{type: 'array', required: true, message: '请选择租用时间段', trigger: 'change'}]
        }
      }
    },
    methods: {
      submitForm(reservation) {
        this.$refs[reservation].validate((valid) => {
          if (valid) {
            this.reservationLoading = true
            console.log(this.reservation)
            this.reservation.startTime = this.reservation.dateRange[0]
            this.reservation.stopTime = this.reservation.dateRange[1]
            delete this.reservation.dateRange
            this.$axios.post("/reservationDevice/addNewReservation", this.reservation)
              .then(res => {
                if (res.data === true) {
                  this.$message({
                    showClose: true,
                    message: "预约成功",
                    type: "success"
                  })
                } else {
                  this.$message({
                    showClose: true,
                    message: "预约失败，请刷新设备状态",
                    type: "success"
                  })
                }
                this.reservationLoading = false
              })
              .catch(error => {
                console.log(error.state)
                this.reservationLoading = false
              })
          } else {
            console.log('error submit!!');
            return false;
          }
        })
      },
      resetForm(reservation) {
        this.$refs[reservation].resetFields()
      }
    }
  }
</script>

<style>
  .reservation-container {
    position: relative;
    top: -40px;
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 510px;
    left: -60px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .reservation-title {
    margin: 0 auto 40px auto;
    text-align: center;
    color: #505458;
  }

</style>
