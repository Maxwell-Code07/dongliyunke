<template>
  <el-container>
    <!--左侧-->
    <el-aside :width="isCollapse ? '64px' : '200px'">

      <div class="menuTitle">@动力云客管理系统</div>

      <el-menu
          active-text-color="#ffd04b"
          background-color="#334157"
          class="el-menu-vertical-demo"
          default-active="2"
          text-color="#fff"
          style="border-right: solid 0px"
          :collapse="isCollapse"
          :collapse-transition="false"
          :router="true"
          :unique-opened="true">

        <!--市场活动-->
        <el-sub-menu index="1">
          <template #title>
            <el-icon>
              <OfficeBuilding/>
            </el-icon>
            <span>市场活动</span>
          </template>
          <el-menu-item index="/dashboard/activity">
            <el-icon>
              <Notification/>
            </el-icon>
            市场活动
          </el-menu-item>
          <el-menu-item index="1-2">
            <el-icon>
              <DataAnalysis/>
            </el-icon>
            市场统计
          </el-menu-item>
        </el-sub-menu>

        <!--线索管理-->
        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <Operation/>
            </el-icon>
            <span>线索管理</span>
          </template>
          <el-menu-item index="2-1">
            <el-icon>
              <Film/>
            </el-icon>
            线索管理
          </el-menu-item>
        </el-sub-menu>

        <!--客户管理菜单-->
        <el-sub-menu index="3">
          <template #title>
            <el-icon>
              <User/>
            </el-icon>
            <span>客户管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <Postcard/>
            </el-icon>
            客户管理
          </el-menu-item>

        </el-sub-menu>

        <!--交易管理菜单-->
        <el-sub-menu index="4">
          <template #title>
            <el-icon>
              <CreditCard/>
            </el-icon>
            <span>交易管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <Postcard/>
            </el-icon>
            交易管理
          </el-menu-item>
        </el-sub-menu>

        <!--产品管理菜单-->
        <el-sub-menu index="5">
          <template #title>
            <el-icon>
              <Memo/>
            </el-icon>
            <span>产品管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <Postcard/>
            </el-icon>
            产品管理
          </el-menu-item>
        </el-sub-menu>

        <!--字典管理菜单-->
        <el-sub-menu index="6">
          <template #title>
            <el-icon>
              <location/>
            </el-icon>
            <span>字典管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <Postcard/>
            </el-icon>
            字典管理
          </el-menu-item>
        </el-sub-menu>

        <!--用户管理菜单-->
        <el-sub-menu index="7">
          <template #title>
            <el-icon>
              <UserFilled/>
            </el-icon>
            <span>用户管理</span>
          </template>
          <el-menu-item index="/dashboard/user">
            <el-icon>
              <Postcard/>
            </el-icon>
            用户管理
          </el-menu-item>
        </el-sub-menu>

        <!--系统管理菜单-->
        <el-sub-menu index="8">
          <template #title>
            <el-icon>
              <Setting/>
            </el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="1-1">
            <el-icon>
              <Postcard/>
            </el-icon>
            系统管理
          </el-menu-item>
        </el-sub-menu>

      </el-menu>
    </el-aside>

    <!--右侧-->
    <el-container class="rightContent">
      <!--右侧：上-->
      <el-header>
        <el-icon class="show" @click="showMenu">
          <Fold/>
        </el-icon>

        <el-dropdown :hide-on-click="false">
    <span class="el-dropdown-link">
      {{ user.name }}
      <el-icon class="el-icon--right"><arrow-down/></el-icon>
    </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>我的资料</el-dropdown-item>
              <el-dropdown-item>修改密码</el-dropdown-item>
              <el-dropdown-item divided @click="logout()">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

      </el-header>

      <!--右侧：中-->
      <el-main>
        <router-view v-if="isRouterAlive"/>
      </el-main>

      <!--右侧：下-->
      <el-footer>@版权所有1994-2099 西京学院 陕西省西安市长安区大华路一号</el-footer>

    </el-container>
  </el-container>
</template>

<script>
import {defineComponent} from "vue";
import {doGet} from "../http/httpRequest.js";
import {messageTip, messsageConfirm, removeToken} from "../util/util.js";

export default defineComponent({
  // 定义组件名
  name: "DashboardView",

  data() {
    return {
      // 控制左侧菜单左右展开和折叠，true是折叠，false是展开
      isCollapse: false,
      // 登录用户对象，初始值是空
      user: { },
      // 控制仪表盘页面右侧内容体是否显示
      isRouterAlive: true,
    }
  },

  // 提供者（生产者）
  provide(){
    return{
      // 提供一个函数（要求是箭头函数）
      reload: () => {
        this.isRouterAlive = false; // 右侧内容隐藏
        this.$nextTick(() => { // $nextTick(),当数据更新了，在dom中渲染后，自动执行该函数
          this.isRouterAlive = true;
        })
      },

    }
  },


  // vue的生命周期中的一个函数钩子，这个钩子是在页面(div,form标签之类的)渲染完后执行
  mounted() {
    // 加载当前用户
    this.loadLoginUser();
  },

  methods: {
    // 左侧菜单左右展开和折叠
    showMenu() {
      this.isCollapse = !this.isCollapse;
    },

    // 加载当前用户
    loadLoginUser() {
      // .then 在这里的作用是：当 doGet 函数成功获取到数据并返回 Promise 时，
      // 执行 .then 里的回调函数，并将获取到的数据作为参数 rep 传递给这个回调函数。这样，就可以在回调函数内部处理这些数据，比如展示在页面上。
      doGet("/api/dashboard/info", {}).then((resp) => {
        console.log(resp);
        this.user = resp.data.data;
      })
    },

    // 退出登录
    logout(){
      doGet("/api/logout",{}).then(resp => {
        if(resp.data.code === 200){
          removeToken();
          messageTip("退出成功","success");
          // 跳到登录页（跳路由就可以，不需要/api/login）
          window.location.href = "/";
        }else{
          // 类似“文件删不掉，是否要强行删掉”
          messsageConfirm("退出异常，是否要强制退出？").then(() => { // 用户点击“确定”按钮，就会出发then函数
            // 既然token没法通过，那么前端的token肯定是有问题的，那干脆在调登录页前删掉那个有问题的token
            removeToken();
            // 跳到登录页（跳路由就可以，不需要/api/login）
            window.location.href = "/";
          }).catch(() => { // 用户点击“取消”按钮，就会出发catch函数
            messageTip("取消强制退出","warning");
          })
        }
      });
    }
  }


})
</script>


<style scoped>

.el-aside {
  background-color: #171615;
}

.el-header {
  background-color: azure;
  height: 35px;
  line-height: 35px;
}

.el-footer {
  background-color: aliceblue;
  line-height: 35px;
  height: 35px;
  text-align: center;
}

.rightContent {
  height: calc(100vh);
}

.menuTitle {
  height: 35px;
  text-align: center;
  line-height: 35px;
  color: #f9f9f9;
}

.show {
  cursor: pointer;
}

.el-dropdown {
  float: right;
  line-height: 35px;
}

</style>