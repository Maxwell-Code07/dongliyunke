package com.bjpowernode.commons;

import java.lang.annotation.*;

/**
 * 数据范围注解
 */
// 指定这个注解可以用于方法级别。
@Target(ElementType.METHOD)
// 指定这个注解在运行时是可见的，可以被反射机制读取。
@Retention(RetentionPolicy.RUNTIME)
// 指定这个注解应该被包含在文档中。
@Documented
public @interface DataScope {

    // 在sql语句的末尾添加一个过滤条件
    // select * from t_user (管理员)
    // select * from t_user tu where tu.id = 2 (普通用户：于嫣)

    // select * from t_activity (管理员)
    // select * from t_activity ta where ta.owner_id = 2 (普通用户：于嫣)

    /**
     * 表的别名
     * 如select * from t_user tu where tu.id = 2 (普通用户：于嫣)
     * 中，tu就是表的别名
     */
    public String tableAlias() default "";

    /**
     * 表的字段名
     * 如select * from t_activity ta where ta.owner_id = 2 (普通用户：于嫣)
     * 中，owner_id就是表的别名
     */
    public String tableField() default "";

}

