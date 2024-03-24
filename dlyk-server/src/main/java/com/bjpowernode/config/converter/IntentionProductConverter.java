package com.bjpowernode.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.bjpowernode.DlykServerApplication;
import com.bjpowernode.model.TDicValue;
import com.bjpowernode.model.TProduct;
import com.bjpowernode.result.DicEnum;

import java.util.List;

/**
 * 意向产品转换器
 * @Author hzz
 * @Date 2024-03-24 19:20
 * Excel中的比亚迪e2 ---> java中的2
 * 秦PLUS EV ---> 7
 */
public class IntentionProductConverter implements Converter<Integer> {

    /**
     * 把Excel中的数据转换为Java中的数据
     * 也就是Excel中的"意向不明" ----> Java类中是48
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        // cellData是Excel中读取到的单元格数据，是“比亚迪e2”、“秦PLUS EV”
        String cellIntentionProductName = cellData.getStringValue();

        // 从内存中获取数据
        List<TProduct> tDicValueList= (List<TProduct>) DlykServerApplication.cacheMap.get(DicEnum.PRODUCT.getCode());

        for (TProduct tProduct : tDicValueList) {
            Integer id = tProduct.getId();
            String name = tProduct.getName();

            if(cellIntentionProductName.equals(name)){
                return id;
            }
        }

        return -1;
    }

}
