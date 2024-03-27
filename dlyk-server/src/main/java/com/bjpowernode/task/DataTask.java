package com.bjpowernode.task;


import com.bjpowernode.DlykServerApplication;
import com.bjpowernode.model.TActivity;
import com.bjpowernode.model.TDicType;
import com.bjpowernode.model.TDicValue;
import com.bjpowernode.model.TProduct;
import com.bjpowernode.result.DicEnum;
import com.bjpowernode.service.ActivityService;
import com.bjpowernode.service.DicTypeService;
import com.bjpowernode.service.ProductService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author hzz
 * @Date 2024-03-23 18:04
 */
@EnableScheduling // 开启定时任务
@Component
public class DataTask {

    @Resource
    private DicTypeService dicTypeService;

    @Resource
    private ProductService productService;

    @Resource
    private ActivityService activityService;

    // 调度的意思
    // @Scheduled(cron = "${project.task.cron}",zone = "Asia/Shanghai",timeUnit = TimeUnit.MILLISECONDS,initialDelay = 1000)
    @Scheduled(fixedDelayString = "${project.task.delay}",zone = "Asia/Shanghai",timeUnit = TimeUnit.MILLISECONDS,initialDelay = 1000)
    public void task(){
        // 定时任务要执行的业务逻辑代码
        // System.out.println("定时任务逻辑执行" + new Date());

        List<TDicType> tDicTypesList = dicTypeService.loadAllDicData();

        tDicTypesList.forEach(tDicType -> {
            String typeCode = tDicType.getTypeCode();
            List<TDicValue> tDicValueList = tDicType.getDicValueList();

            // 这个map里装的：（sex,{'男','女'}）
            // key是sex、appellation等参数，value是一个包含着“该参数下选择”的列表，如sex对应的value就是一个包含着“男”和“女”的列表
            DlykServerApplication.cacheMap.put(typeCode, tDicValueList);
        });

        // 查询所有的在售产品
        List<TProduct> tProductList = productService.getAllOnSaleProduct();
        DlykServerApplication.cacheMap.put(DicEnum.PRODUCT.getCode(), tProductList);

        // 查询所有正在进行的市场活动
        List<TActivity> tActivityList = activityService.getOngoingActivity();
        DlykServerApplication.cacheMap.put(DicEnum.ACTIVITY.getCode(), tActivityList);
    }
}
