package com.bjpowernode.web;

import com.bjpowernode.model.TClue;
import com.bjpowernode.model.TUser;
import com.bjpowernode.result.R;
import com.bjpowernode.service.ClueService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author hzz
 * @Date 2024-03-23 13:14
 */
@RestController
public class ClueController {

    @Resource
    private ClueService clueService;

    @GetMapping(value = "/api/clues")
    public R cluePage(@RequestParam(value = "current",required = false) Integer current){
        // required = false 表示参数可以传，也可以不传
        // required = true 表示参数必须传，不传会报错
        if(current == null){
            current = 1;
        }
        PageInfo<TClue> pageInfo = clueService.getClueByPage(current);
        return R.OK(pageInfo);
    }
}