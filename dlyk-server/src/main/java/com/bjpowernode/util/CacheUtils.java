package com.bjpowernode.util;

import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @Author hzz
 * @Date 2024-03-17 14:51
 */
public class CacheUtils {

    /**
     * 带有缓存的查询工具方法
     * @param cacheSelector
     * @param databaseSelector
     * @param cacheSave
     * @return
     * @param <T>
     */
    public static <T> T getCacheData(Supplier<T> cacheSelector, Supplier<T> databaseSelector, Consumer<T> cacheSave){
        // 从redis中查
        T data = cacheSelector.get();
        // 如果redis没查到
        if(ObjectUtils.isEmpty(data)){
            // 从数据库中查
            data = databaseSelector.get();
            // 数据库查到了数据
            if(!ObjectUtils.isEmpty(data)) {
                // 把数据放入redis
                cacheSave.accept(data);
            }
        }
        // 返回数据
        return data;
    }
}
