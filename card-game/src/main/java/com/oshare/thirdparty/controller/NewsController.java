package com.oshare.thirdparty.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oshare.thirdparty.common.exception.SystemException;
import com.oshare.thirdparty.common.http.ApiResponse;
import com.oshare.thirdparty.utils.StringUtil;
import com.oshare.thirdparty.wechat.material.Material;
import com.oshare.thirdparty.wechat.material.MaterialManager;

/**
 * 
 * 新闻广告咨询相关controller
 * 
 * @author mengzhg
 * 
 */
@Controller
@RequestMapping("/news")
public class NewsController {

	private Logger logger = LoggerFactory.getLogger(NewsController.class);

	/**
	 * 获取新闻.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param cursor
	 *            起始位置
	 * @param count
	 *            查询数量
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/news", method = RequestMethod.POST)
	public ApiResponse<List<Material>> news(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<List<Material>> result = new ApiResponse<List<Material>>();
		try {
			JSONObject body = JSON.parseObject(requestBody);
			StringUtil.verifyReqParam(body, "cursor", "count");

			int cursor = body.getIntValue("cursor");
			int count = body.getIntValue("count");

			MaterialManager materialManager = new MaterialManager();
			List<Material> news = materialManager.getMaterialList(Material.TYPE_NEWS, (cursor - 1), count);
			result.setData(news);
			result.setCode(1);
			result.setMessage("查询成功");
		} catch (SystemException e) {
			logger.error("Failed to get news.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to get news.", e);
			result.setCode(0);
			result.setMessage("查询新闻失败");
		}
		return result;
	}

}
