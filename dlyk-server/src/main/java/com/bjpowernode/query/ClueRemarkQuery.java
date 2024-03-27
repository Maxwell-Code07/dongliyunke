package com.bjpowernode.query;

import lombok.Data;

/**
 * @Author hzz
 * @Date 2024-03-28 0:53
 */
@Data
public class ClueRemarkQuery extends BaseQuery{

    private Integer clueId;
    private String noteContent;
    private Integer noteWay;

}
