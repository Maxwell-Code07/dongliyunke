package com.bjpowernode.web;

import com.bjpowernode.model.TCustomer;
import com.bjpowernode.query.CustomerQuery;
import com.bjpowernode.result.R;
import com.bjpowernode.service.CustomerService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Author hzz
 * @Date 2024-03-29 17:42
 */
@RestController
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @PostMapping(value = "/api/clue/customer")
    public R convertCustomer(@RequestBody CustomerQuery customerQuery, @RequestHeader(value = "Authorization") String token ){
        customerQuery.setToken(token);
        Boolean convent = customerService.convertCustomer(customerQuery);
        return convent ? R.OK() : R.FAIL();
    }

    @GetMapping(value = "/api/customers")
    public R cluePage(@RequestParam(value = "current", required = false) Integer current){
        if(current == null){
            current = 1;
        }
        PageInfo<TCustomer> pageInfo = customerService.getCustomerByPage(current);
        return R.OK(pageInfo);
    }
}
