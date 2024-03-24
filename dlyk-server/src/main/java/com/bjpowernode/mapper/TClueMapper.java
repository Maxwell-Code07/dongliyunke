package com.bjpowernode.mapper;

import com.bjpowernode.model.TClue;
import com.bjpowernode.query.BaseQuery;

import java.util.List;

public interface TClueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TClue record);

    int insertSelective(TClue record);

    TClue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TClue record);

    int updateByPrimaryKey(TClue record);

    List<TClue> selectClueByPage(BaseQuery build);

    // void saveClue(List<TClue> );
}