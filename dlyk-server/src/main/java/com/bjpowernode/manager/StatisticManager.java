package com.bjpowernode.manager;

import com.bjpowernode.mapper.*;

import com.bjpowernode.result.NameValue;
import com.bjpowernode.result.SummaryData;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author hzz
 * @Date 2024-04-04 14:07
 */
@Component
public class StatisticManager {

    // 活动Mapper
    @Resource
    private TActivityMapper tActivityMapper;

    // 线索Mapper
    @Resource
    private TClueMapper tClueMapper;

    // 客户Mapper
    @Resource
    private TCustomerMapper tCustomerMapper;

    // 交易表就是用户配置，型号什么都选好了，然后下个订单，等待用户支付，其实就是订单表
    @Resource
    private TTranMapper tTranMapper;

    public SummaryData loadSummaryData() {
        //有效的市场活动总数
        Integer effectiveActivityCount  = tActivityMapper.selectOngoingActivity().size(); // 偷懒一下，不单独写方法了

        //总的市场活动数
        Integer totalActivityCount  = tActivityMapper.selectByCount();

        // 线索总数
        Integer totalClueCount  = tClueMapper.selectClueByCount();

        // 客户总数
        Integer totalCustomerCount  = tCustomerMapper.selectByCount();

        // 成功的交易额
        BigDecimal successTranAmount = tTranMapper.selectBySuccessTranAmount();

        // 总的交易额（包含成功和不成功的）
        BigDecimal totalTranAmount =  tTranMapper.selectByTotalTranAmount();
        
        return SummaryData.builder()
                .effectiveActivityCount(effectiveActivityCount)
                .totalActivityCount(totalActivityCount)
                .totalClueCount(totalClueCount)
                .totalCustomerCount(totalCustomerCount)
                .successTranAmount(successTranAmount)
                .totalTranAmount(totalTranAmount)
                .build();
    }

    public List<NameValue> loadSaleFunnelData() {

        List<NameValue> resultList = new ArrayList<>();

        int clueCount = tClueMapper.selectClueByCount();
        int customerCount = tCustomerMapper.selectByCount();
        int tranCount = tTranMapper.selectByTotalTranCount();
        int tranSuccessCount = tTranMapper.selectBySuccessTranCount();

        NameValue clue = NameValue.builder().name("线索").value(clueCount).build();
        resultList.add(clue);

        NameValue customer = NameValue.builder().name("客户").value(customerCount).build();
        resultList.add(customer);

        NameValue tran = NameValue.builder().name("交易").value(tranCount).build();
        resultList.add(tran);

        NameValue tranSuccess = NameValue.builder().name("成交").value(tranSuccessCount).build();
        resultList.add(tranSuccess);

        return resultList;
    }
}
