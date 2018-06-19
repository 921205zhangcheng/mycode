package com.xh.sdk.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class memcached {

	private static Log log = LogFactory.getLog(memcached.class);
	private boolean isOpen = true; // Memcached 开关
	private int expires; // 默认过期时间
	@Autowired
	private MemcachedClient mc;

	/*
	 * add 仅当存储空间中不存在键相同的数据时才保存
	 */
	public <T> void addWithNoReply(String key, T value) {
		this.addWithNoReply(key, expires, value);
	}

	/**
	 * 设置键值对
	 * 
	 * @param key
	 *            key
	 * @param expires
	 *            单位:秒，0 表示永不过期
	 * @param value
	 *            必须是一个可序列化的对象, 可以是容器类型如:List，但容器里面保存的对象必须是可序列化的
	 */
	public <T> void addWithNoReply(String key, int expires, T value) {
		System.out.println("this is run");
		if (key == null)
			return;
		try {
			if (isOpen && mc != null) {
				mc.addWithNoReply(key, expires, value);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/*
	 * set 无论何时都保存
	 */
	public <T> void set(String key, T value) {
		this.set(key, expires, value);
	}

	/**
	 * 设置键值对
	 * 
	 * @param key
	 *            key
	 * @param expires
	 *            单位:秒，0 表示永不过期
	 * @param value
	 *            必须是一个可序列化的对象, 可以是容器类型如:List，但容器里面保存的对象必须是可序列化的
	 */
	public <T> void set(String key, int expires, T value) {
		if (key == null)
			return;
		try {
			if (isOpen && mc != null) {
				mc.set(key, expires, value);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据key获得值
	 * 
	 * @param key
	 *            key
	 * @return value
	 */
	public <T> T get(String key) {
		try {
			if (key != null && isOpen && mc != null) {
				return (T) mc.get(key);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 给每个原始key加上前缀后，再查。
	 * 
	 * @param keys
	 *            原始key
	 * @param memcachedKeyPrefix
	 *            前缀
	 * @return
	 */
	public <T> Map<String, T> getMulti(Collection keys,
			String memcachedKeyPrefix) {

		if (keys == null)
			return null;
		if ("".equals(memcachedKeyPrefix)) {
			return this.get(keys);
		}
		List<String> strList = new ArrayList<String>();
		for (Object key : keys) {
			strList.add(memcachedKeyPrefix + String.valueOf(key));
		}
		return this.get(strList);
	}

	/**
	 * 重载方法
	 * 
	 * @param keys
	 * @param <T>
	 * @return
	 */
	public <T> Map<String, T> getMulti(Collection keys) {
		return getMulti(keys, "");
	}

	/**
	 * 批量获取
	 * 
	 * @param keys
	 *            keys
	 * @return valueMap
	 */
	private <T> Map<String, T> get(Collection<String> keys) {
		Map<String, T> map = null;
		try {
			if (keys != null && isOpen && mc != null) {
				map = mc.get(keys);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 按key删除
	 * 
	 * @param keys
	 *            keys
	 * @return valueMap
	 */
	public Boolean delete(String key) {
		try {
			return mc.delete(key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();

		}
		return false;
	}

	// 获取所有服务器上所有的缓存数据

//	 public List<MemcachedClient> getKeys() throws
//	 UnsupportedEncodingException{
//	 mc.get(keyCollections);
//	
//	 }
	

	public void setIsOpen(boolean open) {
		isOpen = open;
	}

	public void setExpires(int expires) {
		this.expires = expires;
	}

	public void setMemcachedClient(MemcachedClient mc) {
		this.mc = mc;
	}

}