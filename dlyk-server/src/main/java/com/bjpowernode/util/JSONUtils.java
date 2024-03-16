package com.bjpowernode.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

    //jackson这个jar包转json

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 把java对象转成json
     *
     * @param object
     * @return
     */
    public static String toJSON(Object object) {
        try {
             //把java对象转成json
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把json字符串转java对象
     *
     * @param json
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
