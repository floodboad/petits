package com.oshare.thirdparty.entity.bean;

import java.util.Date;

import com.oshare.thirdparty.entity.BaseEntity;

/**
 * 
 * 系统配置信息
 * 
 * @author mengzhg
 * 
 */
@SuppressWarnings("serial")
public class SysConfiBean extends BaseEntity {

	/** 主键ID */
	private Long id;

	/** 编码 */
	private String code;

	/** 标签 */
	private String label;

	/** 值 */
	private String value;

	/** 描述 */
	private String description;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
