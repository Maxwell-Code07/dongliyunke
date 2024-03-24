package com.bjpowernode.service.impl;

import com.alibaba.excel.EasyExcel;
import com.bjpowernode.config.listener.UploadDataListener;
import com.bjpowernode.constant.Constants;
import com.bjpowernode.mapper.TClueMapper;
import com.bjpowernode.model.TClue;
import com.bjpowernode.model.TUser;
import com.bjpowernode.query.BaseQuery;
import com.bjpowernode.service.ClueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-23 13:18
 */
@Service
public class ClueServiceImpl implements ClueService {

    @Resource
    private TClueMapper tclueMapper;

    @Override
    public PageInfo<TClue> getClueByPage(Integer current) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TClue> list = tclueMapper.selectClueByPage(BaseQuery.builder().build());
        // 3.封装分页数据到PageInfo
        PageInfo<TClue> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public void importExcel(InputStream inputStream) {
        // 链式编程，3个参数，第一个参数是要读取的Excel文件，第二个参数是Excel模板类(要有所有字段)，第三个参数是文件读取的监听器
        EasyExcel.read(inputStream, TClue.class, new UploadDataListener(tclueMapper))
                .sheet()
                .doRead();
    }
}
