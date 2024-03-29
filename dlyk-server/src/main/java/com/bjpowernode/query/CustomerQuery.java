package com.bjpowernode.query;

import lombok.Data;

import java.util.Date;

/**
 * @Author hzz
 * @Date 2024-03-29 17:44
 */
@Data
public class CustomerQuery extends BaseQuery{
    private Integer clueId;
    private Integer product;
    private String description;
    private Date nextContactTime;
}
