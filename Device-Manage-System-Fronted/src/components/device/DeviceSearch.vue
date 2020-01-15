<template>
  <div>
    <el-card>
      <el-input placeholder="请输入内容" v-model="input" class="input-with-select">
        <el-select v-model="select"
                   clearable
                   slot="prepend" placeholder="请选择查询条件">
          <el-option v-for="(item,index) in searchOptions"
                     :key="index"
                     :label="item.label"
                     :value="item.searchOption"/>
        </el-select>
        <el-button slot="append" icon="el-icon-search"/>
      </el-input>
      <el-table>

      </el-table>
    </el-card>
  </div>
</template>

<script>
  export default {
    name: "DeviceSearch",
    data() {
      return {
        input: '',
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
        select: ''
      };
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
</style>
