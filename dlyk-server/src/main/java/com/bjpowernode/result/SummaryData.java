package com.bjpowernode.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author hzz
 * @Date 2024-04-04 13:57
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SummaryData {

    //有效的市场活动总数
    private Integer effectiveActivityCount;

    //总的市场活动数
    private Integer totalActivityCount;

    // 线索总数
    private Integer totalClueCount;

    // 客户总数
    private Integer totalCustomerCount;

    // 成功的交易额
    private BigDecimal successTranAmount;

    // 总的交易额（包含成功和不成功的）
    private BigDecimal totalTranAmount;

}
