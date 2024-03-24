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
 * 是否需要贷款转换器
 * @Author hzz
 * @Date 2024-03-24 19:17
 */
public class NeedLoanConverter implements Converter<Integer> {


    /**
     * 把Excel中的数据转换为Java中的数据
     * 也就是Excel中的"需要" ----> Java类中是49
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        // cellData是Excel中读取到的单元格数据，是“需要”、“不需要”
        String cellNeedLoanName = cellData.getStringValue();

        // 从内存中获取数据
        List<TDicValue> tDicValueList= (List<TDicValue>) DlykServerApplication.cacheMap.get(DicEnum.NEEDLOAN.getCode());

        for (TDicValue tDicValue : tDicValueList) {
            Integer id = tDicValue.getId();
            String name = tDicValue.getTypeValue();

            if(cellNeedLoanName.equals(name)){
                return id;
            }
        }

        return -1;
    }
}
