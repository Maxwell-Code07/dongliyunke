package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.TUserMapper;
import com.bjpowernode.model.TUser;
import com.bjpowernode.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author hzz
 * @Date 2024-03-09 12:40
 */
@SpringBootTest
class UserServiceImplTest {

    @Resource
    private TUserMapper tUserMapper;

    @Test
    void UserServiceTest(){
        TUser admin = tUserMapper.selectByLoginAct("admin");
        System.out.println(admin.getRoleList());
        System.out.println(admin.getLoginAct());
        System.out.println(admin.getLoginPwd());


    }

}