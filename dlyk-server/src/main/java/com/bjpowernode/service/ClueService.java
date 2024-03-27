package com.bjpowernode.service;

import com.bjpowernode.model.TClue;
import com.bjpowernode.query.ClueQuery;
import com.github.pagehelper.PageInfo;

import java.io.InputStream;

/**
 * @Author hzz
 * @Date 2024-03-23 13:17
 */
public interface ClueService {

    PageInfo<TClue> getClueByPage(Integer current);

    void importExcel(InputStream inputStream,String token);

    Boolean checkPhone(String phone);

    int saveClue(ClueQuery clueQuery);

    TClue getClueById(Integer id);

    int updateClue(ClueQuery clueQuery);
}
