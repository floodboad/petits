package com.oshare.thirdparty.entity.bean;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.oshare.thirdparty.entity.BaseEntity;
import com.oshare.thirdparty.entity.model.UserModel;

/**
 * 用户基本信息Bean.
 * 
 * @author mengzhg
 */
public class UserBean extends BaseEntity {

	private static final long serialVersionUID = 1172982175690470465L;

	/** The id. */
	@JSONField(ordinal = 1)
	private Long id;

	/** 手机号码. */
	@JSONField(ordinal = 2)
	private String mobile;

	/** 昵称. */
	@JSONField(ordinal = 3)
	private String username;

	/** 密码. */
	@JSONField(ordinal = 4, serialize = false)
	private String password;

	/** 个人邀请码. */
	@JSONField(ordinal = 5)
	private String invitationCode;

	/** 上线邀请码. */
	@JSONField(ordinal = 6, serialize = false)
	private String upLineMobile;

	/** 头像. */
	@JSONField(ordinal = 7)
	private String avatar;

	/** 会员状态. */
	@JSONField(ordinal = 8)
	private Integer status;

	@JSONField(ordinal = 9)
	private Integer role;

	/** 0=女/1=男. */
	@JSONField(ordinal = 9)
	private Integer sex;

	/** 0=铁牌会员；1=铜牌会员；2=银牌会员；3=金牌会员. */
	@JSONField(ordinal = 10)
	private Integer accountLevel;

	@JSONField(ordinal = 11, format = "yyyy-MM-dd HH:mm:ss", serialize = false)
	private Date createTime;

	@JSONField(ordinal = 12, format = "yyyy-MM-dd HH:mm:ss", serialize = false)
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

	public void from(UserModel model) {
		if (model == null) {
			return;
		}
		this.setAccountLevel(model.getAccountLevel());
		this.setAvatar(model.getAvatar());
		this.setCreateTime(model.getCreateTime());
		this.setId(model.getId());
		this.setInvitationCode(model.getInvitationCode());
		this.setUpLineMobile(model.getUpLineMobile());
		this.setMobile(model.getMobile());
		this.setRole(model.getRole());
		this.setPassword(model.getPassword());
		this.setSex(model.getSex());
		this.setStatus(model.getStatus());
		this.setUpdateTime(model.getUpdateTime());
		this.setUsername(model.getUsername());
	}

	public void to(UserModel model) {
		model.setId(this.getId());
		model.setAccountLevel(this.getAccountLevel());
		model.setAvatar(this.getAvatar());
		model.setCreateTime(this.getCreateTime());
		model.setInvitationCode(this.getInvitationCode());
		model.setUpLineMobile(this.getUpLineMobile());
		model.setMobile(this.getMobile());
		model.setRole(this.getRole());
		model.setPassword(this.getPassword());
		model.setSex(this.getSex());
		model.setStatus(this.getStatus());
		model.setUpdateTime(this.getUpdateTime());
		model.setUsername(this.getUsername());
	}
}
