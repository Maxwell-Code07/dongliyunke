<template>
  <el-form
      ref="clueRefForm"
      :model="clueQuery"
      :rules="clueRules"
      label-width="100px"
      style="max-width: 95%;">

    <el-form-item label="负责人">
      <el-select
          v-model="clueQuery.ownerId"
          placeholder="请选择负责人"
          style="width: 100%"
          clearable
          disabled>
        <el-option
            v-for="item in ownerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="所属活动">
      <el-select
          v-model="clueQuery.activityId"
          placeholder="请选择所属活动"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in activityOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="姓名" prop="fullName">
      <el-input v-model="clueQuery.fullName"/>
    </el-form-item>

    <el-form-item label="称呼">
      <el-select
          v-model="clueQuery.appellation"
          placeholder="请选择称呼"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in appellationOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="手机" v-if="clueQuery.id > 0"><!--此时是编辑-->
      <el-input v-model="clueQuery.phone" disabled/>
    </el-form-item>

    <el-form-item label="手机" prop="phone" v-else><!--此时是录入-->
      <el-input v-model="clueQuery.phone"/>
    </el-form-item>

    <el-form-item label="微信">
      <el-input v-model="clueQuery.weixin"/>
    </el-form-item>

    <el-form-item label="QQ" prop="qq">
      <el-input v-model="clueQuery.qq"/>
    </el-form-item>

    <el-form-item label="邮箱" prop="email">
      <el-input v-model="clueQuery.email"/>
    </el-form-item>

    <el-form-item label="年龄" prop="age">
      <el-input v-model="clueQuery.age"/>
    </el-form-item>

    <el-form-item label="职业">
      <el-input v-model="clueQuery.job"/>
    </el-form-item>

    <el-form-item label="年收入" prop="yearIncome">
      <el-input v-model="clueQuery.yearIncome"/>
    </el-form-item>

    <el-form-item label="住址">
      <el-input v-model="clueQuery.address"/>
    </el-form-item>

    <el-form-item label="贷款">
      <el-select
          v-model="clueQuery.needLoan"
          placeholder="请选择是否需要贷款"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in needLoanOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="意向状态">
      <el-select
          v-model="clueQuery.intentionState"
          placeholder="请选择意向状态"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in intentionStateOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="意向产品">
      <el-select
          v-model="clueQuery.intentionProduct"
          placeholder="请选择意向产品"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in productOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索状态">
      <el-select
          v-model="clueQuery.state"
          placeholder="请选择线索状态"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in clueStateOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索来源">
      <el-select
          v-model="clueQuery.source"
          placeholder="请选择线索来源"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in sourceOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索描述" prop="description">
      <el-input
          v-model="clueQuery.description"
          :rows="5"
          type="textarea"
          placeholder="请输入线索描述"/>
    </el-form-item>

    <el-form-item label="下次联系时间">
      <el-date-picker
          v-model="clueQuery.nextContactTime"
          type="datetime"
          style="width: 100%;"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择下次联系时间"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="addClueSubmit">提 交</el-button>
      <el-button type="success" plain @click="goBack">返 回</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {defineComponent} from "vue";
import {doGet, doPost, doPut} from "../http/httpRequest.js";
import {goBack, messageTip} from "../util/util.js";

