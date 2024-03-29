// 从vue-router这个依赖库中导入createRouter()函数，createWebHistory()函数
import {createRouter, createWebHistory} from "vue-router";

// 定义一个变量
let router = createRouter({

    // 路由历史
    history: createWebHistory(),

    // 配置路由，是一个数组，里面可以配置多个路由
    routes: [
        {
            // 路由路径
            path: '/',
            // 路由路径所对应的页面
            component: () => import('../view/LoginView.vue'),
        },
        {
            // 路由路径
            path: '/dashboard',
            // 路由路径所对应的页面
            component: () => import('../view/DashboardView.vue'),
            // 子路由
            children:[
                {
                    // 路由路径，子路由路径不能以斜杠开头
                    path: 'user',
                    // 路由路径所对应的页面
                    component: () => import('../view/UserView.vue'),
                },
                {
                    // 路由路径，子路由路径不能以斜杠开头,id是动态变量，这个叫动态路由
                    path: 'user/:id',
                    // 路由路径所对应的页面
                    component: () => import('../view/UserDetailView.vue'),
                },
                {
                    // 路由路径，子路由路径不能以斜杠开头
                    path: 'activity',
                    // 路由路径所对应的页面
                    component: () => import('../view/ActivityView.vue'),
                },
                {
                    // 路由路径，子路由路径不能以斜杠开头
                    path: 'activity/add',
                    // 路由路径所对应的页面
                    component: () => import('../view/ActivityRecordView.vue'),
                },
                {
                    // 路由路径，子路由路径不能以斜杠开头,id是动态变量，这个叫动态路由
                    path: 'activity/edit/:id',
                    // 路由路径所对应的页面
                    component: () => import('../view/ActivityRecordView.vue'),
                },
                {
                    // 路由路径，子路由路径不能以斜杠开头,id是动态变量，这个叫动态路由
                    path: 'activity/:id',
                    // 路由路径所对应的页面
                    component: () => import('../view/ActivityDetailView.vue'),
                },
                {
                  path:'clue',
                  component:() => import('../view/ClueView.vue'),
                },
                {
                    path:'clue/add',
                    component:() => import('../view/ClueRecordView.vue'),
                },
                {
                    // 路由路径，子路由路径不能以斜杠开头,id是动态变量，这个叫动态路由
                    path: 'clue/edit/:id',
                    // 路由路径所对应的页面
                    component: () => import('../view/ClueRecordView.vue'),
                },
                {
                    // 路由路径，子路由路径不能以斜杠开头,id是动态变量，这个叫动态路由
                    path: 'clue/detail/:id',
                    // 路由路径所对应的页面
                    component: () => import('../view/ClueDetailView.vue'),
                },
                {
                    // 路由路径，子路由路径不能以斜杠开头,id是动态变量，这个叫动态路由
                    path: 'customer',
                    // 路由路径所对应的页面
                    component: () => import('../view/CustomerView.vue'),
                },

            ]
        },
        {
            // 路由路径
            path: '/hello',
            // 路由路径所对应的页面
            component: () => import('../components/HelloWorld.vue'),
        },
    ]
})

// 导出创建的路由对象
export default router;