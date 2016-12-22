package com.oshare.thirdparty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oshare.thirdparty.common.constant.CacheKeyConstant;
import com.oshare.thirdparty.common.exception.CacheException;
import com.oshare.thirdparty.dao.SysConfigDao;
import com.oshare.thirdparty.entity.bean.AppVersionBean;
import com.oshare.thirdparty.entity.model.SysConfigModel;
import com.oshare.thirdparty.utils.StringUtil;

/**
 * 配置信息Service
 * 
 * @author mengzhg
 */
@Service
public class SysConfigService {

	private static Logger logger = LoggerFactory.getLogger(SysConfigService.class);

	@Autowired
	private CacheService cacheService;
	@Autowired
	private SysConfigDao sysConfigDao;

	/**
	 * 获取APP版本信息
	 * 
	 * @return
	 * @throws CacheException
	 */
	public AppVersionBean getAppVersion() throws CacheException {
		String appVersion = cacheService.get(CacheKeyConstant.KEY_APP_VERSION);
		if (StringUtil.isNotBlank(appVersion)) {
			logger.info("Get app version from cache, cache value:{}.", appVersion);
			return JSONObject.parseObject(appVersion, AppVersionBean.class);
		}
		SysConfigModel model = sysConfigDao.getAppVersion();
		cacheService.set(CacheKeyConstant.KEY_APP_VERSION, model.getValue());
		AppVersionBean bean = JSON.parseObject(model.getValue(), AppVersionBean.class);
		return bean;
	}

}
