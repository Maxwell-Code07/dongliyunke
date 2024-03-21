package com.bjpowernode.query;

import lombok.Data;

/**
 * @Author hzz
 * @Date 2024-03-21 0:26
 */
@Data
public class ActivityRemarkQuery extends BaseQuery{

    private Integer id;

    private Integer activityId;

    private String noteContent;


}
