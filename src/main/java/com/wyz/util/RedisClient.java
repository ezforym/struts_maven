package com.wyz.util;

import java.util.ResourceBundle;

import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * TODO
 * 
 * @author wuyize
 * 
 * @version v1.0.0
 * @date 2015年12月4日
 *
 */
public class RedisClient {
          
          private static JedisPool jedisPool;
          
          private static class RedisClientHolder {
                    private static RedisClient redisClient = new RedisClient();
          }
          
          private RedisClient() {
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("com/wyz/config/redis");
                    String host = resourceBundle.getString("redis.ip");
                    int port = Integer.parseInt(resourceBundle.getString("redis.port"));
                    jedisPool = new JedisPool(host, port);
          }
          
          public static RedisClient getInstance() {
                    return RedisClientHolder.redisClient;
          }
          
          /**
           * 向缓存中设置字符串内容
           * 
           * @param key
           *                  key
           * @param value
           *                  value
           * @return
           * @throws Exception
           */
          public boolean set(String key, String value) throws Exception {
                    Jedis jedis = null;
                    try {
                              jedis = jedisPool.getResource();
                              jedis.set(key, value);
                              return true;
                    } catch (Exception e) {
                              e.printStackTrace();
                              return false;
                    } finally {
                              jedisPool.returnResource(jedis);
                    }
          }
          
          /**
           * 向缓存中设置对象
           * 
           * @param key
           * @param value
           * @return
           */
          public boolean set(String key, Object value) {
                    Jedis jedis = null;
                    try {
                              JSONObject json = new JSONObject();
                              json = JSONObject.fromObject(value);
                              String objectJson = json.toString();
                              jedis = jedisPool.getResource();
                              jedis.set(key, objectJson);
                              return true;
                    } catch (Exception e) {
                              e.printStackTrace();
                              return false;
                    } finally {
                              jedisPool.returnResource(jedis);
                    }
          }
          
          /**
           * 删除缓存中得对象，根据key
           * 
           * @param key
           * @return
           */
          public boolean del(String key) {
                    Jedis jedis = null;
                    try {
                              jedis = jedisPool.getResource();
                              jedis.del(key);
                              return true;
                    } catch (Exception e) {
                              e.printStackTrace();
                              return false;
                    } finally {
                              jedisPool.returnResource(jedis);
                    }
          }
          
          /**
           * 根据key 获取内容
           * 
           * @param key
           * @return
           */
          public Object get(String key) {
                    Jedis jedis = null;
                    try {
                              jedis = jedisPool.getResource();
                              return jedis.get(key);
                    } catch (Exception e) {
                              e.printStackTrace();
                              return false;
                    } finally {
                              jedisPool.returnResource(jedis);
                    }
          }
          
          @SuppressWarnings("unchecked")
          public <T> T get(String key, Class<T> clazz) {
                    Jedis jedis = null;
                    try {
                              jedis = jedisPool.getResource();
                              String value = jedis.get(key);
                              JSONObject j = JSONObject.fromObject(value);
                              return (T) JSONObject.toBean(j, clazz);
                    } catch (Exception e) {
                              e.printStackTrace();
                              return null;
                    } finally {
                              jedisPool.returnResource(jedis);
                    }
          }
          
          public Jedis getJedis() {
                    Jedis jedis = null;
                    jedis = jedisPool.getResource();
                    return jedis;
          }
}