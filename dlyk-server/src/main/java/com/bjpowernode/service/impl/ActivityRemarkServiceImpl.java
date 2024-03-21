package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.mapper.TActivityMapper;
import com.bjpowernode.mapper.TActivityRemarkMapper;
import com.bjpowernode.model.TActivity;
import com.bjpowernode.model.TActivityRemark;
import com.bjpowernode.model.TUser;
import com.bjpowernode.query.ActivityRemarkQuery;
import com.bjpowernode.query.BaseQuery;
import com.bjpowernode.service.ActivityRemarkService;
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
 * @Date 2024-03-21 0:40
 */
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Resource
    private TActivityRemarkMapper tActivityRemarkMapper;

    @Override
    public int saveActivityRemark(ActivityRemarkQuery activityRemarkQuery) {
        TActivityRemark tActivityRemark = new TActivityRemark();

        //把ActivityRemarkQuery对象里面的属性数据复制到TActivityRemark对象里面
        BeanUtils.copyProperties(activityRemarkQuery,tActivityRemark);

        tActivityRemark.setCreateTime(new Date()); // 创建时间

        // 登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(activityRemarkQuery.getToken()).getId();
        tActivityRemark.setCreateBy(loginUserId); // 创建人

        return tActivityRemarkMapper.insertSelective(tActivityRemark);
    }

    @Override
    public PageInfo<TActivityRemark> getActivityRemarkByPage(Integer current,ActivityRemarkQuery activityRemarkQuery) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TActivityRemark> list = tActivityRemarkMapper.selectActivityRemarkByPage(activityRemarkQuery);
        // 3.封装分页数据到PageInfo
        PageInfo<TActivityRemark> info = new PageInfo<>(list);
        return info;
    }
}
