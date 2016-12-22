package com.oshare.thirdparty.wechat.material;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sword.lang.HttpUtils;
import org.sword.wechat4j.exception.WeChatException;
import org.sword.wechat4j.token.TokenProxy;
import org.sword.wechat4j.util.WeChatUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oshare.thirdparty.utils.DateUtil;

/**
 * 微信素材操作类
 * 
 * @author Heaunter Meng
 * 
 */
public class MaterialManager {

	final static Logger logger = LoggerFactory.getLogger(MaterialManager.class);

	/** 获取永久素材 URL */
	private final String GET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=";

	/** 获取永久素材列表 */
	private final String GET_MATERIAL_LIST = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=";

	private String accessToken;

	public MaterialManager() {
		this.accessToken = TokenProxy.accessToken();
	}

	/**
	 * 获取单个素材.
	 * 
	 * @param meidaId
	 *            the meida id
	 * @return the material
	 */
	public List<Material> getMaterial(String meidaId) {
		logger.info("获取永久素材");

		String resultStr = HttpUtils.post(GET_MATERIAL + this.accessToken, JSON.toJSONString(meidaId));
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			e.printStackTrace();
			return null;
		}
		JSONObject jsonObject = JSONObject.parseObject(resultStr);
		JSONArray array = jsonObject.getJSONArray("news_item");
		List<Material> result = new ArrayList<Material>();
		for (int i = 0; i < array.size(); i++) {
			Material material = array.getObject(i, Material.class);
			result.add(material);
		}
		return result;
	}

	/**
	 * 获取永久素材列表.
	 * 
	 * @param type
	 *            the type
	 * @param offset
	 *            the offset
	 * @param count
	 *            the count
	 * @return the material list
	 */
	public List<Material> getMaterialList(String type, int offset, int count) {
		logger.info("获取永久素材列表");

		GetMaterialRequest request = new GetMaterialRequest();
		request.setCount(count);
		request.setOffset(offset);
		request.setType(type);

		String resultStr = HttpUtils.post(GET_MATERIAL_LIST + this.accessToken, JSON.toJSONString(request));
		try {
			WeChatUtil.isSuccess(resultStr);
		} catch (WeChatException e) {
			e.printStackTrace();
			return null;
		}

		JSONObject jsonObject = JSONObject.parseObject(resultStr);
		JSONArray array = jsonObject.getJSONArray("item");
		List<Material> result = new ArrayList<Material>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject materialJson = array.getJSONObject(i);

			Material material = new Material();
			material.setMedia_id(materialJson.getString("media_id"));

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(materialJson.getLongValue("update_time"));
			material.setUpdate_time(DateUtil.formatDate(cal.getTime()));

			if (array.getJSONObject(i).containsKey("content")) {
				JSONArray array2 = array.getJSONObject(i).getJSONObject("content").getJSONArray("news_item");
				List<MaterialItem> items = new ArrayList<MaterialItem>();
				for (int j = 0; j < array2.size(); j++) {
					MaterialItem item = array2.getObject(j, MaterialItem.class);
					items.add(item);
				}
				material.setItems(items);
			}
			result.add(material);
		}
		return result;

	}
}

class GetMaterialRequest {
	private String type;
	private int offset;
	private int count;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
