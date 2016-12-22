package com.oshare.thirdparty.entity.model;

import java.util.Date;

import com.oshare.thirdparty.entity.BaseEntity;

/**
 * 订单信息Model
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/12/21 20:31
 */
@SuppressWarnings("serial")
public class OrderInfoModel extends BaseEntity {

	/** 主键 */
	private Long id;

	/** 订单编号 */
	private String orderNo;

	/** payOrderNo */
	private String payOrderNo;

	/** 订单金额 */
	private Double orderAmount;

	/** 订单类型：1=支付宝订单；2=微信订单 */
	private Integer type;

	/** 订单来源：1=购买卡片；2=购买会员；3=提现积分 */
	private Integer origin;

	/** createdTime */
	private Date createdTime;

	/** updatedTime */
	private Date updatedTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrigin() {
		return origin;
	}

	public void setOrigin(Integer origin) {
		this.origin = origin;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

}