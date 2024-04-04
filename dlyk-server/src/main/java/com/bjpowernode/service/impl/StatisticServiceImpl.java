package com.bjpowernode.service.impl;

import com.bjpowernode.manager.StatisticManager;
import com.bjpowernode.result.NameValue;
import com.bjpowernode.result.SummaryData;
import com.bjpowernode.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author hzz
 * @Date 2024-04-04 14:05
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    @Resource
    private StatisticManager statisticManager;

    @Override
    public SummaryData loadSummaryData() {
        return statisticManager.loadSummaryData();
    }

    @Override
    public List<NameValue> loadSaleFunnelData() {
        return statisticManager.loadSaleFunnelData();
    }

    @Override
    public List<NameValue> loadsourcePieData() {
        return statisticManager.loadsourcePieData();
    }
}
