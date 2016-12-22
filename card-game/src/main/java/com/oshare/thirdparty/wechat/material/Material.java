package com.oshare.thirdparty.wechat.material;

import java.io.Serializable;
import java.util.List;

/**
 * 微信素材.
 */
public class Material implements Serializable {

	private static final long serialVersionUID = 7063008334730687197L;

	/** 图文（news）. */
	public static final String TYPE_NEWS = "news";

	/** 图片（image）. */
	public static final String TYPE_IMAGES = "image";

	/** 语音 （voice）. */
	public static final String TYPE_VIDEO = "video";

	/** 视频（video）. */
	public static final String TYPE_VOICE = "voice";

	private String media_id;

	private String media_title;

	private List<MaterialItem> items;

	private String update_time;

	/**
	 * Gets the media_id.
	 * 
	 * @return the media_id
	 */
	public String getMedia_id() {
		return media_id;
	}

	/**
	 * Sets the media_id.
	 * 
	 * @param media_id
	 *            the new media_id
	 */
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	/**
	 * Gets the items.
	 * 
	 * @return the items
	 */
	public List<MaterialItem> getItems() {
		return items;
	}

	/**
	 * Sets the items.
	 * 
	 * @param items
	 *            the new items
	 */
	public void setItems(List<MaterialItem> items) {
		this.items = items;
	}

	public String getMedia_title() {
		if (items != null && items.size() > 0) {
			return items.get(0).getTitle();
		}
		return media_title;
	}

	public void setMedia_title(String media_title) {
		this.media_title = media_title;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

}
