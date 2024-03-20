package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.mapper.TActivityMapper;
import com.bjpowernode.model.TActivity;
import com.bjpowernode.model.TUser;
import com.bjpowernode.query.ActivityQuery;
import com.bjpowernode.query.BaseQuery;
import com.bjpowernode.service.ActivityService;
import com.bjpowernode.util.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-17 13:44
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private TActivityMapper tActivityMapper;

    @Override
    public PageInfo<TActivity> getActivityByPage(Integer current, ActivityQuery activityQuery) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TActivity> list = tActivityMapper.selectActivityByPage(activityQuery);
        // 3.封装分页数据到PageInfo
        PageInfo<TActivity> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public int saveActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();

        //把ActivityQuery对象里面的属性数据复制到TActivity对象里面
        BeanUtils.copyProperties(activityQuery,tActivity);

        tActivity.setCreateTime(new Date()); // 创建时间

        // 登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(activityQuery.getToken()).getId();
        tActivity.setCreateBy(loginUserId); // 创建人

        return tActivityMapper.insertSelective(tActivity);
    }

    @Override
    public TActivity getActivityById(Integer id) {
        return tActivityMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateActivity(ActivityQuery activityQuery) {
        TActivity tActivity = new TActivity();

        // 把ActivityQuery对象里面的属性数据复制到TActivity对象里面
        BeanUtils.copyProperties(activityQuery,tActivity);

        tActivity.setEditTime(new Date());

        // 登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(activityQuery.getToken()).getId();
        tActivity.setEditBy(loginUserId); // 创建人

        return tActivityMapper.updateByPrimaryKeySelective(tActivity);
    }
}
