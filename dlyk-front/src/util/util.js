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