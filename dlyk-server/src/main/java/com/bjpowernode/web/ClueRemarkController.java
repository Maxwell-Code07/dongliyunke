package com.bjpowernode.web;

import com.bjpowernode.query.ClueRemarkQuery;
import com.bjpowernode.result.R;
import com.bjpowernode.service.ClueRemarkService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
}
