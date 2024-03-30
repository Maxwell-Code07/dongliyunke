package com.bjpowernode.service;

import com.bjpowernode.model.TCustomer;
import com.bjpowernode.query.CustomerQuery;
import com.bjpowernode.result.CustomerExcel;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-29 17:46
 */
public interface CustomerService {
    Boolean convertCustomer(CustomerQuery customerQuery);

    PageInfo<TCustomer> getCustomerByPage(Integer current);

    List<CustomerExcel> getCustomerByExcel();
}
