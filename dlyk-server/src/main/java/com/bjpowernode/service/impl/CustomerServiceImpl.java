package com.bjpowernode.service.impl;

import com.bjpowernode.manager.CustomerManager;
import com.bjpowernode.mapper.TClueMapper;
import com.bjpowernode.mapper.TCustomerMapper;
import com.bjpowernode.query.CustomerQuery;
import com.bjpowernode.service.CustomerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author hzz
 * @Date 2024-03-29 17:48
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Resource
    private CustomerManager customerManager;

    @Override
    public Boolean convertCustomer(CustomerQuery customerQuery) {
        return customerManager.convertCustomer(customerQuery);
    }
}
