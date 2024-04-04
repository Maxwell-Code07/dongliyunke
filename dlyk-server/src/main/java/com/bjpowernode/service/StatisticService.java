package com.bjpowernode.service;

import com.bjpowernode.result.NameValue;
import com.bjpowernode.result.SummaryData;

import java.util.List;

/**
 * @Author hzz
 * @Date 2024-04-04 13:56
 */
public interface StatisticService {
    SummaryData loadSummaryData();

    List<NameValue> loadSaleFunnelData();
}
