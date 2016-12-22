package com.oshare.thirdparty.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oshare.thirdparty.common.http.ApiResponse;
import com.oshare.thirdparty.service.CacheService;

/**
 * 后台管理系统的controller.
 */
@Controller
@RequestMapping("/cms")
public class CMSController {

	private static Logger logger = LoggerFactory.getLogger(CMSController.class);

	@Autowired
	CacheService cacheService;

	@RequestMapping(value = "/cache/{key}", method = RequestMethod.GET)
	@ResponseBody
	public ApiResponse<String> getCache(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("key") String key) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), key);

		String cacheValue = cacheService.get(key);

		ApiResponse<String> result = new ApiResponse<String>(1, "获取成功", cacheValue);
		return result;
	}

	@RequestMapping(value = "/cache/{key}", method = RequestMethod.POST)
	@ResponseBody
	public ApiResponse<Boolean> setCache(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("key") String key, @RequestBody String requestBody) {

		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<Boolean> result = new ApiResponse<Boolean>();
		try {
			JSONObject body = JSON.parseObject(requestBody);
			cacheService.set(key, body.getString("value"), body.getIntValue("time"));
			result.setCode(1);
			result.setMessage("操作成功");
			result.setData(true);
		} catch (Exception e) {
			result.setCode(0);
			result.setMessage("操作失败");
			result.setData(false);
		}
		return result;
	}

	/**
	 * 管理员登陆.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the model and view
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {

		return null;
	}

}
