package com.bjpowernode.web;

import com.bjpowernode.result.R;
import com.bjpowernode.result.SummaryData;
import com.bjpowernode.service.StatisticService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author hzz
 * @Date 2024-04-04 13:54
 * 数据统计controller
 */
@RestController
public class StatisticController {

    @Resource
    private StatisticService statisticService;

    @GetMapping(value = "/api/summary/data")
    public R summaryData(){
        SummaryData summaryData = statisticService.loadSummaryData();
        return R.OK(summaryData);
    }

}
