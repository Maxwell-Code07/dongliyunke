package com.bjpowernode.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_activity_remark
 * @author 
 */
@Data
public class TActivityRemark implements Serializable {
    /**
     * 主键，自动增长，活动备注ID
     */
    private Integer id;

    /**
     * 活动ID
     */
    private Integer activityId;

    /**
     * 备注内容
     */
    private String noteContent;

    /**
     * 备注创建时间
     */
    private Date createTime;

    /**
     * 备注创建人
     */
    private Integer createBy;

    /**
     * 备注编辑时间
     */
    private Date editTime;

    /**
     * 备注编辑人
     */
    private Integer editBy;

    /**
     * 删除状态（0正常，1删除）
     */
    private Integer deleted;

    /**
     * 一对一关联
     */
    private TUser createByDO;
    private TUser editByDO;

    private static final long serialVersionUID = 1L;
}