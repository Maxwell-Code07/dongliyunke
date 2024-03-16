package com.bjpowernode.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {

    void setValue(String key, Object value);

    Object getValue(String key);

    Boolean removeValue(String key);

    // 设定过期时间
    Boolean expire(String key, Long timeOUt,TimeUnit timeUnit);
}
