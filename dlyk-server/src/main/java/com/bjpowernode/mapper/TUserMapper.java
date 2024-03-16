package com.bjpowernode.mapper;

import com.bjpowernode.model.TUser;

import java.util.List;

public interface TUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);

    TUser selectByLoginAct(String username);

    List<TUser> selectUserByPage();

    TUser selectDetailById(Integer id);

    int deleteByIds(List<String> idList);
}