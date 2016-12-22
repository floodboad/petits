package com.oshare.thirdparty.service;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oshare.thirdparty.common.exception.CacheException;

/**
 * The Class CacheService.
 */
@Service
public class CacheService {

	private Logger logger = LoggerFactory.getLogger(CacheService.class);

	@Autowired
	private MemcachedClient cacheClient;

	public String get(String key) {
		try {
			String cache = cacheClient.get(key);
			logger.info("Get from cache, key=[{}], value={}.", key, cache);
			return cache;
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			logger.error("获取缓存失败, key=" + key, key, e);
		}
		return "";
	}

	public void set(String key, String value, int time) throws CacheException {
		try {
			logger.info("Set to cache, key={}, value={}, time={}.", key, value, time);
			cacheClient.set(key, time, value);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			throw new CacheException("设置缓存信息失败, key=" + key, e);
		}
	}

	public void set(String key, String value) throws CacheException {
		this.set(key, value, 0);
	}

	public boolean delete(String key) throws CacheException {
		try {
			logger.info("Delete from cache, key={}.", key);
			return cacheClient.delete(key);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			throw new CacheException("删除缓存失败, key=" + key, e);
		}
	}
}
