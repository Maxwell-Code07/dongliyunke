package com.bjpowernode.manager;

import com.bjpowernode.mapper.TClueMapper;
import com.bjpowernode.mapper.TCustomerMapper;
import com.bjpowernode.model.TClue;
import com.bjpowernode.model.TCustomer;
import com.bjpowernode.query.CustomerQuery;
import com.bjpowernode.util.JWTUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author hzz
 * @Date 2024-03-29 18:18
 */
@Component
public class CustomerManager {

    @Resource
    private TCustomerMapper tCustomerMapper;

    @Resource
    private TClueMapper tClueMapper;

    @Transactional(rollbackFor = Exception.class)
    public Boolean convertCustomer(CustomerQuery customerQuery) {
        // 1.验证该线索是否已经转过客户，转过的就不能再转了
        TClue tClue = tClueMapper.selectByPrimaryKey(customerQuery.getClueId());
        if(tClue.getState() == -1){
            throw new RuntimeException("该线索已经转过客户，不能再转了");
        }

        // 2.向客户表插入一条数据
        TCustomer tCustomer = new TCustomer();
        // 把customerQuery对象里面的属性数据复制到tCustomer对象里面去
        BeanUtils.copyProperties(customerQuery,tCustomer);
        tCustomer.setCreateTime(new Date()); // 创建时间

        // 登录人的id
        Integer loginUserid = JWTUtils.parseUserFromJWT(customerQuery.getToken()).getId();
        tCustomer.setCreateBy(loginUserid); // 创建人

        int insert = tCustomerMapper.insertSelective(tCustomer);

        // 3.把线索表的数据状态改名为-1(已转客户)
        TClue clue = new TClue();
        clue.setId(customerQuery.getClueId());
        clue.setState(-1);
        int update = tClueMapper.updateByPrimaryKeySelective(clue);
        return insert >= 1 && update >= 1;
    }
}
