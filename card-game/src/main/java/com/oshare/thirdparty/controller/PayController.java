package com.oshare.thirdparty.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.oshare.thirdparty.common.http.ApiResponse;
import com.oshare.thirdparty.service.tpay.TenpayHandler;

/**
 * 支付相关的
 * 
 * @author mengzhg
 * 
 */
@Controller
@RequestMapping("/pay")
public class PayController {

	/**
	 * 支付宝统一下单.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param requestBody
	 *            the request body
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/alipay/unifiedorder", method = RequestMethod.POST)
	public ApiResponse<JSONObject> alipayUnifiedOrder(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		ApiResponse<JSONObject> result = new ApiResponse<JSONObject>();

		return result;
	}

	/**
	 * 阿里支付回调函数.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param requestBody
	 *            the request body
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/alipay/notify", method = RequestMethod.GET)
	public ApiResponse<JSONObject> alipayNotify(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		ApiResponse<JSONObject> result = new ApiResponse<JSONObject>();

		return result;
	}

	/**
	 * 微信支付统一下单.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param requestBody
	 *            the request body
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/wechat/unifiedorder", method = RequestMethod.POST)
	public ApiResponse<JSONObject> tenpayUnifiedOrder(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		ApiResponse<JSONObject> result = new ApiResponse<JSONObject>();

		TenpayHandler handler = new TenpayHandler();
		handler.tenpayUnifiedOrder();

		return result;
	}

	/**
	 * 微信支付回调函数
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param requestBody
	 *            the request body
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/wechat/notify", method = RequestMethod.GET)
	public ApiResponse<JSONObject> tenpayNotify(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		ApiResponse<JSONObject> result = new ApiResponse<JSONObject>();

		return result;
	}

}
