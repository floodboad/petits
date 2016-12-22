package com.oshare.thirdparty.dao;

import com.oshare.thirdparty.entity.model.CardDonateLogModel;

/**
 * 卡片赠送记录信息
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/11/20 22:35
 */
public interface CardDonateLogDao {

	public void saveCardDonateLog(CardDonateLogModel model);

}