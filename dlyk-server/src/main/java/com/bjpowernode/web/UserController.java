package com.bjpowernode.web;

import com.bjpowernode.model.TUser;
import com.bjpowernode.query.UserQuery;
import com.bjpowernode.result.R;
import com.bjpowernode.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-10 10:55
 */
@RestController
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 获取登录人信息
     *
     * @param authentication
     * @return
     */
    @GetMapping(value = "/api/dashboard/info")
    public R loginInfo(Authentication authentication) {
        TUser tUser = (TUser) authentication.getPrincipal();
        return R.OK(tUser);
    }

    @GetMapping(value = "/api/login/free")
    public R freeLogin() {
        // 流程：
        // 登录 -> 前端读取localStorage ->
        // 携带token的请求头被axios拦截 ->
        // 过滤器验证token ->
        // 到达controller层，能到这里说明token没问题，所以可以直接返回200
        return R.OK();
    }

    /**
     * 分页查询用户接口
     * @param current
     * @return
     */
    @GetMapping(value = "/api/users")
    public R userPage(@RequestParam(value = "current",required = false) Integer current){
        // required = false 表示参数可以传，也可以不传
        // required = true 表示参数必须传，不传会报错
        if(current == null){
            current = 1;
        }
        PageInfo<TUser> pageInfo = userService.getUserByPage(current);
        return R.OK(pageInfo);
    }

    @GetMapping(value = "/api/user/{id}")
    public R userDetail(@PathVariable(value = "id") Integer id){
        TUser tUser =  userService.getUserById(id);
        return R.OK(tUser);
    }

    /**
     * 新增用户接口
     * @param userQuery
     * @return
     */
    @PostMapping(value = "/api/user")
    public R addUser(UserQuery userQuery,@RequestHeader(value = "Authorization") String token){
        userQuery.setToken(token);
        int save = userService.saveUser(userQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    /**
     * 编辑用户接口
     * @param userQuery
     * @return
     */
    @PutMapping(value = "/api/user")
    public R editUser(UserQuery userQuery,@RequestHeader(value = "Authorization") String token){
        userQuery.setToken(token);
        int update = userService.updateUser(userQuery);
        return update >= 1 ? R.OK() : R.FAIL();
    }

    @DeleteMapping(value="/api/user/{id}")
    public R delUser(@PathVariable(value = "id")Integer id){
        int del = userService.delUserById(id);
        return del >=1 ? R.OK() : R.FAIL();
    }

    @DeleteMapping(value="/api/user")
    public R batchDelUser(@RequestParam(value = "ids")String ids){
        // ids = "1,2,3,4,5";
        List<String> idList = Arrays.asList(ids.split(","));

        int batchDel = userService.batchDelUserIds(idList);

        return batchDel >= idList.size() ? R.OK() : R.FAIL();
    }

    /**
     * 查询负责人
     */
    @GetMapping(value = "/api/owner")
    public R owner(){
        List<TUser> ownerList = userService.getOwnerList();
        return R.OK(ownerList);
    }

}
