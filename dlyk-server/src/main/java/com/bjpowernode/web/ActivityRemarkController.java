package com.bjpowernode.web;

import com.bjpowernode.model.TActivityRemark;
import com.bjpowernode.model.TUser;
import com.bjpowernode.query.ActivityRemarkQuery;
import com.bjpowernode.result.R;
import com.bjpowernode.service.ActivityRemarkService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/api/activity/remark")
    public R activityRemarkPage(@RequestParam(value = "current",required = false) Integer current,
                                @RequestParam(value = "activityId") Integer activityId){

        ActivityRemarkQuery activityRemarkQuery = new ActivityRemarkQuery();
        activityRemarkQuery.setActivityId(activityId);

        // required = false 表示参数可以传，也可以不传
        // required = true 表示参数必须传，不传会报错
        if(current == null){
            current = 1;
        }
        PageInfo<TActivityRemark> pageInfo = activityRemarkService.getActivityRemarkByPage(current,activityRemarkQuery);
        return R.OK(pageInfo);
    }

    @GetMapping(value = "/api/activity/remark/{id}")
    public R activityRemarkPage(@PathVariable(value = "id")Integer id){

        TActivityRemark tActivityRemark = activityRemarkService.getActivityRemarkById(id);
        return R.OK(tActivityRemark);
    }

    @PutMapping(value = "/api/activity/remark")
    public R editActivityRemark(@RequestBody ActivityRemarkQuery activityRemarkQuery, @RequestHeader(value = "Authorization") String token){
        // axios提交post请求,提交过来的是json数据，使用@@RequestBody注解接受
        activityRemarkQuery.setToken(token);
        int update = activityRemarkService.updateActivityRemark(activityRemarkQuery);
        return update >= 1 ? R.OK() : R.FAIL();
    }

    @DeleteMapping(value = "/api/activity/remark/{id}")
    public R delActivityRemark(@PathVariable(value = "id")Integer id){
        int del = activityRemarkService.delActivityRemarkById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

}
