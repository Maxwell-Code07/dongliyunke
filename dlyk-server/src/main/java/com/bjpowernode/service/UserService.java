package com.bjpowernode.service;

import com.bjpowernode.model.TUser;
import com.bjpowernode.query.UserQuery;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-08 20:42
 */
public interface UserService extends UserDetailsService {

    PageInfo<TUser> getUserByPage(Integer current);

    TUser getUserById(Integer id);

    int saveUser(UserQuery userQuery);

    int updateUser(UserQuery userQuery);

    int delUserById(Integer id);

    int batchDelUserIds(List<String> idList);

    List<TUser> getOwnerList();

    // 从主页注册，通过主页注册的用户，默认不启用，account_enabled属性为0
    int registerUser(UserQuery userQuery);

}
