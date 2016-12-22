package com.oshare.thirdparty.entity.bean;

import java.util.Date;

import com.oshare.thirdparty.entity.BaseEntity;
import com.oshare.thirdparty.entity.model.UserBuyCardLogModel;

/**
 * 购买卡片日志Bean.
 */
public class BuyCardLogBean extends BaseEntity {
	private static final long serialVersionUID = -5191724588686357409L;

	/** 卡片编号 */
	private String cardNo;

	/** 用户ID */
	private Long userId;

	/** 订单渠道 */
	private Integer channel;

	/** 订单编号 */
	private String orderNo;

	/** 购买金额 */
	private Double money;

	/** 购买时间 */
	private Date buyDate;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public void to(UserBuyCardLogModel model) {
		model.setBuyDate(this.getBuyDate());
		model.setCardNo(this.getCardNo());
		model.setChannel(this.getChannel());
		model.setMoney(this.getMoney());
		model.setOrderNo(this.getOrderNo());
		model.setUserId(this.getUserId());
	}
}
