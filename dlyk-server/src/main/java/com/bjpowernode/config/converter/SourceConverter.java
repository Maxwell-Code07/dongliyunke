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
 * 线索状态的转换器
 * @Author hzz
 * @Date 2024-03-24 19:22
 */
public class SourceConverter implements Converter<Integer> {

    /**
     * 把Excel中的数据转换为Java中的数据
     * 也就是Excel中的"已联系" ----> Java类中是27
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        // cellData是Excel中读取到的单元格数据，是“车展会”、“网络广告”
        String cellSourceName = cellData.getStringValue();


        // 从内存中获取数据
        List<TDicValue> tDicValueList= (List<TDicValue>) DlykServerApplication.cacheMap.get(DicEnum.SOURCE.getCode());

        for (TDicValue tDicValue : tDicValueList) {
            Integer id = tDicValue.getId();
            String name = tDicValue.getTypeValue();

            if(cellSourceName.equals(name)){
                return id;
            }
        }

        return -1;
    }
}
