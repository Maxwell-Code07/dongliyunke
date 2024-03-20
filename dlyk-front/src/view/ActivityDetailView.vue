<template>
  <el-form ref="activityRemarkRefForm" :model="activityRemarkQuery" label-width="120px" :rules="activityRemarkRules">
    <el-form-item label="ID">
      <div class="desc">{{ activityDetail.id }}</div>
    </el-form-item>
    <el-form-item label="负责人">
      <div class="desc">{{ activityDetail.ownerDO.name }}</div>
    </el-form-item>
    <el-form-item label="活动名称">
      <div class="desc">{{ activityDetail.name }}</div>
    </el-form-item>
    <el-form-item label="开始时间">
      <div class="desc">{{ activityDetail.startDate }}</div>
    </el-form-item>
    <el-form-item label="活动时间">
      <div class="desc">{{ activityDetail.endDate }}</div>
    </el-form-item>
    <el-form-item label="活动预算">
      <div class="desc">{{ activityDetail.cost }}</div>
    </el-form-item>
    <el-form-item label="活动描述">
      <div class="desc">{{ activityDetail.description }}</div>
    </el-form-item>
    <el-form-item label="创建时间">
      <div class="desc">{{ activityDetail.createTime }}</div>
    </el-form-item>
    <el-form-item label="创建人">
      <div class="desc">{{ activityDetail.createByDO.name }}</div>
    </el-form-item>
    <el-form-item label="编辑时间">
      <div class="desc">{{ activityDetail.editTime }}</div>
    </el-form-item>
    <el-form-item label="编辑人">
      <div class="desc">{{ activityDetail.editByDO.name }}</div>
    </el-form-item>
    <el-form-item label="填写备注" prop="noteContent">
      <el-input
          v-model="activityRemarkQuery.noteContent"
          :rows="8"
          type="textarea"
          placeholder="请输入活动备注"/>
    </el-form-item>


    <el-form-item>
      <el-button type="primary" @click="activityRemarkSubmit">提 交</el-button>
      <el-button @click="goBack">返 回</el-button>
    </el-form-item>

  </el-form>
</template>

<script>

import {defineComponent} from "vue";
import {doGet, doPost} from "../http/httpRequest.js";
import {goBack, messageTip} from "../util/util.js";


export default defineComponent({
  name: "ActivityDetailView",

  // 注入
  inject:['reload'],

  data(){
    return{
      // 活动详情对象
      activityDetail : {
        ownerDO:{},
        createByDO:{},
        editByDO: {}
      },
      // 市场活动备注对象，初始值是空
      activityRemarkQuery: {},

      // 验证规则
      activityRemarkRules:{
        noteContent:[
          {required:true,message:"请输入活动描述",trigger:'blur'},
          {min: 5,max: 255,message:"活动备注长度为5-255个字符",trigger:'blur'},
        ]
      },

    }
  },

  mounted() {
    this.loadActivityDetail();
  },

  methods:{

    // 声明一下该函数，才能使用
    goBack,

    // 加载市场活动详情
    loadActivityDetail(){
      let id = this.$route.params.id;
      doGet("/api/activity/" + id,{}).then(resp => {
        if(resp.data.code === 200){
          this.activityDetail = resp.data.data;
          if(!this.activityDetail.ownerDO){
            this.activityDetail.ownerDO = {};
          }
          if(!this.activityDetail.createByDO){
            this.activityDetail.createByDO = {};
          }
          if(!this.activityDetail.editByDO){
            this.activityDetail.editByDO = {};
          }
        }
      })
    },

    // 提交活动备注
    activityRemarkSubmit(){
      this.$refs.activityRemarkRefForm.validate((isValid) => {
        if(isValid){
          doPost("/api/activity/remark",{
            activityId: this.activityDetail.id,
            noteContent: this.activityRemarkQuery.noteContent
          }).then(resp => {
            if(resp.data.code === 200){
              messageTip("提交成功", "success");
              // 刷新
              this.reload();
            }else{
              messageTip("提交失败", "error");
            }
          })
        }
      })
    }
  }


})
</script>

<style scoped>
.desc{
  background-color: #F0FFFF;
  width: 100%;
  padding-left: 15px;
}

</style>