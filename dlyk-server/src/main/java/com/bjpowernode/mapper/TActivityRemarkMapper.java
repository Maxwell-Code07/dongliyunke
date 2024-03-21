package com.bjpowernode.mapper;

import com.bjpowernode.commons.DataScope;
import com.bjpowernode.model.TActivityRemark;
import com.bjpowernode.query.ActivityRemarkQuery;
import com.bjpowernode.query.BaseQuery;

import java.util.List;

public interface TActivityRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TActivityRemark record);

    int insertSelective(TActivityRemark record);

    TActivityRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TActivityRemark record);

    int updateByPrimaryKey(TActivityRemark record);

    @DataScope(tableAlias = "tar",tableField = "create_by")
    List<TActivityRemark> selectActivityRemarkByPage(ActivityRemarkQuery activityRemarkQuery);
}