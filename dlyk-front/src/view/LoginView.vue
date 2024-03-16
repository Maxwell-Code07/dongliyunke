<template>

  <el-container>
    <!-- 左侧 -->
    <el-aside width="200px">
      <img src="../assets/loginBox.svg">
      <p class="imgTitle">
        欢迎使用动力云客系统
      </p>
    </el-aside>

    <!-- 右侧 -->
    <el-main>
      <div class="loginText">欢迎登录</div>

      <el-form ref="loginRefForm" :model="user" label-width="120px" :rules="loginRules">

        <el-form-item label="账号" prop="loginAct">
          <el-input v-model="user.loginAct"/>
        </el-form-item>

        <el-form-item label="密码" prop="loginPwd">
          <el-input type="password" v-model="user.loginPwd"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="login">登 录</el-button>
        </el-form-item>

        <el-form-item>
          <el-checkbox label="记住我" v-model="user.rememberMe"/>
        </el-form-item>
      </el-form>


    </el-main>
  </el-container>

</template>

<script>

import {defineComponent} from "vue";
import {doGet, doPost} from "../http/httpRequest.js";
import {getTokenName, messageTip, removeToken} from "../util/util.js";

export default defineComponent({
  // 组件的名字
  name: "LoginView",

  // 定义页面使用到的变量，定义时初始值都是给个空的
  data() {
    return {
      // 对象变量定义，是{}
      user: {},

      // 定义登录表单的验证规则
      loginRules: {
        // 定义账号的验证规则，规则可有多个，故为数组
        loginAct: [
          {required: true, message: '请输入登录账号', trigger: 'blur'}
        ],
        // 定义密码的验证规则，规则可有多个，故为数组
        loginPwd: [
          {required: true, message: '请输入登录密码', trigger: 'blur'},
          {min: 6, max: 16, message: '登录密码长度为 6-16位', trigger: 'blur'}
        ]

      }
    }
  },

  // vue的生命周期中的一个函数钩子，这个钩子是在页面(div,form标签之类的)渲染完后执行
  mounted() {
    this.freeLogin();
  },


  // 页面上需要使用的js函数，都在methods属性中定义
  methods: {
    // 登录函数
    login() {
      // 提交前保证输入框的合法性
      this.$refs.loginRefForm.validate((isValid) => {
        //isValid是验证后的结果，如果是true表示验证通过，否则未通过
        if (isValid) {
          // 验证通过，可以提交登录
          let formData = new FormData();
          formData.append("loginAct", this.user.loginAct);
          formData.append("loginPwd", this.user.loginPwd);
          formData.append("rememberMe", this.user.rememberMe);

          // 这里的resp和axios响应拦截器里的response是同一个对象
          doPost("/api/login",formData).then((resp) => {
            console.log(resp);
            if(resp.data.code === 200){
              // 登录成功，提示一下
              messageTip("登录成功！","success");

              // 删除localStorage中和sessionStorage中的token
              // 此举是为了让浏览器中的两个Storage的地方不产生重复的token。
              // （例如：登录时先不记住，然后再记住，不特殊处理的话localStorage中和sessionStorage中就会有两个一模一样的token）
              removeToken();

              // 前端存储jwt
              if(this.user.rememberMe === true){
                // 选择“记住我”的话就放在localStorage里面，否则，放到sessionStorage里面
                // “resp.data”是R对象，“resp.data.data”是R.data里的内容
                window.localStorage.setItem(getTokenName(),resp.data.data);
              }else{
                window.sessionStorage.setItem(getTokenName(),resp.data.data);
              }

              // 跳转到系统的主页面
              window.location.href = "/dashboard";
            }else{
              // 登录失败，提示一下
              messageTip("登录失败！","error");
            }
          });
        }
      })
    },

    // 免登录（自动登录）函数
    freeLogin(){
      let token = window.localStorage.getItem(getTokenName());
      if (token){ // 表示token有值、不是空、存在
        doGet("/api/login/free",{}).then(resp => {
          if(resp.data.code === 200){
            // token验证通过了，那么可以免登录
            window.location.href = "/dashboard";
          }
        })
      }
    }
  }
})
</script>

<style scoped>
.el-aside {
  background: #1a1a1a;
  width: 40%;
  text-align: center;
}

.el-main {
  height: calc(100vh)
}

img {
  height: 480px;
}

.imgTitle {
  color: #f9f9f9;
  font-size: 28px;
}

.el-form {
  width: 60%;
  margin: auto;
}

.loginText {
  text-align: center;
  margin-top: 100px;
  margin-bottom: 15px;
  font-weight: bold;
}

.el-button {
  width: 100%;
}
</style>