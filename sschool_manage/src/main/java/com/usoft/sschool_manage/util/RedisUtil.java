package com.usoft.sschool_manage.util;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


import com.usoft.smartschool.util.MyResult;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import org.springframework.stereotype.Service;

/**
 *@file RedisUtil.java
 *@date 2018年7月25日
 *@author jijh
 */

@Service
@SuppressWarnings("all")
public class RedisUtil {
	private static final Long expireTime = 600000L;
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 存入缓存
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 读取缓存
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		try {
			
			ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
			Object obj = operations.get(key);
			return obj;
		}catch(Exception e) {
			e.printStackTrace();
			return MyResult.myException(e);
		}
	}
	/**
	 * 写入缓存设置时效
	 * @param key 键名
	 * @param value 值
	 * @param expireTime 过期时间(分钟)
	 * @return
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
			result = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;	
	}
	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public boolean exist(String key) {
		return redisTemplate.hasKey(key);
	}
	/**
	 * 删除key
	 * @param key
	 * @return
	 */
	public Integer remove(String key) {
		Integer num = 0;
		if(exist(key)) {
			redisTemplate.delete(key);
			num=1;
		}
		return num;
	}
	/**
	 * 批量删除key
	 * @param keys
	 * @return
	 */
	public Integer removes(String... keys) {
		Integer num = 0;
		for(String key : keys) {
			num+=remove(key);
		}
		return num;
	}
	/**
	 * 列表添加
	 * @param key
	 * @param obj
	 * @return
	 */
	public boolean lpush(String key, Object obj) {
		boolean result = false;
		try {
			ListOperations<String,Object> list = redisTemplate.opsForList();
			list.rightPush(key, obj);
			result =true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Object> lRange(String key, Long l1, Long l2){
		ListOperations<String,Object> list = redisTemplate.opsForList();
		return list.range(key, l1, l2);
	}
	public boolean add(String key,Object obj) {
		boolean result = false;
		try {
			SetOperations<String,Object> set = redisTemplate.opsForSet();
			set.add(key, obj);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Set<Object> setMemebers(String key){
		SetOperations<String,Object> set = redisTemplate.opsForSet();
		return set.members(key);
	}
}











