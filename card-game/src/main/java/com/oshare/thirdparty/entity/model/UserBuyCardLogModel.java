package com.oshare.thirdparty.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户购买卡片日志
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/11/20 22:35
 */
public class UserBuyCardLogModel implements Serializable {

	private static final long serialVersionUID = 659977341258973925L;

	/** 主键ID */
	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

}