package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.manager.CustomerManager;
import com.bjpowernode.mapper.TCustomerMapper;
import com.bjpowernode.model.TCustomer;
import com.bjpowernode.query.BaseQuery;
import com.bjpowernode.query.CustomerQuery;
import com.bjpowernode.service.CustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-29 17:48
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerManager customerManager;

    @Resource
    private TCustomerMapper tCustomerMapper;

    @Override
    public Boolean convertCustomer(CustomerQuery customerQuery) {
        return customerManager.convertCustomer(customerQuery);
    }

    @Override
    public PageInfo<TCustomer> getCustomerByPage(Integer current) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TCustomer> list = tCustomerMapper.selectCustomerPage();
        // 3.封装分页数据到PageInfo
        PageInfo<TCustomer> info = new PageInfo<>(list);
        return info;
    }
}
