package com.bjpowernode.web;

import com.bjpowernode.model.TActivity;
import com.bjpowernode.model.TUser;
import com.bjpowernode.result.R;
import com.bjpowernode.service.ActivityService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public R activityPage(@RequestParam(value = "current",required = false) Integer current){
        // required = false 表示参数可以传，也可以不传
        // required = true 表示参数必须传，不传会报错
        if(current == null){
            current = 1;
        }
        PageInfo<TActivity> pageInfo = activityService.getActivityByPage(current);
        return R.OK(pageInfo);
    }

}
