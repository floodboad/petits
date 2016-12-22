package com.oshare.thirdparty.entity.bean;

import com.oshare.thirdparty.entity.BaseEntity;

/**
 * 搜索用户的信息响应Bean
 * 
 * @author mengzhg
 */
public class SearchUserResBean extends BaseEntity {
	private static final long serialVersionUID = -4283238811891489511L;

	/** 手机号码. */
	private String mobile;

	/** 昵称. */
	private String username;

	/** 头像. */
	private String avatar;

	/** 0=女/1=男. */
	private Integer sex;

	/** 0=铁牌会员；1=铜牌会员；2=银牌会员；3=金牌会员. */
	private Integer accountLevel;

	/** 用户剩余可拥有的卡片数量. */
	private Integer ownCards;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAccountLevel() {
		return accountLevel;
	}

	public void setAccountLevel(Integer accountLevel) {
		this.accountLevel = accountLevel;
	}

	public Integer getOwnCards() {
		return ownCards;
	}

	public void setOwnCards(Integer ownCards) {
		this.ownCards = ownCards;
	}

}
