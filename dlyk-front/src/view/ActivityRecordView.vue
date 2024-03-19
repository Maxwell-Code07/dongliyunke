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

    <el-form-item>
      <el-button type="primary" @click="activitySubmit">提 交</el-button>
      <el-button @click="goBack">返 回</el-button>
    </el-form-item>

  </el-form>

</template>

<script>
import {defineComponent} from "vue";
import {doGet, doPost} from "../http/httpRequest.js";
import {messageTip} from "../util/util.js";

export default defineComponent({
  name: "ActivityRecordView",

  // 注入
  inject :['reload'],

  data(){
    return {
      // 市场活动表单对象，初始值是空
      activityQuery:{},
      // 市场活动表单验证规则
      activityRules:{
        ownerId:[
          {required:true,message:"请选择负责人",trigger:'blur'}
        ],
        name:[
          {required:true,message:"请输入活动名称",trigger:'blur'}
        ],
        startTime:[
          {required:true,message:"请选择开始时间",trigger:'blur'}
        ],
        endTime:[
          {required:true,message:"请选择结束时间",trigger:'blur'}
        ],
        cost:[
          {required:true,message:"请输入活动预算",trigger:'blur'},
          {pattern: /^[0-9]+(\.[0-9]{2})?$/, message: '活动预算必须是整数或者两位小数', trigger: 'blur'}
        ],
        description:[
          {required:true,message:"请输入活动描述",trigger:'blur'},
          {min: 5,max: 255,message:"活动描述长度为5-255个字符",trigger:'blur'},
        ]
      },
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

    // 返回
    goBack(){
      this.$router.go(-1);
    },

    // 市场活动提交
    activitySubmit(){
      let formData = new FormData();
      for(let field in this.activityQuery){
        formData.append(field,this.activityQuery[field]);
      }
      this.$refs.activityRefForm.validate((isValid) => {
        if(isValid){
          doPost("api/activity",formData).then(resp =>{
            if(resp.data.code === 200){
              messageTip("提交成功", "success");
              // 跳转到市场活动列表页
              this.$router.push("/dashboard/activity");
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
.width{
  width: 100%;
}
</style>