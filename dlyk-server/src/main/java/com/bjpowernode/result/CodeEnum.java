package com.bjpowernode.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author hzz
 * @Date 2024-01-28 23:05
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CodeEnum {

    OK(200,"成功"),  // 定义一个名为OK的枚举常量，其code为200，msg为"成功"

    FAIL(500,"失败"), // 定义一个名为FAIL的枚举常量，其code为500，msg为"失败"

    TOKEN_IS_EMPTY(901,"请求Token参数为空"),
    TOKEN_IS_ERROR(902,"请求Token有误"),

    TOKEN_IS_EXPIRED(903,"请求Token已过期"),

    TOKEN_IS_NONE_MATCH(903,"请求Token不匹配"),

    USER_LOGOUT(200,"退出成功"),

    DATA_ACCESS_EXCEPTION(500,"数据访问异常"),

    ACCESS_DENIED(500,"权限不足"),

    ;

    // 结果码
    private int code;

    // 结果信息
    private String msg;

    CodeEnum(String msg){
        this.msg = msg;
    }
}
