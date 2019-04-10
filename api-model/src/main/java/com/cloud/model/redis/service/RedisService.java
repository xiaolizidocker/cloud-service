package com.cloud.model.redis.service;

import java.util.Map;
import java.util.Set;
/**
 * Redis操作类
 * @author zhouyu
 *
 */
public interface RedisService {
	
	/**
	 * 指定缓存失效时间
	 * @param key
	 * @param time 单位 s
	 * @return
	 */
	public boolean expire(String key, long time);
	
	/**
	 * 
	 * 根据key获取过期时间 单位 S
	 * @param key
	 * @return
	 */
	public long getExpire(String key);
	
	/**
	 * 
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key);
	
	/**
	 * 删除缓存
	 * @param key
	 */
	public void delete(String... key);
	
	/**
	 * 普通缓存获取值
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * 普通缓存放入
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, Object value);
	
	/**
	 * 普通缓存放入并设置时间  单位 S
	 * @param key
	 * @param value
	 * @param timeOut
	 * @return
	 */
	public boolean set(String key, Object value, long timeOut);
	
	
	/**
	 * HashGet
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object getHash(String key, String hashKey);
	
	/**
	 * 获取key的所有hash值
	 * @param key
	 * @return
	 */
	public Map<Object, Object> getAllHash(String key);
	
	/**
	 * redis缓存hashMap
	 * @param key
	 * @param hashMap
	 * @return
	 */
	public boolean setHash(String key, Map<String, Object> hashMap);
	
	/**
	 * redis缓存hash,并设置超时时间 单位 S
	 * @param key
	 * @param hashMap
	 * @param timeOut
	 * @return
	 */
	public boolean setHash(String key, Map<String, Object> hashMap, long timeOut);
	
	/**
	 * redis缓存二级值，并设置超时时间 单位S
	 * @param key
	 * @param hashKey
	 * @param value
	 * @param timeOut
	 * @return
	 */
	public boolean setHash(String key, String hashKey, Object value, long timeOut);
	
	/**
	 * 删除hash表中的值
	 * @param key
	 * @param hashKeys  可以使多个 不能为null
	 */
	public void deleteHash(String key, Object... hashKeys);
	
	/**
	 * 判断hash表中是否有该hashKey的值
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public boolean hasHashKey(String key, String hashKey);
	
	
	/**
	 * 根据key获取set中的所有值
	 * @param key
	 * @return
	 */
	public Set<Object> getSet(String key);
	
	/**
	 * 将数据放入set缓存
	 * @param key
	 * @param values
	 * @return 成功个数
	 */
	public long setSet(String key, Object... values);
	
	/**
	 * 将数据放入set缓存，并设置缓存时间
	 * @param key
	 * @param timeOut
	 * @param values
	 * @return
	 */
	public long setSet(String key, long timeOut, Object... values);
	
	/**
	 * 一直set中的缓存值 values
	 * @param key
	 * @param values
	 * @return
	 */
	public long deleteSet(String key, Object... values);
}
