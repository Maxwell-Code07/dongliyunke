package com.bjpowernode.service.impl;

import com.bjpowernode.constant.Constants;
import com.bjpowernode.manager.CustomerManager;
import com.bjpowernode.mapper.TCustomerMapper;
import com.bjpowernode.model.TCustomer;
import com.bjpowernode.query.BaseQuery;
import com.bjpowernode.query.CustomerQuery;
import com.bjpowernode.result.CustomerExcel;
import com.bjpowernode.service.CustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
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

    @Override
    public List<CustomerExcel> getCustomerByExcel(List<String> idList) {
        List<CustomerExcel> customerExcelList = new ArrayList<>();

        List<TCustomer> tCustomerList = tCustomerMapper.selectCustomerByExcel(idList);

        // 把从数据库查询出来的List<TCustomer>数据，转换为List<CustomerExcel>数据
        tCustomerList.forEach(tCustomer -> {
            CustomerExcel customerExcel = new CustomerExcel();

            // 需要一个一个单独设置，没有办法，因为无法使用BeanUtils直接进行复制
            // 需要一个一个设置，没有办法，因为没法使用BeanUtils复制
            // 对于有可能是空的属性，如果他是空的，就赋给他一个空字符串Constants.EMPTY
            customerExcel.setOwnerName(ObjectUtils.isEmpty(tCustomer.getOwnerDO()) ? Constants.EMPTY : tCustomer.getOwnerDO().getName());
            customerExcel.setActivityName(ObjectUtils.isEmpty(tCustomer.getActivityDO()) ? Constants.EMPTY : tCustomer.getActivityDO().getName());
            customerExcel.setFullName(tCustomer.getClueDO().getFullName());
            customerExcel.setAppellationName(ObjectUtils.isEmpty(tCustomer.getAppellationDO()) ? Constants.EMPTY : tCustomer.getAppellationDO().getTypeValue());
            customerExcel.setPhone(tCustomer.getClueDO().getPhone());
            customerExcel.setWeixin(tCustomer.getClueDO().getWeixin());
            customerExcel.setQq(tCustomer.getClueDO().getQq());
            customerExcel.setEmail(tCustomer.getClueDO().getEmail());
            customerExcel.setAge(tCustomer.getClueDO().getAge());
            customerExcel.setJob(tCustomer.getClueDO().getJob());
            customerExcel.setYearIncome(tCustomer.getClueDO().getYearIncome());
            customerExcel.setAddress(tCustomer.getClueDO().getAddress());
            customerExcel.setNeedLoadName(ObjectUtils.isEmpty(tCustomer.getNeedLoanDO()) ? Constants.EMPTY : tCustomer.getNeedLoanDO().getTypeValue());
            customerExcel.setProductName(ObjectUtils.isEmpty(tCustomer.getIntentionProductDO()) ? Constants.EMPTY : tCustomer.getIntentionProductDO().getName());
            customerExcel.setSourceName(ObjectUtils.isEmpty(tCustomer.getSourceDO()) ? Constants.EMPTY : tCustomer.getSourceDO().getTypeValue());
            customerExcel.setDescription(tCustomer.getDescription());
            customerExcel.setNextContactTime(tCustomer.getNextContactTime());

            customerExcelList.add(customerExcel);
        });

        return customerExcelList;
    }
}
