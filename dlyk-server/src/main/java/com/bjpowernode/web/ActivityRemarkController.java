package com.bjpowernode.web;

import com.bjpowernode.query.ActivityRemarkQuery;
import com.bjpowernode.result.R;
import com.bjpowernode.service.ActivityRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author hzz
 * @Date 2024-03-21 0:21
 */
@RestController
public class ActivityRemarkController {

    @Resource
    private ActivityRemarkService activityRemarkService;

    @PostMapping(value = "/api/activity/remark")
    public R addActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery, @RequestHeader(value = "Authorization") String token){
        // axios提交post请求,提交过来的是json数据，使用@@RequestBody注解接受
        activityRemarkQuery.setToken(token);
        int save = activityRemarkService.saveActivityRemark(activityRemarkQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }


}
