<template>
  <el-card class="addDevice-container">
    <h3 class="addDevice-title">添加设备</h3>
    <el-form :model="device" :rules="rules" ref="device" label-width="100px">
      <el-form-item label="设备名称" prop="name">
        <el-input placeholder="请输入设备名称" v-model="device.name"/>
      </el-form-item>
      <el-form-item label="设备序列号" prop="serialNumber">
        <el-input placeholder="请输入设备序列号" v-model="device.serialNumber"/>
      </el-form-item>
      <el-form-item label="设备类型" prop="type">
        <el-input placeholder="请输入设备类型" v-model="device.type"/>
      </el-form-item>
      <el-form-item label="负责人" prop="manager">
        <el-select style="left: 1px; width: 360px"
                   v-model="device.manager"
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
      <el-form-item label="设备描述" prop="description">
        <el-input type="textarea"
                  :autosize="{ minRows: 2, maxRows: 4}"
                  maxlength="500"
                  show-word-limit
                  placeholder="请输入设备描述"
                  v-model="device.description"/>
      </el-form-item>
      <el-form-item label="租用单价" prop="price">
        <el-input placeholder="请选择设备租用单价" v-model="device.price"/>
      </el-form-item>
      <el-form-item label="当前是否可用" prop="available">
        <el-switch
          style="position: relative;right: 160px"
          v-model="device.available"
          active-color="#13ce66"
          inactive-color="#ff4949">
        </el-switch>
      </el-form-item>
      <el-form-item style="position: relative; top: 20px">
        <el-button style="position:relative; left: -80px" type="primary" @click="submitForm('device')">立即创建</el-button>
        <el-button type="danger" @click="resetForm('device')">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
  export default {
    name: "AddDevice",
    data() {
      return {
        admins: [],
        device: {
          name: "",
          serialNumber: "",
          type: "",
          manager: "",
          description: "",
          price: "",
          available: false,
        },
        rules: {
          name: [{required: true, message: '请输入设备名称', trigger: 'blur'}],
          serialNumber: [{required: true, message: '请输入设备序列号', trigger: 'blur'}],
          type: [{required: true, message: '请输入设备类型', trigger: 'blur'}],
          manager: [{required: true, message: '请选择设备负责人', trigger: 'blur'}],
          description: [{required: true, message: '请输入设备描述', trigger: 'blur'}],
          price: [{required: true, message: '请选择设备单价', trigger: 'blur'}]
        }
      }
    },
    mounted() {
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
    methods: {
      submitForm(device) {
        this.$refs[device].validate((valid) => {
          if (valid) {
            alert('submit!');
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(device) {
        this.$refs[device].resetFields();
        this.device.available = false
      }
    }
  }
</script>

<style>
  .addDevice-container {
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

  .addDevice-title {
    margin: 0 auto 40px auto;
    text-align: center;
    color: #505458;
  }
</style>
