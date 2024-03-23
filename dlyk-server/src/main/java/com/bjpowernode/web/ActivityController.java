package com.bjpowernode.web;

import com.bjpowernode.model.TActivity;
import com.bjpowernode.model.TUser;
import com.bjpowernode.query.ActivityQuery;
import com.bjpowernode.query.UserQuery;
import com.bjpowernode.result.R;
import com.bjpowernode.service.ActivityService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Author hzz
 * @Date 2024-03-17 13:37
 */
@RestController
public class ActivityController {

    @Resource
    private ActivityService activityService;

    /**
     * 分页查询用户接口
     * @param current
     * @return
     */
    @GetMapping(value = "/api/activitys")
    public R activityPage(@RequestParam(value = "current",required = false) Integer current, ActivityQuery activityQuery){
        // required = false 表示参数可以传，也可以不传
        // required = true 表示参数必须传，不传会报错
        if(current == null){
            current = 1;
        }
        PageInfo<TActivity> pageInfo = activityService.getActivityByPage(current,activityQuery);
        return R.OK(pageInfo);
    }

    @PostMapping(value = "/api/activity")
    public R addActivity(ActivityQuery activityQuery, @RequestHeader(value = "Authorization") String token){
        activityQuery.setToken(token);
        int save = activityService.saveActivity(activityQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    @GetMapping(value = "/api/activity/{id}")
    public R loadActivity(@PathVariable(value = "id")Integer id){
        TActivity tActivity = activityService.getActivityById(id);
        return R.OK(tActivity);
    }

    @PutMapping(value = "/api/activity")
    public R editActivity(ActivityQuery activityQuery, @RequestHeader(value = "Authorization") String token){
        activityQuery.setToken(token);
        int update = activityService.updateActivity(activityQuery);
        return update >= 1 ? R.OK() : R.FAIL();
    }

}
