package com.oshare.thirdparty.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.oshare.thirdparty.common.KeyFactory;
import com.oshare.thirdparty.common.exception.SystemException;
import com.oshare.thirdparty.dao.CardExchangeLogDao;
import com.oshare.thirdparty.dao.UserCardDao;
import com.oshare.thirdparty.dao.UserDao;
import com.oshare.thirdparty.entity.bean.AllCardsResBean;
import com.oshare.thirdparty.entity.bean.BuyCardLogBean;
import com.oshare.thirdparty.entity.bean.CardDonateLogBean;
import com.oshare.thirdparty.entity.bean.UserCardBean;
import com.oshare.thirdparty.entity.model.CardExchangeLogModel;
import com.oshare.thirdparty.entity.model.UserCardModel;
import com.oshare.thirdparty.entity.model.UserModel;
import com.oshare.thirdparty.utils.DateUtil;

/**
 * 用户卡片服务.
 */
@Service
public class UserCardService {

	private static Logger logger = LoggerFactory.getLogger(UserCardService.class);

	@Autowired
	private UserCardDao userCardDao;
	@Autowired
	private CardExchangeLogDao cardExchangeLogDao;
	@Autowired
	private UserBuyCardLogService buyCardLogService;
	@Autowired
	private CardDonateLogService cardDonateLogService;
	@Autowired
	private UserDao userDao;

	/**
	 * 获取我的卡片.
	 * 
	 * @param mobile
	 *            the mobile
	 * @return the mycards
	 */
	public List<UserCardBean> getMycards(Long userId) {
		List<UserCardModel> cardModels = userCardDao.getMycards(userId);

		List<UserCardBean> cardBeans = new ArrayList<UserCardBean>();
		for (UserCardModel model : cardModels) {
			UserCardBean bean = new UserCardBean();
			bean.from(model);
			cardBeans.add(bean);
		}
		return cardBeans;

	}

	/**
	 * 获取棋盘卡片.
	 * 
	 * @param xMin
	 *            the x min
	 * @param xMax
	 *            the x max
	 * @param yMin
	 *            the y min
	 * @param yMax
	 *            the y max
	 * @return the cards
	 */
	public List<AllCardsResBean> getCards(int xMin, int xMax, int yMin, int yMax) {
		List<AllCardsResBean> cardBeans = userCardDao.getCards(xMin, xMax, yMin, yMax);
		return cardBeans;
	}

	/**
	 * 搜索棋盘卡片.
	 * 
	 * @param xMin
	 *            the x min
	 * @param xMax
	 *            the x max
	 * @param yMin
	 *            the y min
	 * @param yMax
	 *            the y max
	 * @return the cards
	 */
	public List<AllCardsResBean> searchCards(String cardNo, String mobile, Integer score, Date beginTime, Date endTime,
			int index, int offset) {
		List<AllCardsResBean> cardBeans = userCardDao.searchCards(cardNo, mobile, score, beginTime, endTime, index,
				offset);
		return cardBeans;
	}

	/**
	 * 购买卡片.
	 * 
	 * @param cards
	 *            the cards
	 * @return the list
	 */
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public List<UserCardBean> buyCards(List<UserCardBean> cards, Long userId) {

		List<UserCardBean> result = new ArrayList<UserCardBean>();
		for (UserCardBean bean : cards) {
			// 保存卡片信息
			UserCardModel model = new UserCardModel();
			model.setUserId(userId);
			model.setCardNo(KeyFactory.cardNo());
			model.setValue(bean.getValue());
			model.setStatus(1);
			model.setXCoords(bean.getXCoords());
			model.setYCoords(bean.getYCoords());
			model.setCreateTime(DateUtil.now());
			model.setUpdateTime(DateUtil.now());
			userCardDao.addCard(model);

			// 保存购买日志
			BuyCardLogBean logBean = new BuyCardLogBean();
			logBean.setBuyDate(model.getCreateTime());
			logBean.setCardNo(model.getCardNo());
			logBean.setChannel(1); // 1=支付宝；2=微信
			logBean.setMoney(model.getValue() * 0.01);
			logBean.setOrderNo("11111111111111");
			logBean.setUserId(userId);
			buyCardLogService.saveLog(logBean);

			// 返回购买卡片信息
			bean.from(model);
			result.add(bean);
		}
		return result;
	}

	/**
	 * 卡片提现.
	 * 
	 * @param cards
	 *            the cards
	 */
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public void withdrawals(List<UserCardBean> cards, int channel) {
		for (UserCardBean userCardBean : cards) {
			// 0.校验卡片是否可以提现

			// 1.提现到微信

			// 2.更新数据库信息
			UserCardModel model = new UserCardModel();
			userCardBean.to(model);
			userCardDao.updateCardInfo(model);

			// 3.写日志
			CardExchangeLogModel logModel = new CardExchangeLogModel();
			logModel.setUserId(userCardBean.getUserId());
			logModel.setCardNo(userCardBean.getCardNo());
			logModel.setChannel(channel);
			logModel.setOrderNo("=====");
			logModel.setStatus(1);
			logModel.setCreateTime(DateUtil.now());
			logModel.setUpdateTime(DateUtil.now());

			cardExchangeLogDao.insertLog(logModel);
		}

	}

	/**
	 * 卡片赠送.
	 * 
	 * @param mobile
	 *            the mobile
	 * @param cardNos
	 *            the card nos
	 * @throws SystemException
	 */
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public void donateCard(String mobile, List<String> cardNos, Long donatorId) throws SystemException {
		logger.info("donateCard, mobile={},cardNos={},donatorId={}.", mobile, cardNos, donatorId);

		UserModel user = userDao.getUser(mobile);
		if (user == null) {
			logger.error("用户不存在");
			throw new SystemException("用户不存在");
		}
		if (user.getId() == donatorId) {
			logger.error("不能转赠给自己");
			throw new SystemException("不能转赠给自己");
		}

		int result = userCardDao.donateCard(user.getId(), donatorId, cardNos);
		if (result <= 0) {
			logger.error("转赠失败");
			throw new SystemException("转赠失败");
		}
		for (String cardNo : cardNos) {
			CardDonateLogBean bean = new CardDonateLogBean();
			bean.setCardNo(cardNo);
			bean.setDonatedTime(DateUtil.now());
			bean.setDonater(user.getId());
			bean.setDonator(donatorId);
			cardDonateLogService.saveDonateLog(bean);
		}

	}

	/**
	 * 给卡片赋值.
	 * 
	 * @param userCards
	 *            the new card value
	 * @throws SystemException
	 *             the system exception
	 */
	@Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
	public void setCardValue(List<UserCardBean> userCards) throws SystemException {
		for (UserCardBean userCardBean : userCards) {
			int result = this.userCardDao.setCardValue(userCardBean);
			if (result <= 0) {
				logger.warn("The card can't be found, card no:{}, set value:{}.", userCardBean.getCardNo(),
						userCardBean.getValue());
			}
		}
	}
}
