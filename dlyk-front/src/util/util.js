import {ElMessage, ElMessageBox} from "element-plus";

export function messageTip(msg, type) {
    ElMessage({
        showClose: true, // 消息提示框是否可以关闭（弹出的消息最旁边有没有叉叉可以点）
        center: true, // 文字是否居中
        duration: 3000,  // 多少毫秒后自动关闭，默认为3000
        message: msg, // 提示的消息的内容
        type: type, // 消息类型： 'success' | 'warning' | 'info' | 'error'
    })
}

// 获取存储在sessionStorage或localStorage中的Token(jwt)的名字
export function getTokenName(){
    return "dlyk_token";
}

/**
 * 删除localStorage中和sessionStorage中的token
 */
export function removeToken(){
    window.sessionStorage.removeItem(getTokenName());
    window.localStorage.removeItem(getTokenName());
}

/**
 * 消息确认提示
 * @param msg
 * @returns {Promise<MessageBoxData>}
 */
export function messsageConfirm(msg){
    return ElMessageBox.confirm(

        msg, // 提示语
        '系统提醒', // 提示的标题
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
}

export function goBack(){
    this.$router.go(-1);
}

/**
 * 获取token
 * @returns {string}
 */
export function getToken(){
    // 在发送请求之前做些什么
    // 在请求头中放入一个token(jwt)传给后端
    let token = window.sessionStorage.getItem(getTokenName());
    if (!token) { // 前面加了一个!，表示token为空，token不存在，token没有值的意思
        token = window.localStorage.getItem(getTokenName());
    }
    if (token) { // 表示token不为空，token存在，token有值的意思
        // token放到请求头中的名为Authorization的参数里面去了
        return token;
    }else{
        messsageConfirm( "请求token是空,是否重新去登录？").then(() => { // 用户点击“确定”按钮，就会出发then函数
            // 既然token没法通过，那么前端的token肯定是有问题的，那干脆在调登录页前删掉那个有问题的token
            removeToken();
            // 跳到登录页（跳路由就可以，不需要/api/login）
            window.location.href = "/";
        }).catch(() => { // 用户点击“取消”按钮，就会出发catch函数
            messageTip("取消去登录","warning");
        })
    }
}