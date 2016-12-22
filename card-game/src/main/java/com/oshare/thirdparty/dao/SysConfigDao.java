package com.oshare.thirdparty.dao;

import com.oshare.thirdparty.entity.model.SysConfigModel;

/**
 * 系统配置信息
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/11/20 22:35
 */
public interface SysConfigDao {

	/**
	 * 获取最新的版本信息
	 * 
	 * @return the app version
	 */
	public SysConfigModel getAppVersion();
}