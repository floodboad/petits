package com.oshare.thirdparty.entity.model;

import java.io.Serializable;

/**
 * 会员等级信息
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/11/20 22:35
 */
@SuppressWarnings("serial")
public class MemberLevelInfoModel implements Serializable {

	/** 主键ID */
	private Long id;

	/** 会员等级名称 */
	private String name;

	/** 描述 */
	private String description;

	/** 排序码 */
	private Integer sortNo;

	/** 等级需要的钱 */
	private Double money;

	/** 拥有的卡片数量 */
	private Integer ownCardNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getOwnCardNumber() {
		return ownCardNumber;
	}

	public void setOwnCardNumber(Integer ownCardNumber) {
		this.ownCardNumber = ownCardNumber;
	}
	
	

}