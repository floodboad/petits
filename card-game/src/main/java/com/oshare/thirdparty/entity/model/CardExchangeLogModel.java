package com.oshare.thirdparty.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 卡片提现记录
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/11/20 22:35
 */
@SuppressWarnings("serial")
public class CardExchangeLogModel implements Serializable {

	/** 主键ID */
	private Long id;

	/** 用户ID */
	private Long userId;

	/** 卡号 */
	private String cardNo;

	/** 状态 */
	private Integer status;

	/** 订单渠道 */
	private Integer channel;

	/** 订单编号 */
	private String orderNo;

	/** 创建时间 */
	private Date createTime;

	/** 修改时间 */
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}