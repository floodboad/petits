package com.oshare.thirdparty.entity.bean;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.oshare.thirdparty.entity.BaseEntity;

/**
 * 查询棋盘所有卡片响应Bean
 * 
 * @author mengzhg
 * 
 */
public class AllCardsResBean extends BaseEntity {
	private static final long serialVersionUID = -3504291772982693853L;

	@JSONField(ordinal = 1)
	private String cardNo;
	@JSONField(ordinal = 2)
	private int value;
	@JSONField(ordinal = 3)
	private int xCoords;
	@JSONField(ordinal = 4)
	private int yCoords;
	@JSONField(ordinal = 5)
	private String username;
	@JSONField(ordinal = 6)
	private String mobile;
	@JSONField(ordinal = 7)
	private String avatar;
	@JSONField(format = "yyyy-MM-dd HH:mm:ss", ordinal = 8)
	private Date buyTime;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getxCoords() {
		return xCoords;
	}

	public void setxCoords(int xCoords) {
		this.xCoords = xCoords;
	}

	public int getyCoords() {
		return yCoords;
	}

	public void setyCoords(int yCoords) {
		this.yCoords = yCoords;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

}
