package com.oshare.thirdparty.entity.bean;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.oshare.thirdparty.entity.BaseEntity;

/**
 * 通知
 * 
 * @author mengzhg
 */
public class NoticeBean extends BaseEntity {
	private static final long serialVersionUID = -1701997652724011311L;

	@JSONField(ordinal = 1)
	private String title;

	@JSONField(ordinal = 2)
	private String content;

	@JSONField(format = "yyyy-MM-dd HH:mm:ss", ordinal = 3)
	private Date time;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
