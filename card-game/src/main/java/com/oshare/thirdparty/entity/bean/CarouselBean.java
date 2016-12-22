package com.oshare.thirdparty.entity.bean;

import com.oshare.thirdparty.entity.BaseEntity;

/**
 * 轮播图信息.
 */
public class CarouselBean extends BaseEntity {
	private static final long serialVersionUID = 5572048668650605265L;

	private int sort;

	private String imageUrl;

	private String linkUrl;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

}
