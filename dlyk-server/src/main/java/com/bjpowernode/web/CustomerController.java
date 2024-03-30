package com.bjpowernode.web;

import com.alibaba.excel.EasyExcel;
import com.bjpowernode.constant.Constants;
import com.bjpowernode.model.TCustomer;
import com.bjpowernode.query.CustomerQuery;
import com.bjpowernode.result.CustomerExcel;
import com.bjpowernode.result.R;
import com.bjpowernode.service.CustomerService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

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

    /**
     * 导出Excel
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/api/exportExcel")
    public void exportExcel(HttpServletResponse response) throws IOException {
        // 要想让浏览器弹出下载框，后端要设置一下响应头信息
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码,和easyexcel没有关系
        response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(Constants.EXCEL_FILE_NAME + System.currentTimeMillis(),"utf-8") + ".xlsx");

        // 2.后端查询数据库数据，把数据写入Excel，然后把Excel以IO流的方式输出到前端浏览器
        List<CustomerExcel> dataList = customerService.getCustomerByExcel();

        EasyExcel.write(response.getOutputStream(), CustomerExcel.class)
                .sheet()
                .doWrite(dataList);
    }
}
