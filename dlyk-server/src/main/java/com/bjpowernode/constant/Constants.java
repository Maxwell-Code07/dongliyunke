package com.bjpowernode.constant;

/**
 * 常量类
 *
 */
public class Constants {

    public static final String LOGIN_URI = "/api/login";

    //redis的key的命名规范： 项目名:模块名:功能名:唯一业务参数(比如用户id)
    public static final String REDIS_JWT_KEY = "dlyk:user:login:";

    // 过期时间7天（单位是秒）
    public static final Long EXPIRE_TIME = 7 * 24 * 60 * 60L;

    // 过期时间30分钟（单位是秒）（默认为30分钟）
    public static final Long DEFAULT_EXPIRE_TIME = 30 * 60L;


    // 分页时每页显示10条数据
    public static final Integer PAGE_SIZE = 10;
}
