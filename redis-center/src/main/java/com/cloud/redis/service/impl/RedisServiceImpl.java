package com.cloud.redis.service.impl;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.cloud.model.redis.service.RedisService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(version = "2.0.0", interfaceClass = RedisService.class)
@Component
public class RedisServiceImpl implements RedisService{
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 指定缓存失效时间
	 * @param key
	 * @param time 单位 s
	 * @return
	 */
	public boolean expire(String key, long time) {
		try {
			if(time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			log.error("redis指定失效时间出错！", e);
			return false;
		}
	}
	
	/**
	 * 
	 * 根据key获取过期时间 单位 S
	 * @param key
	 * @return
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}
	
	/**
	 * 
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			log.error("redis判断key是否存在出错！", e);
			return false;
		}
	}
	
	/**
	 * 删除缓存
	 * @param key
	 */
	@SuppressWarnings("unchecked")
	public void delete(String... key) {
		if(null != key && key.length > 0) {
			if(key.length == 1) {
				redisTemplate.delete(key[0]);
			}else {
				redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}
	
	/**
	 * 普通缓存获取值
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		return StringUtils.isBlank(key) ? null : redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 普通缓存放入
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			log.error("redis缓存值出错！", e);
			return false;
		}
	}
	
	/**
	 * 普通缓存放入并设置时间  单位 S
	 * @param key
	 * @param value
	 * @param timeOut
	 * @return
	 */
	public boolean set(String key, Object value, long timeOut) {
		try {
			if(timeOut > 0) {
				redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.SECONDS);
			}else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			log.error("redis缓存值出错！", e);
			return false;
		}
	}
	
	
	/**
	 * HashGet
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object getHash(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}
	
	/**
	 * 获取key的所有hash值
	 * @param key
	 * @return
	 */
	public Map<Object, Object> getAllHash(String key) {
		return redisTemplate.opsForHash().entries(key);
	}
	
	/**
	 * redis缓存hashMap
	 * @param key
	 * @param hashMap
	 * @return
	 */
	public boolean setHash(String key, Map<String, Object> hashMap) {
		try {
			redisTemplate.opsForHash().putAll(key, hashMap);
			return true;
		} catch (Exception e) {
			log.error("redis缓存hash出错！", e);
			return false;
		}
	}
	
	/**
	 * redis缓存hash,并设置超时时间 单位 S
	 * @param key
	 * @param hashMap
	 * @param timeOut
	 * @return
	 */
	public boolean setHash(String key, Map<String, Object> hashMap, long timeOut) {
		try {
			redisTemplate.opsForHash().putAll(key, hashMap);
			if(timeOut > 0) {
				expire(key, timeOut);
			}
			return true;
		} catch (Exception e) {
			log.error("redis缓存hash出错！", e);
			return false;
		}
	}
	
	/**
	 * redis缓存二级值，并设置超时时间 单位S
	 * @param key
	 * @param hashKey
	 * @param value
	 * @param timeOut
	 * @return
	 */
	public boolean setHash(String key, String hashKey, Object value, long timeOut) {
		try {
			redisTemplate.opsForHash().put(key, hashKey, value);
			if(timeOut > 0) {
				expire(key, timeOut);
			}
			return true;
		} catch (Exception e) {
			log.error("redis缓存hash出错！", e);
			return false;
		}
	}
	
	/**
	 * 删除hash表中的值
	 * @param key
	 * @param hashKeys  可以使多个 不能为null
	 */
	public void deleteHash(String key, Object... hashKeys) {
		redisTemplate.opsForHash().delete(key, hashKeys);
	}
	
	/**
	 * 判断hash表中是否有该hashKey的值
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public boolean hasHashKey(String key, String hashKey) {
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}
	
	
	/**
	 * 根据key获取set中的所有值
	 * @param key
	 * @return
	 */
	public Set<Object> getSet(String key){
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			log.error("redis获取set出错！", e);
			return null;
		}
	}
	
	/**
	 * 将数据放入set缓存
	 * @param key
	 * @param values
	 * @return 成功个数
	 */
	public long setSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			log.error("redis缓存set出错！", e);
			return 0;
		}
	}
	
	/**
	 * 将数据放入set缓存，并设置缓存时间
	 * @param key
	 * @param timeOut
	 * @param values
	 * @return
	 */
	public long setSet(String key, long timeOut, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if(timeOut > 0) {
				expire(key, timeOut);
			}
			return count;
		} catch (Exception e) {
			log.error("redis缓存set出错！", e);
			return 0;
		}
	}
	
	/**
	 * 一直set中的缓存值 values
	 * @param key
	 * @param values
	 * @return
	 */
	public long deleteSet(String key, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			log.error("redis移除set中的缓存值出错！", e);
			return 0;
		}
	}

}
