package com.bjpowernode.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * t_activity
 * @author 
 */
@Data
public class TActivity implements Serializable {
    /**
     * 主键，自动增长，活动ID
     */
    private Integer id;

    /**
     * 活动所属人ID
     */
    private Integer ownerId;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 活动预算
     */
    private BigDecimal cost;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 活动创建时间
     */
    private Date createTime;

    /**
     * 活动创建人
     */
    private Integer createBy;

    /**
     * 活动编辑时间
     */
    private Date editTime;

    /**
     * 活动编辑人
     */
    private Integer editBy;

    /**
     * 一对一关联，用户对象
     */
    private TUser ownerDO;
    private TUser createByDO;
    private TUser editByDO;

    private static final long serialVersionUID = 1L;
}