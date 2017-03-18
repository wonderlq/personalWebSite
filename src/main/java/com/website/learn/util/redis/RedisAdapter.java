/**
 *
 */
package com.website.learn.util.redis;


import com.alibaba.fastjson.TypeReference;
import com.website.learn.util.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wenxin.liang
 */
public class RedisAdapter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JedisPool pool;

    public RedisAdapter() {
    }

    public RedisAdapter(JedisPool pool) {
        this.pool = pool;
    }

    /**
     * 通过pipeline，批量插入
     * 注意：这个方法只是针对不同的key，相同的field
     * 例如SubjectTranslation，它的key-value结构比较特殊，只能采用这种方式批量插入，无法hmset，使用时请注意！
     * @param kvMap map的key为redis的key，value为redis的value
     */
    public void hsetByPipeline(final Map<String, String> kvMap, final String field){
        execute(new JedisExecutor() {
            @Override
            public Object execute(Jedis jedis) {
                Pipeline pipelineJedis = jedis.pipelined();
                for (Map.Entry<String, String> entry : kvMap.entrySet()) {
                    pipelineJedis.hset(entry.getKey(), field, entry.getValue());
                }
                pipelineJedis.sync();
                return null;
            }
        });
    }


    public List<String> hgetByPipeline(final List<String> keys, final String field) {
        return execute(new JedisExecutor<List<String>>() {
            @Override
            public List<String> execute(Jedis jedis) {
                Pipeline pipelineJedis = jedis.pipelined();
                List<Response<String>> responses = new ArrayList<>();
                for (String key : keys) {
                    responses.add(pipelineJedis.hget(key, field));
                }

                // 一定要先sync()才能执行resp.get()
                // 否则报错：Please close pipeline or multi block before calling this method.
                pipelineJedis.sync();

                List<String> results = new ArrayList<>(responses.size());
                for (Response<String> resp : responses) {
                    results.add(resp.get());
                }

                return results;
            }
        });
    }

    /**
     * 通过pipeline方式，根据角标的投放范围，得到这些投放范围已经投放角标的app
     * @param keys, key为hash结构
     * @return key为app id， value为cornerMark id的map
     */
    public Map<Integer, Integer> getAppIdCornerMarkIdMap(final String... keys) {
        return execute(new JedisExecutor<Map<Integer, Integer>>() {
            @Override
            public Map<Integer, Integer> execute(Jedis jedis) {
                Pipeline pipelineJedis = jedis.pipelined();
                List<Response<Map<String, String>>> responses = new ArrayList<>();
                for(String key:keys){
                    responses.add(pipelineJedis.hgetAll(key));
                }
                pipelineJedis.sync();

                Map<Integer, Integer> appIdCornerMarkIdMap = new HashMap<>(responses.size());
                for(Response<Map<String, String>> resp:responses){
                    Map<String, String> map = resp.get();
                    for(Map.Entry<String, String> entry:map.entrySet()){
                        Integer appId = Integer.parseInt(entry.getKey());
                        Integer cornerMarkId = Integer.parseInt(entry.getValue());
                        if(!appIdCornerMarkIdMap.containsKey(appId)){
                            appIdCornerMarkIdMap.put(appId, cornerMarkId);
                        }
                    }
                }

                return appIdCornerMarkIdMap;
            }
        });
    }

    public Jedis getJedis() {
        return execute(new JedisExecutor<Jedis>() {
            @Override
            public Jedis execute(Jedis jedis) {
                return jedis;
            }
        });
    }

    public <T> T get(final String key, final TypeReference<T> type) {
        return execute(new JedisExecutor<T>() {
            @Override
            public T execute(Jedis jedis) {
                return parseObject(jedis.get(key), type);
            }
        });
    }

    public <T> T get(final String key, final Class<T> clazz) {
        return execute(new JedisExecutor<T>() {
            @Override
            public T execute(Jedis jedis) {
                return parseObject(jedis.get(key), clazz);
            }
        });
    }

    public String get(final String key) {
        return execute(new JedisExecutor<String>() {
            @Override
            public String execute(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    public List<String> mget(final String... keys) {
        return execute(new JedisExecutor<List<String>>() {
            @Override
            public List<String> execute(Jedis jedis) {
                return jedis.mget(keys);
            }
        });
    }

    public <T> List<T> mget(final String[] keys, Class<T> type){
        List<String> list = mget(keys);
        List<T> result = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (String json : list) {
                if (json != null) {
                    result.add((T) parseObject(json, type));
                } else {
                    result.add(null);
                }
            }
        }
        return result;
    }

    /**
     * 批量set到cache
     * @throws JedisException
     */
    public String mset(final String... keysvalues) throws JedisException {
        return execute(new JedisExecutor<String>() {
            @Override
            public String execute(Jedis jedis) {
                return jedis.mset(keysvalues);
            }
        });
    }

    public String set(final String key, final Object o) {
        return execute(new JedisExecutor<String>() {
            @Override
            public String execute(Jedis jedis) {
                return jedis.set(key, toJsonString(o));
            }
        });

    }

    public String setex(final String key, final int seconds, final Object o) {
        return execute(new JedisExecutor<String>() {
            @Override
            public String execute(Jedis jedis) {
                return jedis.setex(key, seconds, toJsonString(o));
            }
        });
    }

    public long del(final String... keys) {
        return execute(new JedisExecutor<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                Long result = 0L;
                // expire key 0代替del操作
                for(String key:keys) {
                    result+=jedis.expire(key, 0);
                }
                return result;
            }
        });
    }

    public Long lpush(final String key, final Object o) {
        return execute(new JedisExecutor<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.lpush(key, toJsonString(o));
            }
        });
    }

    public Long rpush(final String key, final String... strs) {
        return execute(new JedisExecutor<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.rpush(key, strs);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> T rpop(final String key, final Class<T> clazz) {
        return execute(new JedisExecutor<T>() {
            @Override
            public T execute(Jedis jedis) {
                return (T) parseObject(jedis.rpop(key), clazz);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> lrange(final String key, final long start, final long end, Class<T> clazz) {
        List<String> list = execute(new JedisExecutor<List<String>>() {
            @Override
            public List<String> execute(Jedis jedis) {
                return jedis.lrange(key, start, end);
            }
        });
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> vals = new ArrayList<>();
        for (String item : list) {
            vals.add((T) parseObject(item, clazz));
        }
        return vals;
    }


    public List<String> lrange(final String key, final long start, final long end) {
        List<String> list = execute(new JedisExecutor<List<String>>() {
            @Override
            public List<String> execute(Jedis jedis) {
                return jedis.lrange(key, start, end);
            }
        });
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list;
    }

    public Long llen(final String key) {
        return execute(new JedisExecutor<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.llen(key);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private static <T> T parseObject(String val, Object clazz) {
        if (clazz instanceof TypeReference) {
            return JsonConverter.parse(val, (TypeReference<T>) clazz);
        }

        if (clazz == String.class) {
            return (T) val;
        }

        return JsonConverter.parse(val, (Class<T>) clazz);
    }

    private static String toJsonString(Object o) {
        String value;
        if (o instanceof String) {
            value = o.toString();
        } else {
            value = JsonConverter.format(o);
        }
        return value;
    }

    public Set<String> hkeys(final String key) {
        return execute(new JedisExecutor<Set<String>>() {
            @Override
            public Set<String> execute(Jedis jedis) {
                return jedis.hkeys(key);
            }
        });

    }

    public <T> T hget(String key, String field, Class<T> clazz) {
        String val = hashGet(key, field);
        return parseObject(val, clazz);
    }

    public <T> T hget(String key, String field, TypeReference<T> type) {
        String val = hashGet(key, field);
        return parseObject(val, type);
    }

    public String hget(String key, String field) {
        return hashGet(key, field);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> hgetall(String key, TypeReference<T> type) {
        Map<String, String> map = hgetall(key);
        List<T> list = new ArrayList<>();
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                list.add((T) parseObject(entry.getValue(), type));
            }
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> hmget(final String key, final String[] fields,  TypeReference<T> type) {
        List<String> list = hmget(key, fields);
        List<T> result = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (String json : list) {
                if (json != null) {
                    result.add((T) parseObject(json, type));
                } else {
                    result.add(null);
                }
            }
        }
        return result;
    }


    @SuppressWarnings("unchecked")
    public <T> Map<String, T> hgetall(String key, Class<T> clazz) {
        Map<String, String> map = hgetall(key);
        Map<String, T> finalResult = new HashMap<>();
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                finalResult.put(entry.getKey(), JsonConverter.parse(entry.getValue(), clazz));
            }
        }

        return finalResult;
    }

    public void hset(String key, String field, Object value) {
        if (value instanceof String) {
            hashSet(key, field, (String) value);
        } else {
            hashSet(key, field, JsonConverter.format(value));
        }
    }

    private Long hashSet(final String key, final String field, final String value) {
        return execute(new JedisExecutor<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.hset(key, field, value);
            }
        });
    }

    private String hashGet(final String key, final String field) {
        return execute(new JedisExecutor<String>() {

            @Override
            public String execute(Jedis jedis) {
                return jedis.hget(key, field);
            }
        });
    }

    public Map<String, String> hgetall(final String key) {
        return execute(new JedisExecutor<Map<String, String>>() {
            @Override
            public Map<String, String> execute(Jedis jedis) {
                return jedis.hgetAll(key);
            }
        });
    }

    public String hmset(final String key, final Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        return execute(new JedisExecutor<String>() {

            @Override
            public String execute(Jedis jedis) {
                return jedis.hmset(key, map);
            }
        });
    }

    public List<String> hmget(final String key, final String... fields) {
        return execute(new JedisExecutor<List<String>>() {
            @Override
            public List<String> execute(Jedis jedis) {
                return jedis.hmget(key, fields);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> hmget(final String key, final String[] fields, Class<T> type) {
        List<String> list = hmget(key, fields);
        List<T> result = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (String json : list) {
                if (json != null) {
                    result.add((T) parseObject(json, type));
                } else {
                    result.add(null);
                }
            }
        }
        return result;
    }

    @Deprecated
    public Set<String> keys(final String pattern) {
        return execute(new JedisExecutor<Set<String>>() {
            @Override
            public Set<String> execute(Jedis jedis) {
                return jedis.keys(pattern);
            }
        });
    }

    public boolean exists(final String key) {
        return execute(new JedisExecutor<Boolean>() {
            @Override
            public Boolean execute(Jedis jedis) {
                return jedis.exists(key);
            }
        });
    }

    public boolean hexists(final String key, final String field) {
        return execute(new JedisExecutor<Boolean>() {
            @Override
            public Boolean execute(Jedis jedis) {
                return jedis.hexists(key, field);
            }
        });
    }

    public Long hincrBy(final String key, final String field, final long value) {
        return execute(new JedisExecutor<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.hincrBy(key, field, value);
            }
        });
    }

    public Long incr(final String key) {
        return execute(new JedisExecutor<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.incr(key);
            }
        });
    }

    public Long hdel(final String key, final String field) {
        return execute(new JedisExecutor<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.hdel(key, field);
            }
        });
    }

    public List<String> srandmember(final String key, final int count) {
        return execute(new JedisExecutor<List<String>>() {
            @Override
            public List<String> execute(Jedis jedis) {
                return jedis.srandmember(key, count);
            }
        });
    }

    public long sadd(final String key, final String member) {
        return execute(new JedisExecutor<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.sadd(key, member);
            }
        });
    }

    /**
     * 设置key超时时间
     *
     * @param key     缓存key
     * @param timeout 超时时间，单位秒
     */
    public Long expire(final String key, final int timeout) {
        return execute(new JedisExecutor<Long>() {
            @Override
            public Long execute(Jedis jedis) {
                return jedis.expire(key, timeout);
            }
        });
    }

    public <T> T execute(JedisExecutor<T> jedisExecutor) throws JedisException {
        Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = pool.getResource();
            return jedisExecutor.execute(jedis);
        } catch (JedisConnectionException e) {
            logger.error("Redis connection failed when execute.", e);
            broken = true;
            throw e;
        } catch (JedisException e) {
            logger.error("Redis exception when execute.", e);
            broken = true;
            throw e;
        } finally {
            closeResource(jedis, broken);
        }
    }

    private void closeResource(Jedis jedis, boolean broken) {
        if (jedis != null) {
            try {
                if (broken) {
                    pool.returnBrokenResource(jedis);
                } else {
                    pool.returnResource(jedis);
                }
            } catch (Exception e) {
                logger.error("Error happen when return jedis to pool, try to close it directly.", e);
                jedis.close();
            }
        }
    }
}




