package com.bjpowernode.service;

import com.bjpowernode.model.TActivityRemark;
import com.bjpowernode.query.ActivityRemarkQuery;
import com.github.pagehelper.PageInfo;

/**
 * @Author hzz
 * @Date 2024-03-21 0:35
 */
public interface ActivityRemarkService {
    int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery);

    PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current,ActivityRemarkQuery activityRemarkQuery);

    TActivityRemark getActivityRemarkById(Integer id);

    int updateActivityRemark(ActivityRemarkQuery activityRemarkQuery);

    int delActivityRemarkById(Integer id);
}
