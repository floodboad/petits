package com.oshare.thirdparty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oshare.thirdparty.dao.UserBuyCardLogDao;
import com.oshare.thirdparty.entity.bean.BuyCardLogBean;
import com.oshare.thirdparty.entity.model.UserBuyCardLogModel;

/**
 * 
 * 用户购买卡片日志服务
 * 
 * @author mengzhg
 * 
 */
@Service
public class UserBuyCardLogService {

	@Autowired
	UserBuyCardLogDao dao;

	public void saveLog(BuyCardLogBean bean) {
		UserBuyCardLogModel model = new UserBuyCardLogModel();
		bean.to(model);
		dao.saveUserBuyCardLog(model);
	}

}
