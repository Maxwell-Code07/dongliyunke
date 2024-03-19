package com.bjpowernode.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author hzz
 * @Date 2024-03-17 22:14
 */
@Data
public class ActivityQuery extends BaseQuery{

    private Integer ownerId;
    private String name;

    /**
     * 前端传过来的是一个格式为：YYYY-MM-DD HH:mm:ss 的字符串日期
     * 后台接受要把字符串的日期转成java.util.Date日期，需要使用@DateTimeFormat
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private BigDecimal cost;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 活动描述
    private String description;

}
