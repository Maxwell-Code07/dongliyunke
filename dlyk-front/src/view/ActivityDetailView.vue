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

  <!--  表格-->
  <el-table
      :data="activityRemarkList"
      style="width: 100%">
    <el-table-column type="index" label="序号" width="60"/>
    <el-table-column prop="noteContent" label="备注内容"/>
    <el-table-column property="createTime" label="备注时间"/>
    <el-table-column property="createByDO.name" label="备注人"/>
    <el-table-column property="editTime" label="编辑时间"/>
    <el-table-column property="editByDO.name" label="编辑人"/>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button type="success" @click="edit(scope.row.id)">编辑</el-button>
        <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!--分页条-->
  <el-pagination
      background
      layout="prev, pager, next"
      :page-size="pageSize"
      :total="total"
      @prev-click="toPage"
      @next-clic="toPage"
      @current-change="toPage"/>

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
      // 活动 备注的列表对象，初始值是空
      activityRemarkList:[{
        createByDO:{},
        editByDO:{}
      }],
      pageSize: 0,
      total: 0,

    }
  },

  mounted() {
    this.loadActivityDetail();
    this.loadActivityRemarkList(1);
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
    },

    // 查询备注列表数据
    loadActivityRemarkList(current) {
      doGet("/api/activity/remark", {
        current: current, // 当前查询第几页
        activityId: this.$route.params.id
      }).then(resp => {
        console.log(resp);
        if (resp.data.code === 200) {
          this.activityRemarkList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    // 分页函数(current这个参数是ele-plus组件传过来的，就是传的当前页）
    toPage(current) {
      this.loadActivityRemarkList(current);
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