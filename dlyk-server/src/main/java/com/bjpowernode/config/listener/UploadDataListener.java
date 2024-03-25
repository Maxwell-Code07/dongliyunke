package com.bjpowernode.config.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.bjpowernode.mapper.TClueMapper;
import com.bjpowernode.model.TClue;
import com.bjpowernode.model.TUser;
import com.bjpowernode.util.JSONUtils;

import com.bjpowernode.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * @Author hzz
 * @Date 2024-03-23 16:56
 * 每读一行Excel的数据，就会触发该监听器中的invoke()方法，Excel读完之后会触发该监听器中的doAfterAllAnalysed()方法
 */
@Slf4j
public class UploadDataListener implements ReadListener<TClue> {
    /**
     * 每隔100条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    // 缓存List
    private List<TClue> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private TClueMapper tClueMapper;
    private String token;


    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     */
    public UploadDataListener(TClueMapper tClueMapper,String token) {
        this.tClueMapper = tClueMapper;
        this.token = token;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param tClue    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */

    public void invoke(TClue tClue, AnalysisContext context) {
        log.info("读取到的每一条数据:{}", JSONUtils.toJSON(tClue));

        // 给读到的clue对象设置创建时间（导入时间）和创建人（导入人）
        tClue.setCreateTime(new Date());
        TUser tUser = JWTUtils.parseUserFromJWT(token);
        tClue.setCreateBy(tUser.getId());

        // 每读到一行，就把该数据放入到一个缓存List中
        cachedDataList.add(tClue);

        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            // 把缓存list中的数据写入到数据库
            saveData();

            // 存储完成清空 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        tClueMapper.saveClue(cachedDataList);
        log.info("存储数据库成功！");
    }
}
