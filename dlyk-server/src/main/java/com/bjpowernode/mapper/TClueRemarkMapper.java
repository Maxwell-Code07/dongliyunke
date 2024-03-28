package com.bjpowernode.mapper;

import com.bjpowernode.commons.DataScope;
import com.bjpowernode.model.TClueRemark;
import com.bjpowernode.query.ClueRemarkQuery;

import java.util.List;

public interface TClueRemarkMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TClueRemark record);

    int insertSelective(TClueRemark record);

    TClueRemark selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TClueRemark record);

    int updateByPrimaryKey(TClueRemark record);

    @DataScope(tableAlias = "tcr", tableField = "create_by")
    List<TClueRemark> selectClueRemarkByPage(ClueRemarkQuery clueRemarkQuery);
}