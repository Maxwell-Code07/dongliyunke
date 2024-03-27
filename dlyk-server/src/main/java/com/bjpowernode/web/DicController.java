package com.bjpowernode.web;

import com.bjpowernode.DlykServerApplication;
import com.bjpowernode.model.TActivity;
import com.bjpowernode.model.TDicValue;
import com.bjpowernode.model.TProduct;
import com.bjpowernode.result.DicEnum;
import com.bjpowernode.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-26 21:19
 */
@RestController
public class DicController {

    @GetMapping(value = "/api/dicvalue/{typeCode}")
    public R dicData(@PathVariable(value = "typeCode") String typeCode) {

        if(typeCode.equals(DicEnum.ACTIVITY.getCode())){ // 当typeCode是activity时
            List<TActivity> tActivityList = (List<TActivity>)DlykServerApplication.cacheMap.get(typeCode);
            return R.OK(tActivityList);
        }else if(typeCode.equals(DicEnum.PRODUCT.getCode())){
            List<TProduct> tProductList = (List<TProduct>)DlykServerApplication.cacheMap.get(typeCode);
            return R.OK(tProductList);
        }else{
            // 因为内存中已经存有字典，所以直接从内存中拿
            List<TDicValue> tDicValueList = (List<TDicValue>)DlykServerApplication.cacheMap.get(typeCode);
            return R.OK(tDicValueList);
        }

    }
}
