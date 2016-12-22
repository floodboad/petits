package com.oshare.thirdparty.entity.bean;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.oshare.thirdparty.entity.BaseEntity;

/**
 * App版本信息.
 * 
 * @author mengzhg
 */
public class AppVersionBean extends BaseEntity {

	private static final long serialVersionUID = 8274653040945598560L;

	/** 版本号 */
	@JSONField(ordinal = 1)
	private String version;

	/** 描述 */
	@JSONField(ordinal = 2)
	private String description;

	/** 下载地址. */
	@JSONField(ordinal = 3)
	private String url;

	/** 更新时间. */
	@JSONField(ordinal = 4, format = "yyyy-MM-dd HH:mm:ss")
	private Date updateDate;

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 * 
	 * @param url
	 *            the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
