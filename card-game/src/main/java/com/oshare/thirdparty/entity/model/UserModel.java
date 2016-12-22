package com.oshare.thirdparty.entity.model;

import java.util.Date;

import com.oshare.thirdparty.entity.BaseEntity;

/**
 * 用户信息.
 */
public class UserModel extends BaseEntity {

	private static final long serialVersionUID = 6645901802408230824L;

	/** The id. */
	private Long id;

	/** 手机号码. */
	private String mobile;

	/** 昵称. */
	private String username;

	/** 密码. */
	private String password;

	/** 个人邀请码. */
	private String invitationCode;

	/** 上线的邀请码. */
	private String upLineMobile;

	/** 头像. */
	private String avatar;

	/** 会员状态. */
	private Integer status;

	/** The role. */
	private Integer role;

	/** 0=女/1=男. */
	private Integer sex;

	/** 0=铁牌会员；1=铜牌会员；2=银牌会员；3=金牌会员. */
	private Integer accountLevel;

	private Date createTime;

	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getUpLineMobile() {
		return upLineMobile;
	}

	public void setUpLineMobile(String upLineMobile) {
		this.upLineMobile = upLineMobile;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

}
