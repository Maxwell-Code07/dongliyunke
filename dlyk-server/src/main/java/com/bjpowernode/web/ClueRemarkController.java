package com.bjpowernode.web;

import com.bjpowernode.model.TActivityRemark;
import com.bjpowernode.model.TClueRemark;
import com.bjpowernode.query.ActivityRemarkQuery;
import com.bjpowernode.query.ClueRemarkQuery;
import com.bjpowernode.result.R;
import com.bjpowernode.service.ClueRemarkService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @Author hzz
 * @Date 2024-03-28 0:51
 */
@RestController
public class ClueRemarkController {

    @Resource
    private ClueRemarkService clueRemarkService;


    @PostMapping(value = "/api/clue/remark")
    public R addActivityRemark(@RequestBody ClueRemarkQuery clueRemarkQuery, @RequestHeader(value = "Authorization") String token){
        // axios提交post请求,提交过来的是json数据，使用@@RequestBody注解接受
        clueRemarkQuery.setToken(token);
        int save = clueRemarkService.saveClueRemark(clueRemarkQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    @GetMapping(value = "/api/clue/remark")
    public R clueRemarkPage(@RequestParam(value = "current",required = false) Integer current,
                                @RequestParam(value = "clueId") Integer clueId){

        ClueRemarkQuery clueRemarkQuery = new ClueRemarkQuery();
        clueRemarkQuery.setClueId(clueId);

        // required = false 表示参数可以传，也可以不传
        // required = true 表示参数必须传，不传会报错
        if(current == null){
            current = 1;
        }
        PageInfo<TClueRemark> pageInfo = clueRemarkService.getClueRemarkByPage(current,clueRemarkQuery);
        return R.OK(pageInfo);
    }
}
