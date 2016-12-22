package com.oshare.thirdparty.entity.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户卡片
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/11/20 22:35
 */
@SuppressWarnings("serial")
public class UserCardModel implements Serializable {

	/** 主键ID */
	private Long id;

	/** 卡号 */
	private String cardNo;

	/** 拥有用户ID */
	private Long userId;

	/** X坐标 */
	private Integer xCoords;

	/** Y坐标 */
	private Integer yCoords;

	/** 积分值 */
	private Integer value;

	/** 卡状态 */
	private Integer status;

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