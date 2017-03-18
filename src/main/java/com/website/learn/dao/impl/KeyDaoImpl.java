package com.website.learn.dao.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.TypeReference;
import com.sun.org.apache.regexp.internal.RE;
import com.website.learn.bean.po.KeyPairPo;
import com.website.learn.constant.ValueConstant;
import com.website.learn.dao.KeyDao;
import com.website.learn.util.JsonConverter;
import com.website.learn.util.redis.RedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author dell
 * @since 1.0.0
 * Created On 2017-03-19 01:00
 */
@Repository
public class KeyDaoImpl implements KeyDao {

    @Autowired
    RedisAdapter redisAdapter;


    @Override
    public void saveKeyPairs(String time, KeyPairPo keyPairPo) {
        redisAdapter.setex(ValueConstant.KEY_PAIRS + time, ValueConstant.EXPIRE_TIME,JsonConverter.format(keyPairPo));
    }

    @Override
    public KeyPairPo getKey(String time) {
        String keyCache = redisAdapter.get(ValueConstant.KEY_PAIRS + time);
        if (StringUtils.isEmpty(keyCache)){
            return null;
        }
        return JsonConverter.parse(keyCache, new TypeReference<KeyPairPo>() {});
    }
}
