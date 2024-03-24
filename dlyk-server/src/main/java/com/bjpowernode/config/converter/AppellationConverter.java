package com.bjpowernode.config.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.bjpowernode.DlykServerApplication;
import com.bjpowernode.model.TDicValue;
import com.bjpowernode.result.DicEnum;

import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-23 17:57
 * 称呼的转换器
 * Excel中的"先生" ----> Java类中是18
 * Excel中的"女士" ----> Java类中是41
 */
public class AppellationConverter implements Converter<Integer> {
    /**
     * 把Excel中的数据转换为Java中的数据
     * 也就是Excel中的"先生" ----> Java类中是18
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        // cellData是Excel中读取到的单元格数据，是“先生”、“女士”
        String cellAppellationName = cellData.getStringValue();


        // 从内存中获取数据
        List<TDicValue> tDicValueList= (List<TDicValue>)DlykServerApplication.cacheMap.get(DicEnum.APPELLATION.getCode());

        for (TDicValue tDicValue : tDicValueList) {
            Integer id = tDicValue.getId();
            String name = tDicValue.getTypeValue();

            if(cellAppellationName.equals(name)){
                return id;
            }
        }

        return -1;
    }
}
