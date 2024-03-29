package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.mapper.TClueMapper;
import com.bjpowernode.mapper.TClueRemarkMapper;
import com.bjpowernode.model.TActivityRemark;
import com.bjpowernode.model.TClueRemark;
import com.bjpowernode.query.ClueRemarkQuery;
import com.bjpowernode.service.ClueRemarkService;
import com.bjpowernode.util.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-28 1:00
 */
@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {

    @Resource
    private TClueRemarkMapper tClueRemarkMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveClueRemark(ClueRemarkQuery clueRemarkQuery) {
        TClueRemark tClueRemark = new TClueRemark();

        //把ClueRemarkQuery对象里面的属性数据复制到TClueRemark对象里面
        BeanUtils.copyProperties(clueRemarkQuery,tClueRemark);

        tClueRemark.setCreateTime(new Date()); // 创建时间

        // 登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(clueRemarkQuery.getToken()).getId();
        tClueRemark.setCreateBy(loginUserId); // 创建人

        return tClueRemarkMapper.insertSelective(tClueRemark);
    }

    @Override
    public PageInfo<TClueRemark> getClueRemarkByPage(Integer current, ClueRemarkQuery clueRemarkQuery) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TClueRemark> list = tClueRemarkMapper.selectClueRemarkByPage(clueRemarkQuery);
        // 3.封装分页数据到PageInfo
        PageInfo<TClueRemark> info = new PageInfo<>(list);
        return info;
    }
}
