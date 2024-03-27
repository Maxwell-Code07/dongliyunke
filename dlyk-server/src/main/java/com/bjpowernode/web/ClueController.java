package com.bjpowernode.web;

import com.alibaba.excel.EasyExcel;
import com.bjpowernode.model.TClue;
import com.bjpowernode.model.TUser;
import com.bjpowernode.query.ClueQuery;
import com.bjpowernode.result.R;
import com.bjpowernode.service.ClueService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PostMapping(value = "/api/importExcel")
    public R importExcel(MultipartFile file, @RequestHeader(value = "Authorization")String token) throws IOException { //file的名字要和前段formData里面的名字相同，否则接收不到

        clueService.importExcel(file.getInputStream(),token);

        return R.OK();
    }

    @GetMapping(value = "/api/clue/{phone}")
    public R checkPhone(@PathVariable(value = "phone")String phone){
        Boolean check = clueService.checkPhone(phone);
        return check ? R.OK() : R.FAIL("该手机号已存在");
    }

    @PostMapping(value = "/api/clue")
    public R addClue(ClueQuery clueQuery, @RequestHeader(value = "Authorization") String token){
        clueQuery.setToken(token);
        int save = clueService.saveClue(clueQuery);

        return save >=1 ? R.OK() : R.FAIL();
    }

    @GetMapping(value = "/api/clue/detail/{id}")
    public R loadClue(@PathVariable(value = "id") Integer id){
        TClue tClue = clueService.getClueById(id);
        return R.OK(tClue);
    }

    @PutMapping(value = "/api/clue")
    public R editClue(ClueQuery clueQuery, @RequestHeader(value = "Authorization") String token){
        clueQuery.setToken(token);
        int update = clueService.updateClue(clueQuery);

        return update >=1 ? R.OK() : R.FAIL();
    }

}
