<template>
  <el-form
      ref="clueRemarkRefForm"
      :model="clueRemark"
      label-width="110px"
      :rules="clueRemarkRules"
      style="max-width: 95%;">

    <el-form-item label="负责人">
      <div class="desc">{{clueDetail.ownerDO.name}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="所属活动">
      <div class="desc">{{clueDetail.activityDO.name}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="姓名">
      <div class="desc">{{clueDetail.fullName}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="称呼">
      <div class="desc">{{clueDetail.appellationDO.typeValue}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="手机">
      <div class="desc">{{clueDetail.phone}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="微信">
      <div class="desc">{{clueDetail.weixin}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="QQ">
      <div class="desc">{{clueDetail.qq}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="邮箱">
      <div class="desc">{{clueDetail.email}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="年龄">
      <div class="desc">{{clueDetail.age}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="职业">
      <div class="desc">{{clueDetail.job}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="年收入">
      <div class="desc">{{clueDetail.yearIncome}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="住址">
      <div class="desc">{{clueDetail.address}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="贷款">
      <div class="desc">{{clueDetail.needLoanDO.typeValue}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="意向状态">
      <div class="desc">{{clueDetail.intentionStateDO.typeValue}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="意向产品">
      <div class="desc">{{clueDetail.intentionProductDO.name}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="线索状态">
      <div class="desc">{{clueDetail.stateDO.typeValue}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="线索来源">
      <div class="desc">{{clueDetail.sourceDO.typeValue}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="线索描述">
      <div class="desc">{{clueDetail.description}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="下次联系时间">
      <div class="desc">{{clueDetail.nextContactTime}}&nbsp;</div>
    </el-form-item>

    <el-form-item label="填写跟踪记录" prop="noteContent">
      <el-input
          v-model="clueRemark.noteContent"
          :rows="8"
          type="textarea"/>
    </el-form-item>
    <el-form-item label="跟踪方式" prop="noteWay">
      <el-select
          v-model="clueRemark.noteWay"
          placeholder="请选择跟踪方式"
          style="width: 100%"
          @click="loadDicValue('noteWay')"
          clearable>
        <el-option
            v-for="item in noteWayOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="clueRemarkSubmit">提 交</el-button>
      <el-button type="success" @click="convertCustomer" v-if="clueDetail.state !== -1">转换客户</el-button>
      <el-button type="success" plain @click="goBack">返 回</el-button>
    </el-form-item>
  </el-form>
</template>

<script>

import {defineComponent} from "vue";
import {doGet, doPost} from "../http/httpRequest.js";
import {goBack, messageTip} from "../util/util.js";

export default defineComponent({
  name: "ClueDetailView",

  // 注入
  inject : ['reload'],

  data(){
    return{
      // 线索详情对象，初始值是空
      clueDetail : {
       ownerDO : {},
       activityDO : {},
       appellationDO : {},
       needLoanDO : {},
       intentionStateDO : {},
       intentionProductDO : {},
       stateDO : {},
       sourceDO : {}
      },
      // 线索跟踪记录对象，初始值是空
      clueRemark : {},
      // 跟踪方式的下拉选项，初始值是空
      noteWayOptions: [{}],
    }
  },

  mounted() {
    this.loadClueDetail();
  },

  methods:{
    goBack,

    // 加载线索详情
    loadClueDetail(){
      let id = this.$route.params.id;
      doGet("/api/clue/detail/" + id,{}).then(resp => {
        if(resp.data.code === 200){
          this.clueDetail = resp.data.data;
        }
      })
    },

    // 提交线索跟踪记录
    clueRemarkSubmit(){
      doPost("/api/clue/remark",{
        clueId: this.clueDetail.id,
        noteContent: this.clueRemark.noteContent,
        noteWay: this.clueRemark.noteWay
      }).then(resp => {
        if(resp.data.code === 200){
          messageTip("提交成功", "success");
          // 刷新
          this.reload();
        }else{
          messageTip("提交失败", "error");
        }
      })
    },

    //加载字典数据
    loadDicValue(typeCode) {
      doGet("/api/dicvalue/" + typeCode, {}).then(resp => {
        if (resp.data.code === 200) {
          if (typeCode === 'noteWay') {
            this.noteWayOptions = resp.data.data;
          }
        }
      })
    },
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