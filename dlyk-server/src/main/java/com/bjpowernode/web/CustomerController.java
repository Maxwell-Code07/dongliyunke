package com.bjpowernode.web;

import com.bjpowernode.query.CustomerQuery;
import com.bjpowernode.result.R;
import com.bjpowernode.service.CustomerService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author hzz
 * @Date 2024-03-29 17:42
 */
@RestController
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @PostMapping(value = "/api/clue/customer")
    public R  convertCustomer(@RequestBody CustomerQuery customerQuery, @RequestHeader(value = "Authorization") String token ){
        customerQuery.setToken(token);
        Boolean convent = customerService.convertCustomer(customerQuery);
        return convent ? R.OK() : R.FAIL();
    }
}
