package com.bjpowernode.service.impl;


import com.bjpowernode.constant.Constants;
import com.bjpowernode.manager.RedisManager;
import com.bjpowernode.mapper.TPermissionMapper;
import com.bjpowernode.mapper.TRoleMapper;
import com.bjpowernode.mapper.TUserMapper;
import com.bjpowernode.mapper.TUserRoleMapper;
import com.bjpowernode.model.TPermission;
import com.bjpowernode.model.TRole;
import com.bjpowernode.model.TUser;
import com.bjpowernode.model.TUserRole;
import com.bjpowernode.query.BaseQuery;
import com.bjpowernode.query.UserQuery;
import com.bjpowernode.service.UserService;
import com.bjpowernode.util.CacheUtils;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    @Resource
    private TRoleMapper tRoleMapper;

    @Resource
    private RedisManager redisManager;

    @Resource
    private TPermissionMapper tPermissionMapper;

    @Resource
    private TUserRoleMapper tUserRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser tUser = tUserMapper.selectByLoginAct(username);

        if (tUser == null) {
            throw new UsernameNotFoundException("登录账号不存在！");
        }

        // 查询一下当前用户的角色
        List<TRole> tRoleList = tRoleMapper.selectByUserId(tUser.getId());
        // 字符串的角色列表
        List<String> stringRoleList = new ArrayList<>();
        tRoleList.forEach(tRole -> {
            stringRoleList.add(tRole.getRole());
        });

        // 设置用户的角色
        tUser.setRoleList(stringRoleList);

        // 查询一下该用户有哪些菜单权限
        List<TPermission> menuPermissionList = tPermissionMapper.selectMenuPermissionByUserId(tUser.getId());
        tUser.setMenuPermissionList(menuPermissionList);

        // 查询一下该用户有哪些功能权限
        List<TPermission> buttonPermissionList = tPermissionMapper.selectButtonPermissionByUserId(tUser.getId());
        List<String> stringPermissionList = new ArrayList<>();
        buttonPermissionList.forEach(tPermission -> {
            stringPermissionList.add(tPermission.getCode()); // code就是权限表示符
        });
        tUser.setPermissionList(stringPermissionList); // 设置用户的权限表示符
        return tUser;
    }

    @Override
    public PageInfo<TUser> getUserByPage(Integer current) {
        // 1.设置PageHelper
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        // 2.查询
        List<TUser> list = tUserMapper.selectUserByPage(BaseQuery.builder().build());
        // 3.封装分页数据到PageInfo
        PageInfo<TUser> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public TUser getUserById(Integer id) {
        return tUserMapper.selectDetailById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int saveUser(UserQuery userQuery) {

        TUser tUser = new TUser();

        // 把UserQuery对象里面的属性数据赋值到TUser对象里面去(复制要求：两个对象的属性名相同，属性类型相同，才能复制)
        BeanUtils.copyProperties(userQuery, tUser);

        tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd())); // 密码加密
        tUser.setCreateTime(new Date()); // 创建时间

        // 登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setCreateBy(loginUserId); // 创建人

        // insertSelective方法 相比于 insert方法 的优势在于，前者会检测对象的属性有没有为空的，一旦有一个为空，就不插入了
        return tUserMapper.insertSelective(tUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateUser(UserQuery userQuery) {
        TUser tUser = new TUser();

        // 把UserQuery对象里面的属性数据赋值到TUser对象里面去(复制要求：两个对象的属性名相同，属性类型相同，才能复制)
        BeanUtils.copyProperties(userQuery, tUser);

        if (StringUtils.hasText(userQuery.getLoginPwd())) {
            tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd())); // 密码加密
        }

        tUser.setEditTime(new Date()); // 编辑时间

        // 登录人的id
        Integer loginUserId = JWTUtils.parseUserFromJWT(userQuery.getToken()).getId();
        tUser.setEditBy(loginUserId); // 编辑人

        // updateByPrimaryKeySelective方法 相比于 updateByPrimaryKey方法 的优势在于，前者会有选择的update，万一有哪个值为空，就不给那个值update了，后者啥都不管直接全部update
        return tUserMapper.updateByPrimaryKeySelective(tUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int delUserById(Integer id) {
        return tUserMapper.deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int batchDelUserIds(List<String> idList) {
        return tUserMapper.deleteByIds(idList);
    }

    @Override
    public List<TUser> getOwnerList() {
        // 1.从reids查询
        // 2.从redis查询查不到，就从数据库查询，并且把数据放入redis(3分钟过期)
        return CacheUtils.getCacheData(() -> {
                // 生产，从缓存redis查询数据
                return (List<TUser>)redisManager.getValue(Constants.REDIS_OWNER_KEY);
            },
            () -> {
                // 生产，从mysql查询数据
                return (List<TUser>)tUserMapper.selectByOwner();
            },
            (t) -> {
                // 消费，把数据放入缓存redis
                redisManager.setValue(Constants.REDIS_OWNER_KEY,t);
            }
        );
    }

    // 从主页注册，通过主页注册的用户，默认不启用，account_enabled属性为0
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int registerUser(UserQuery userQuery) {

        if(tUserMapper.selectByLoginAct(userQuery.getLoginAct()) != null){
            throw new RuntimeException("该账号已存在！");
        }

        userQuery.setAccountNoExpired(1);
        userQuery.setCredentialsNoExpired(1);
        userQuery.setAccountNoLocked(1);
        userQuery.setAccountEnabled(0);

        TUser tUser = new TUser();

        // 把UserQuery对象里面的属性数据赋值到TUser对象里面去(复制要求：两个对象的属性名相同，属性类型相同，才能复制)
        BeanUtils.copyProperties(userQuery, tUser);

        tUser.setLoginPwd(passwordEncoder.encode(userQuery.getLoginPwd())); // 密码加密
        tUser.setCreateTime(new Date()); // 创建时间

        // insertSelective方法 相比于 insert方法 的优势在于，前者会检测对象的属性有没有为空的，一旦有一个为空，就不插入了
        int flag = tUserMapper.insert(tUser);

        TUserRole tUserRole = new TUserRole();
        // 用户编号为刚刚插入的用户的id
        tUserRole.setUserId(tUser.getId());
        // 角色编号为2
        tUserRole.setRoleId(2);

        flag += tUserRoleMapper.insertSelective(tUserRole);
        return flag;
    }
}
