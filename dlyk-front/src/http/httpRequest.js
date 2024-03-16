// 从axios框架导入axios组件
import axios from "axios";
import {getTokenName, messageTip, messsageConfirm, removeToken} from "../util/util.js";
import {ElMessage, ElMessageBox} from "element-plus";

// 定义后端接口地址的前缀
axios.defaults.baseURL = "http://localhost:8080";

export function doGet(url, params) {
    return axios({
        method: "get",
        url: url,
        params: params, //{name: "对的", age: 22},
        dataType: "json"
    })
}

export function doPost(url, data) {
    return axios({
        method: "post",
        url: url,
        data: data, //{name: "好的呢", age: 22},
        dataType: "json"
    })
}

export function doPut(url, data) {
    return axios({
        method: "put",
        url: url,
        data: data, //{name:"好的呢", age: 22},
        dataType: "json"
    })
}

export function doDelete(url, params) {
    return axios({
        method: "delete",
        url: url,
        params: params, //{name: "对的", age: 22},
        dataType: "json"
    })
}

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    // 在请求头中放入一个token(jwt)传给后端
    let token = window.sessionStorage.getItem(getTokenName());
    if (!token) { // 前面加了一个!，表示token为空，token不存在，token没有值的意思
        token = window.localStorage.getItem(getTokenName());
        if(token){
            config.headers['rememberMe'] = true;
        }
    }
    if (token) { // 表示token不为空，token存在，token有值的意思
        // token放到请求头中的名为Authorization的参数里面去了
        config.headers['Authorization'] = token;
    }
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么,拦截token验证的结果，进行相应的提示和页面跳转
    if (response.data.code > 900) { // code大于900说明是token验证未通过
        // 给前端用户提示，并跳转到登录页面
        messsageConfirm(response.data.msg + ",是否重新去登录？").then(() => { // 用户点击“确定”按钮，就会出发then函数
            // 既然token没法通过，那么前端的token肯定是有问题的，那干脆在调登录页前删掉那个有问题的token
            removeToken();
            // 跳到登录页（跳路由就可以，不需要/api/login）
            window.location.href = "/";
        }).catch(() => { // 用户点击“取消”按钮，就会出发catch函数
            messageTip("取消去登录","warning");
        })
        return ;
    }

    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
});
