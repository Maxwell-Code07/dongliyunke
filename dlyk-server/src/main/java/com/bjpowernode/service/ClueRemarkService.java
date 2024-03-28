package com.bjpowernode.service;

import com.bjpowernode.model.TClueRemark;
import com.bjpowernode.query.ClueRemarkQuery;
import com.github.pagehelper.PageInfo;

/**
 * @Author hzz
 * @Date 2024-03-28 0:57
 */
public interface ClueRemarkService {


    int saveClueRemark(ClueRemarkQuery clueRemarkQuery);

    PageInfo<TClueRemark> getClueRemarkByPage(Integer current, ClueRemarkQuery clueRemarkQuery);
}
