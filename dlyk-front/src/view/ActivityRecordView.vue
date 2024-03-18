<template>

  <el-form ref="activityRefForm" :model="activityQuery" label-width="110px" :rules="activityRules">
    <el-form-item label="负责人" prop="ownerId">
      <el-select v-model="activityQuery.ownerId" placeholder="请选择" class="width">
        <el-option
            v-for="item in ownerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="活动名称" prop="name"> <!--编辑-->
      <el-input v-model="activityQuery.name"/>
    </el-form-item>

    <el-form-item label="开始时间" prop="startTime">
      <el-date-picker
          v-model="activityQuery.startTime"
          type="datetime"
          placeholder="请选择开始时间"
          value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
    </el-form-item>

    <el-form-item label="结束时间" prop="endTime">
      <el-date-picker
          v-model="activityQuery.endTime"
          type="datetime"
          placeholder="请选择结束时间"
          value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
    </el-form-item>

    <el-form-item label="活动预算" prop="cost">
      <el-input v-model="activityQuery.cost"/>
    </el-form-item>

    <el-form-item label="活动描述" prop="description">
      <el-input
        v-model = "activityQuery.description"
        :rows="6"
        type="textarea"
        placeholder="请输入活动描述"/>
    </el-form-item>

    <el-form-item label="活动名称" prop="name"> <!--编辑-->
      <el-button type="primary" @click="activitySubmit">提 交</el-button>
      <el-button @click="goBack">返 回</el-button>
    </el-form-item>

  </el-form>

</template>

<script>
import {defineComponent} from "vue";
import {doGet} from "../http/httpRequest.js";

export default defineComponent({
  name: "ActivityRecordView",

  data(){
    return {
      // 市场活动表单对象，初始值是空
      activityQuery:{},
      // 市场活动表单验证规则
      activityRules:{},
      // 负责人的下拉选项，初始值是空
      ownerOptions:[{}]
    }
  },

  mounted() {
    this.loadOwner();
  },

  methods:{
    // 加载负责人的下拉选项
    loadOwner() {
      doGet("/api/owner", {}).then(resp => {
        if (resp.data.code === 200) {
          this.ownerOptions = resp.data.data;
        }
      })
    },

    goBack(){
      this.$router.go(-1);
    },
  }
})

</script>

<style scoped>
.width{
  width: 100%;
}
</style>