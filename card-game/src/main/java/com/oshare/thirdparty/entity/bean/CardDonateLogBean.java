package com.oshare.thirdparty.entity.bean;

import java.util.Date;

import com.oshare.thirdparty.entity.BaseEntity;
import com.oshare.thirdparty.entity.model.CardDonateLogModel;

/**
 * 赠送卡片日志.
 */
public class CardDonateLogBean extends BaseEntity {

	private static final long serialVersionUID = 8048597335284104074L;

	/** 卡号 */
	private String cardNo;

	/** 赠送者 */
	private Long donator;

	/** 赠送给的人 */
	private Long donater;

	/** 赠送时间 */
	private Date donatedTime;

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

	public void to(CardDonateLogModel model) {
		model.setCardNo(this.getCardNo());
		model.setDonatedTime(this.getDonatedTime());
		model.setDonater(this.getDonater());
		model.setDonator(this.getDonator());
	}

}
