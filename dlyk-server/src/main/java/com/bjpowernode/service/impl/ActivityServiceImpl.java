package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.mapper.TActivityMapper;
import com.bjpowernode.model.TActivity;
import com.bjpowernode.model.TUser;
import com.bjpowernode.query.BaseQuery;
import com.bjpowernode.service.ActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
    public PageInfo<TActivity> getActivityByPage(Integer current) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TActivity> list = tActivityMapper.selectActivityByPage(BaseQuery.builder().build());
        // 3.封装分页数据到PageInfo
        PageInfo<TActivity> info = new PageInfo<>(list);
        return info;
    }
}
