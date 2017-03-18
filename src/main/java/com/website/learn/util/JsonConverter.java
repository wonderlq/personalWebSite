package com.website.learn.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 00:51
 */
public class JsonConverter {
    private static final Logger logger = LoggerFactory.getLogger(JsonConverter.class);

    public static String format(Object value) {
        String json = JSON.toJSONString(value);
        int length = json.length();
        if (length>Integer.MAX_VALUE){
            logger.info("the json is too long!");
        }
        return json;
    }

    public static <T> T parse(String value, TypeReference<T> clazz) {
        return JSON.parseObject(value,clazz);
    }

    public static <T> T parse(String value, Class<T> clazz) {
        return JSON.parseObject(value,clazz);
    }
}
