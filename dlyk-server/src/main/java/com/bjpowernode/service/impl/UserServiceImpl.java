package com.bjpowernode.service.impl;


import com.bjpowernode.constant.Constants;
import com.bjpowernode.mapper.TUserMapper;
import com.bjpowernode.model.TActivityRemark;
import com.bjpowernode.model.TUser;
import com.bjpowernode.query.UserQuery;
import com.bjpowernode.service.UserService;
import com.bjpowernode.util.JWTUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-08 20:43
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       TUser tUser = tUserMapper.selectByLoginAct(username);

       if(tUser == null){
           throw new UsernameNotFoundException("登录账号不存在！");
       }

        return tUser;
    }

    @Override
    public PageInfo<TUser> getUserByPage(Integer current) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TUser> list = tUserMapper.selectUserByPage();
        // 3.封装分页数据到PageInfo
        PageInfo<TUser> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public TUser getUserById(Integer id) {
        return tUserMapper.selectDetailById(id);
    }

    @Override
    public int saveUser(UserQuery userQuery) {

        TUser tUser = new TUser();

        // 把UserQuery对象里面的属性数据赋值到TUser对象里面去(复制要求：两个对象的属性名相同，属性类型相同，才能复制)
        BeanUtils.copyProperties(userQuery,tUser);

        tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd())); // 密码加密
        tUser.setCreateTime(new Date()); // 创建时间

        // 登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setCreateBy(loginUserId); // 创建人

        // insertSelective方法 相比于 insert方法 的优势在于，前者会检测对象的属性有没有为空的，一旦有一个为空，就不插入了
        return tUserMapper.insertSelective(tUser);
    }

    @Override
    public int updateUser(UserQuery userQuery) {
        TUser tUser = new TUser();

        // 把UserQuery对象里面的属性数据赋值到TUser对象里面去(复制要求：两个对象的属性名相同，属性类型相同，才能复制)
        BeanUtils.copyProperties(userQuery,tUser);

        if(StringUtils.hasText(userQuery.getLoginPwd())){
            tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd())); // 密码加密
        }

        tUser.setEditTime(new Date()); // 编辑时间

        // 登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setEditBy(loginUserId); // 编辑人

        // updateByPrimaryKeySelective方法 相比于 updateByPrimaryKey方法 的优势在于，前者会有选择的update，万一有哪个值为空，就不给那个值update了，后者啥都不管直接全部update
        return tUserMapper.updateByPrimaryKeySelective(tUser);
    }

    @Override
    public int delUserById(Integer id) {
        return tUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batchDelUserIds(List<String> idList) {
        return tUserMapper.deleteByIds(idList);
    }
}
