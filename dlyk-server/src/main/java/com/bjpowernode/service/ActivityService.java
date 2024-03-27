package com.bjpowernode.service;

import com.bjpowernode.model.TActivity;
import com.bjpowernode.query.ActivityQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-17 13:41
 */
public interface ActivityService {

    PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery);

    int saveActivity(ActivityQuery activityQuery);

    TActivity getActivityById(Integer id);

    int updateActivity(ActivityQuery activityQuery);

    List<TActivity> getOngoingActivity();
}
