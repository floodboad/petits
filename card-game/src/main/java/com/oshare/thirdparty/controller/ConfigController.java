package com.oshare.thirdparty.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.oshare.thirdparty.common.exception.CacheException;
import com.oshare.thirdparty.common.http.ApiResponse;
import com.oshare.thirdparty.entity.bean.AppVersionBean;
import com.oshare.thirdparty.entity.bean.MemberLevelBean;
import com.oshare.thirdparty.service.MemberLevelInfoService;
import com.oshare.thirdparty.service.SysConfigService;

/**
 * 
 * 系统配置相关Controller
 * 
 * @author mengzhg
 * 
 */
@Controller
@RequestMapping("/config")
public class ConfigController {

	private Logger logger = LoggerFactory.getLogger(ConfigController.class);

	@Autowired
	private MemberLevelInfoService memberLevelService;

	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 会员等级信息.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/member-level", method = RequestMethod.POST)
	public ApiResponse<List<MemberLevelBean>> memberLevel(HttpServletRequest request, HttpServletResponse response) {
		logger.info("======Request info: uri={}.", request.getRequestURI());

		ApiResponse<List<MemberLevelBean>> result = new ApiResponse<List<MemberLevelBean>>();
		try {
			List<MemberLevelBean> list = memberLevelService.getMemberLevelInfos();
			result.setCode(1);
			result.setMessage("操作成功");
			result.setData(list);
		} catch (CacheException e) {
			logger.error("Failed to query member info.", e);
			result.setCode(0);
			result.setMessage("操作缓存失败，");
		} catch (Exception e) {
			logger.error("Failed to query member info.", e);
			result.setCode(0);
			result.setMessage("操作失败");
		}
		return result;
	}

	/**
	 * 版本更新.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/version-update", method = RequestMethod.POST)
	public ApiResponse<JSONObject> versionUpdate(HttpServletRequest request, HttpServletResponse response) {

		logger.info("======Request info: uri={}.", request.getRequestURI());
		ApiResponse<JSONObject> result = new ApiResponse<JSONObject>();

		try {
			AppVersionBean appVersion = sysConfigService.getAppVersion();
			JSONObject data = new JSONObject();
			data.put("bizCode", "1001");
			data.put("versionInfo", appVersion);

			result.setCode(1);
			result.setMessage("操作成功");
			result.setData(data);
			return result;
		} catch (CacheException e) {
			logger.error("Failed to get app version.", e);
			result.setCode(0);
			result.setMessage("操作缓存失败");
		} catch (Exception e) {
			logger.error("Failed to get app version.", e);
			result.setCode(0);
			result.setMessage("操作失败");
		}
		return result;
	}
}
