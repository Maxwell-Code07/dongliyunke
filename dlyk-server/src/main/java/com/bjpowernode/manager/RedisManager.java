package com.bjpowernode.manager;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author hzz
 * @Date 2024-03-17 15:11
 */
@Component
public class RedisManager {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    public Object getValue(String key){
        /* redisTemplate操作Redis中的List数据类型，
           获取指定key的整个列表内容，起始下标为0，结束下标为-1，
           表示获取整个列表。返回值为List类型，包含指定key的所有元素
         */
        return redisTemplate.opsForList().range(key,0,-1);
    }

    public <T> Object setValue(String key, Collection<T> data){
        // string
        // list
        // hash
        // set
        // zset
        Object[] t = new Object[data.size()];
        data.toArray(t);

        // 这样子最终渲染在页面上的列表是倒序
        // return redisTemplate.opsForList().leftPushAll(key,t);

        // 要让列表正序，应该采用
        return redisTemplate.opsForList().rightPushAll(key,t);
    }

}
