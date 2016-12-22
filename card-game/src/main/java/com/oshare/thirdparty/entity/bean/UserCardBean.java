package com.oshare.thirdparty.entity.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.oshare.thirdparty.entity.BaseEntity;
import com.oshare.thirdparty.entity.model.UserCardModel;
import com.oshare.thirdparty.utils.DateUtil;

/**
 * 卡片信息.
 */

public class UserCardBean extends BaseEntity {
	private static final long serialVersionUID = -6275193639294307484L;

	/** 卡号 */
	@JSONField(ordinal = 1)
	private String cardNo;

	/** 拥有用户ID */
	@JSONField(ordinal = 2)
	private Long userId;

	/** X坐标 */
	@JSONField(ordinal = 3)
	private Integer xCoords;

	/** Y坐标 */
	@JSONField(ordinal = 4)
	private Integer yCoords;

	/** 积分值 */
	@JSONField(ordinal = 5)
	private Integer value;

	/** 卡状态 */
	@JSONField(serialize = false)
	private Integer status;

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

	public Integer getXCoords() {
		return xCoords;
	}

	public void setXCoords(Integer xCoords) {
		this.xCoords = xCoords;
	}

	public Integer getYCoords() {
		return yCoords;
	}

	public void setYCoords(Integer yCoords) {
		this.yCoords = yCoords;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void from(UserCardModel model) {
		this.setCardNo(model.getCardNo());
		this.setStatus(model.getStatus());
		this.setUserId(model.getUserId());
		this.setValue(model.getValue());
		this.setXCoords(model.getXCoords());
		this.setYCoords(model.getYCoords());
	}

	public void to(UserCardModel model) {
		model.setCardNo(this.getCardNo());
		model.setCreateTime(DateUtil.now());
		model.setStatus(this.getStatus());
		model.setUpdateTime(DateUtil.now());
		model.setUserId(this.getUserId());
		model.setValue(this.getValue());
		model.setXCoords(this.getXCoords());
		model.setYCoords(this.getYCoords());

	}

}
