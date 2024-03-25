package com.bjpowernode.service;

import com.bjpowernode.model.TClue;
import com.github.pagehelper.PageInfo;

import java.io.InputStream;

/**
 * @Author hzz
 * @Date 2024-03-23 13:17
 */
public interface ClueService {

    PageInfo<TClue> getClueByPage(Integer current);

    void importExcel(InputStream inputStream,String token);
}
