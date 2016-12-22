package com.oshare.thirdparty.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 卡片赠送信息记录
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/11/20 22:35
 */
@SuppressWarnings("serial")
public class CardDonateLogModel implements Serializable {

	/** 主键ID */
	private Long id;

	/** 卡号 */
	private String cardNo;

	/** 赠送者 */
	private Long donator;

	/** 赠送给的人 */
	private Long donater;

	/** 赠送时间 */
	private Date donatedTime;

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

	public Long getDonator() {
		return donator;
	}

	public void setDonator(Long donator) {
		this.donator = donator;
	}

	public Long getDonater() {
		return donater;
	}

	public void setDonater(Long donater) {
		this.donater = donater;
	}

	public Date getDonatedTime() {
		return donatedTime;
	}

	public void setDonatedTime(Date donatedTime) {
		this.donatedTime = donatedTime;
	}

}