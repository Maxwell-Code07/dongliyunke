// import ... from ... 语句导入，从vue框架导入createApp函数
import { createApp } from 'vue'

// 导入css样式
// import './style.css'

// 没加大括号，表示导入组件，加了大括号一般表示导入函数
// import ... from ... 语句导入，从element-plus框架导入ElementPlus组件
import ElementPlus from 'element-plus'

// 导入element-plus的中文语言包
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

// 导入element-plus的css样式，不需要from子句
import 'element-plus/dist/index.css'

//
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// 从router.js中导入router组件
import router from './router/router.js'

// 从./App.vue导入APP组件
import App from './App.vue'  // 根组件

// 从./view/LoginView.vue导入APP组件
import LoginView from './view/LoginView.vue'

let app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

// 利用上面所导入的creatApp()函数 创建一个vue应用，mount是挂载到#app地方
app.use(ElementPlus,{locale:zhCn}).use(router).mount('#app')


