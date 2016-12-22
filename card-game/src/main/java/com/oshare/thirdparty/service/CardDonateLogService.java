package com.oshare.thirdparty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oshare.thirdparty.dao.CardDonateLogDao;
import com.oshare.thirdparty.entity.bean.CardDonateLogBean;
import com.oshare.thirdparty.entity.model.CardDonateLogModel;

@Service
public class CardDonateLogService {

	@Autowired
	private CardDonateLogDao cardDonateLogDao;

	public void saveDonateLog(CardDonateLogBean bean) {
		CardDonateLogModel model = new CardDonateLogModel();
		bean.to(model);
		cardDonateLogDao.saveCardDonateLog(model);
	}

}
