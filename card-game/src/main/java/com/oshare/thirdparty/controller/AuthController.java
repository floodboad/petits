package com.oshare.thirdparty.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import team.oshare.tlogin.wechat.WxLoginHandler;
import team.oshare.tlogin.wechat.bean.GetAccessTokenResBean;
import team.oshare.tlogin.wechat.bean.WxUserInfoBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oshare.thirdparty.common.exception.SystemException;
import com.oshare.thirdparty.common.http.ApiResponse;
import com.oshare.thirdparty.entity.bean.UserBean;
import com.oshare.thirdparty.service.UserService;
import com.oshare.thirdparty.utils.StringUtil;

/**
 * 微信授权登陆.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

	private Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Value("#{openplatform['wexin.openplatform.appid']}")
	private String appId;

	@Value("#{openplatform['weixin.openplatform.appsecret']}")
	private String secret;

	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ApiResponse<UserBean> loginWechat(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		ApiResponse<UserBean> result = new ApiResponse<UserBean>();
		try {
			JSONObject body = JSON.parseObject(requestBody);
			StringUtil.verifyReqParam(body, "code", "mobile");

			WxLoginHandler handler = new WxLoginHandler();
			GetAccessTokenResBean tokenBean = handler.getAccessToken(appId, secret, body.getString("code"));

			WxUserInfoBean wxUserInfo = handler.getWxUserInfo(tokenBean.getAccess_token(), tokenBean.getOpenid());
			UserBean user = new UserBean();
			user.setRole(1);

			user.setUsername(wxUserInfo.getClass().toString());
			// TODO
			userService.signUp(user);

		} catch (SystemException e) {
			logger.error("微信授权登陆失败", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("微信授权登陆失败", e);
			result.setCode(0);
			result.setMessage("微信登陆失败");
		}
		return result;

	}

}