export default defineComponent({
  name: "ClueRecordView",
  data() {
    return {
      // 线索表单对象，初始值是空
      clueQuery: {},
      // 负责人的下拉选项
      ownerOptions: [{}],
      // 市场活动的下拉选项数据，初始值是空
      activityOptions: [{}],
      // 意向产品的下拉选项
      productOptions: [{}],
      appellationOptions: [{}],
      needLoanOptions: [{}],
      intentionStateOptions: [{}],
      clueStateOptions: [{}],
      sourceOptions: [{}],

      // 录入线索验证规则
      clueRules: {
        phone: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {pattern: /^1[3-9]\d{9}$/, message: '手机号码格式有误', trigger: 'blur'},
          {validator: this.checkPhone, trigger: 'blur'}
        ],
        fullName: [
          {min: 2, message: '姓名至少2个汉字', trigger: 'blur'},
          {pattern: /^[\u4e00-\u9fa5]{0,}$/, message: '姓名必须为中文汉字', trigger: 'blur'},
        ],
        qq: [
          {min: 5, message: 'QQ号至少为5位', trigger: 'blur'},
          {pattern: /^\d+$/, message: 'QQ号码必须为数字', trigger: 'blur'},
        ],
        email: [
          {pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, message: '邮箱格式有误', trigger: 'blur'},
        ],
        age: [
          {pattern: /^\d+$/, message: '年龄必须为数字', trigger: 'blur'},
        ],
        yearIncome: [
          {pattern: /^[0-9]+(\.[0-9]{2})?$/, message: '年收入必须是整数或者两位小数', trigger: 'blur'}
        ],
        description: [
          {min: 5, max: 255, message: '线索描述长度为5-255个字符', trigger: 'blur'},
        ],
      }
    }
  },

  mounted() {
    this.loadDicValue('appellation');
    this.loadDicValue('needLoan');
    this.loadDicValue('intentionState');
    this.loadDicValue('clueState');
    this.loadDicValue('source');
    this.loadDicValue('activity');
    this.loadDicValue('product');
    this.loadOwner();
    this.loadLoginUser();

    // 加载要编辑的数据（由于录入和编辑共用同一个页面，所以要判断是编辑还是录入）
    this.loadClue();
  },

  methods: {
    goBack,

    //加载字典数据
    loadDicValue(typeCode) {
      doGet("/api/dicvalue/" + typeCode, {}).then(resp => {
        if (resp.data.code === 200) {
          if (typeCode === 'appellation') {
            this.appellationOptions = resp.data.data;
          } else if (typeCode === 'needLoan') {
            this.needLoanOptions = resp.data.data;
          } else if (typeCode === 'intentionState') {
            this.intentionStateOptions = resp.data.data;
          } else if (typeCode === 'clueState') {
            this.clueStateOptions = resp.data.data;
          } else if (typeCode === 'source') {
            this.sourceOptions = resp.data.data;
          } else if (typeCode === 'activity') {
            this.activityOptions = resp.data.data;
          } else if (typeCode === 'product') {
            this.productOptions = resp.data.data;
          }
        }
      })
    },

    // 加载负责人
    loadOwner() {
      doGet("/api/owner", {}).then(resp => {
        if (resp.data.code === 200) {
          this.ownerOptions = resp.data.data;
        }
      })
    },

    // 加载当前用户
    loadLoginUser() {
      // .then 在这里的作用是：当 doGet 函数成功获取到数据并返回 Promise 时，
      // 执行 .then 里的回调函数，并将获取到的数据作为参数 rep 传递给这个回调函数。这样，就可以在回调函数内部处理这些数据，比如展示在页面上。
      doGet("/api/dashboard/info", {}).then((resp) => {
        console.log(resp);
        let user = resp.data.data;
        this.clueQuery.ownerId = user.id;
      })
    },

    // 验证手机号有没有录入过，录入过的手机号就不能再录入了
    checkPhone(rule, value, callback) {
      console.log(rule)
      console.log(value) // value 就是input里输入的手机号
      console.log(callback)
      // 验证该收假后是否已经录入过，如果录入过了，就不能再录入了
      let phone = value; // value 就是input里输入的手机号
      if (phone) {
        doGet("/api/clue/" + phone, {}).then(resp => {
          if (resp.data.code === 500) {
            //手机号录入过了，不能再录入了
            return callback(new Error('该手机号录入过了，不能再录入.'))
          } else {
            return callback(); //验证通过了，直接调用一下callback()函数
          }
        })
      }
    },
    addClueSubmit() {
      this.$refs.clueRefForm.validate((isValid) => {
        if (isValid) {
          let formData = new FormData();
          for (let field in this.clueQuery) {
            if(this.clueQuery[field]){ // this.clueQuery[field]有值，存在，不为空
              formData.append(field, this.clueQuery[field]);
            }
          }
          if (this.clueQuery.id > 0) { // 说明是点编辑点进来的
            doPut("/api/clue", formData).then((resp) => { //获取ajax异步请求后的结果
              console.log(resp);
              if (resp.data.code === 200) {
                //编辑成功，提示一下
                messageTip("编辑成功", "success");
                //跳转到活动列表页
                this.$router.push("/dashboard/clue");
              } else {
                //编辑失败，提示一下
                messageTip("编辑失败", "error");
              }
            });
          } else {
            doPost("/api/clue", formData).then((resp) => { //获取ajax异步请求后的结果
              console.log(resp);
              if (resp.data.code === 200) {
                //录入成功，提示一下
                messageTip("录入成功", "success");
                //跳转到活动列表页
                this.$router.push("/dashboard/clue");
              } else {
                //录入失败，提示一下
                messageTip("录入失败", "error");
              }
            });
          }
        }
      })
    },

    // 加载要编辑的线索数据
    loadClue() {
      let id = this.$route.params.id;
      if (id) { // id存在，有值，不为空的话，说明是编辑（即路由有参数传过来）
        doGet("/api/clue/detail/" + id, {}).then(resp => {
          if (resp.data.code === 200) {
            this.clueQuery = resp.data.data;
          }
        })
      }
    },
  },
})
</script>

<style scoped>

</style>