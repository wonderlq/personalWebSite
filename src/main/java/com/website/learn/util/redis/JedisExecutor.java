package com.website.learn.util.redis;

import redis.clients.jedis.Jedis;

/**
 * @author zhenfei.wang
 * @since 1.0.0
 * Created On: 2015-04-17 17:22
 */

public interface JedisExecutor<T> {

    public T execute(Jedis jedis);

}
