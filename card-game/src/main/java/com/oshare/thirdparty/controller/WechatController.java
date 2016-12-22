package com.oshare.thirdparty.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sword.lang.HttpUtils;

import com.alibaba.fastjson.JSONObject;
import com.oshare.thirdparty.utils.WechatSignUtil;
import com.oshare.thirdparty.wechat.WeChat;

/**
 * 微信公众号需要用到的controller
 * 
 * @author mengzhg
 * 
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {

	private final static Logger logger = LoggerFactory.getLogger(WechatController.class);

	/** 微信APP ID. */
	@Value("#{wechat4j['wechat.appid']}")
	private String appid;

	/** The app secret. */
	@Value("#{wechat4j['wechat.appsecret']}")
	private String appSecret;

	/** The token. */
	@Value("#{wechat4j['wechat.token']}")
	private String token;

	@RequestMapping(value = "/core", method = RequestMethod.GET)
	@ResponseBody
	public void wechatGetRequest(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		logger.info("Start to invoke the wechatGetRequest method.");
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		try {
			PrintWriter out = response.getWriter();
			if (WechatSignUtil.checkSignature(token, signature, timestamp, nonce)) {
				out.print(echostr);
			}
			out.close();
		} catch (IOException e) {
			logger.error("Failed to check signature.", e);
		}
		logger.info("Finished invoke the wechatGetRequest method.");
	}

	@RequestMapping(value = "/core", method = RequestMethod.POST)
	public void wechatPostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		logger.info("Start to invoke the wechatPostRequest method.");
		WeChat wc = new WeChat(request);
		String result = wc.execute();
		logger.info("Finished invoke the wechatPostRequest method.");

		response.getOutputStream().write(result.getBytes());
	}

	/**
	 * 通过授权获取openID
	 * 
	 * @param code
	 *            the code
	 * @return the open id
	 */
	@SuppressWarnings("unused")
	private String getOpenId(String code) {
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		String realURL = url.replace("APPID", appid).replace("SECRET", appSecret).replace("CODE", code);
		String result = HttpUtils.get(realURL);
		JSONObject data = JSONObject.parseObject(result);
		String openID = String.valueOf(data.get("openid"));
		return openID;
	}

}
