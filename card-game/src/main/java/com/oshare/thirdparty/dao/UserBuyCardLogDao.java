package com.oshare.thirdparty.dao;

import com.oshare.thirdparty.entity.model.UserBuyCardLogModel;

/**
 * 用户购买卡片记录
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/11/20 22:35
 */
public interface UserBuyCardLogDao {

	/**
	 * 保存购买卡片日志
	 * 
	 * @param model
	 */
	public void saveUserBuyCardLog(UserBuyCardLogModel model);

}