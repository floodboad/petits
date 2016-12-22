package com.oshare.thirdparty.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oshare.thirdparty.entity.model.CardExchangeLogModel;

/**
 * 卡片提现信息
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/11/20 22:35
 */
public interface CardExchangeLogDao {

	/**
	 * 新增用户提现日志.
	 * 
	 * @param logModel
	 *            the log model
	 */
	public void insertLog(CardExchangeLogModel logModel);

	/**
	 * 查询用户的提现日志.
	 * 
	 * @param userId
	 *            the user id
	 * @return the list
	 */
	public List<CardExchangeLogModel> queryExchangeLogs(@Param("userId") Long userId);
}