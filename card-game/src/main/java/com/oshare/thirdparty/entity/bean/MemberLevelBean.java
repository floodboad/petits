package com.oshare.thirdparty.entity.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.oshare.thirdparty.entity.BaseEntity;
import com.oshare.thirdparty.entity.model.MemberLevelInfoModel;

/**
 * 会员等级说明
 */
public class MemberLevelBean extends BaseEntity {

	private static final long serialVersionUID = -2821031860728215625L;

	/** 主键ID */
	@JSONField(ordinal = 1)
	private Long id;

	/** 等级名称. */
	@JSONField(ordinal = 2)
	private String levelName;

	/** 描述 */
	@JSONField(ordinal = 3)
	private String description;

	/** 等级需要的钱 */
	@JSONField(ordinal = 4)
	private Double money;

	/** 排序码 */
	@JSONField(ordinal = 5)
	private Integer order;

	/** 等级所拥有的卡片数量. */
	@JSONField(ordinal = 6)
	private Integer ownCardNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getOwnCardNumber() {
		return ownCardNumber;
	}

	public void setOwnCardNumber(Integer ownCardNumber) {
		this.ownCardNumber = ownCardNumber;
	}

	public void from(MemberLevelInfoModel model) {
		this.setId(model.getId());
		this.setDescription(model.getDescription());
		this.setLevelName(model.getName());
		this.setMoney(model.getMoney());
		this.setOrder(model.getSortNo());
		this.setOwnCardNumber(model.getOwnCardNumber());
	}

}
