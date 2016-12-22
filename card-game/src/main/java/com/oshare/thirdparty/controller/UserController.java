package com.oshare.thirdparty.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oshare.thirdparty.common.KeyFactory;
import com.oshare.thirdparty.common.constant.CommonConstant;
import com.oshare.thirdparty.common.exception.AccessTokenException;
import com.oshare.thirdparty.common.exception.SystemException;
import com.oshare.thirdparty.common.http.ApiResponse;
import com.oshare.thirdparty.entity.bean.SearchUserResBean;
import com.oshare.thirdparty.entity.bean.UserBean;
import com.oshare.thirdparty.service.CacheService;
import com.oshare.thirdparty.service.UserService;
import com.oshare.thirdparty.utils.StringUtil;

/**
 * 
 * 用户相关controller
 * 
 * @author mengzhg
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CacheService cacheService;

	private int ACCESS_TOKEN_EXPIRE_TIME = CommonConstant.TIME_MONTH;

	/**
	 * 登陆
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ApiResponse<JSONObject> login(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<JSONObject> result = new ApiResponse<JSONObject>();
		try {
			JSONObject body = JSONObject.parseObject(requestBody);

			StringUtil.verifyReqParam(body, "mobile", "password");

			String mobile = body.getString("mobile");
			String password = body.getString("password");

			UserBean loginUserBean = new UserBean();
			loginUserBean.setMobile(mobile);
			loginUserBean.setPassword(password);

			UserBean retUserBean = userService.login(loginUserBean);

			String cachedAccessToken = cacheService.get(mobile);
			if (StringUtil.isNotBlank(cachedAccessToken)) {
				// 如果登陆了，踢掉之前的accessToken
				cacheService.delete(cachedAccessToken);
				cacheService.delete(mobile);

				cachedAccessToken = KeyFactory.accessToken(mobile);
				cacheService.set(mobile, cachedAccessToken, ACCESS_TOKEN_EXPIRE_TIME);
				cacheService.set(cachedAccessToken, JSON.toJSONString(retUserBean), ACCESS_TOKEN_EXPIRE_TIME);
			} else {
				cachedAccessToken = KeyFactory.accessToken(mobile);
				cacheService.set(mobile, cachedAccessToken, ACCESS_TOKEN_EXPIRE_TIME);
				cacheService.set(cachedAccessToken, JSON.toJSONString(retUserBean), ACCESS_TOKEN_EXPIRE_TIME);
			}
			logger.info("successed to login, accessToken:{}.", cachedAccessToken);
			result.setCode(1);
			result.setMessage("登陆成功");

			JSONObject data = JSON.parseObject(JSON.toJSONString(retUserBean));
			data.put("accessToken", cachedAccessToken);

			result.setData(data);
		} catch (SystemException e) {
			logger.error("Failed to login.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to login.", e);
			result.setCode(0);
			result.setMessage("登陆失败");
		}
		return result;
	}

	/**
	 * 退出
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ApiResponse<Boolean> logout(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<Boolean> result = new ApiResponse<Boolean>();
		try {
			JSONObject body = JSON.parseObject(requestBody);
			String accessToken = "";
			if (body.containsKey(CommonConstant.ACCESS_TOKEN)) {
				accessToken = body.getString(CommonConstant.ACCESS_TOKEN);
			}

			String cacheValue = cacheService.get(accessToken);
			if (StringUtil.isNotBlank(cacheValue)) {
				JSONObject user = JSON.parseObject(cacheValue);
				String mobile = user.getString("mobile");
				cacheService.delete(accessToken);
				cacheService.delete(mobile);
				logger.info("用户退出成功, mobile={}, name={}.", mobile, user.getString("username"));
			} else {
				logger.warn("用户已经退出或者会话已过期");
			}
			result.setCode(1);
			result.setMessage("退出成功");
		} catch (Exception e) {
			logger.error("Failed to logout.", e);
			result.setCode(0);
			result.setMessage("退出失败");
		}
		return result;
	}

	/**
	 * 注册前校验.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public ApiResponse<Boolean> verify(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<Boolean> result = new ApiResponse<Boolean>();
		try {
			JSONObject body = JSONObject.parseObject(requestBody);
			StringUtil.verifyReqParam(body, "mobile");

			String mobile = body.getString("mobile");
			UserBean user = userService.getUserByMobile(mobile);
			if (user != null && user.getId() > 0) {
				result.setData(true);
			} else {
				result.setData(false);
			}
			result.setCode(1);
			result.setMessage("校验成功");
		} catch (SystemException e) {
			logger.error("校验失败.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("校验失败.", e);
			result.setCode(0);
			result.setMessage("校验失败");
		}
		return result;
	}

	/**
	 * 注册.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/sign-up", method = RequestMethod.POST)
	public ApiResponse<UserBean> signUp(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<UserBean> result = new ApiResponse<UserBean>();

		try {
			JSONObject body = JSONObject.parseObject(requestBody);

			StringUtil.verifyReqParam(body, "mobile", "password", "invitationCode");

			String mobile = body.getString("mobile");
			String invitationCode = body.getString("invitationCode");
			String password = body.getString("password");

			UserBean tempUser = userService.getUserByMobile(mobile);
			if (tempUser != null) { // 用户已经存在
				result.setCode(0);
				result.setMessage("该手机号码已注册");
				return result;
			}

			UserBean userBean = userService.getUserByInvitationCode(invitationCode);
			if (userBean == null) {
				result.setCode(0);
				result.setMessage("请填写正确的邀请码");
				return result;
			}

			UserBean user = new UserBean();
			user.setMobile(mobile);
			user.setUsername(mobile); // 默认的用户名就是手机号
			user.setPassword(password);
			user.setUpLineMobile(userBean.getMobile());
			userService.signUp(user);

			result.setCode(1);
			result.setMessage("注册成功");
		} catch (SystemException e) {
			logger.error("Failed to signup.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to signup.", e);
			result.setCode(0);
			result.setMessage("注册失败");
		}
		return result;
	}

	/**
	 * 修改密码.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/update-password", method = RequestMethod.POST)
	public ApiResponse<Boolean> updatePassword(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<Boolean> result = new ApiResponse<Boolean>();

		try {
			JSONObject body = JSON.parseObject(requestBody);
			StringUtil.verifyReqParam(body, "mobile", "password");

			if (!body.containsKey(CommonConstant.ACCESS_TOKEN)
					|| StringUtil.isBlank(body.getString(CommonConstant.ACCESS_TOKEN))) {
				throw new AccessTokenException(9, "您当前无权访问，请先登陆");
			} else {
				String accessToken = body.getString(CommonConstant.ACCESS_TOKEN);
				String cacheValue = cacheService.get(accessToken);

				if (StringUtil.isBlank(cacheValue)) { // 回话过期
					throw new AccessTokenException(9, "会话过期，请重新登陆");
				} else {
					JSONObject object = JSON.parseObject(cacheValue);
					request.setAttribute(CommonConstant.REQUEST_KEY_MOBILE, object.getString("mobile"));
					request.setAttribute(CommonConstant.REQUEST_KEY_USER, JSON.parseObject(cacheValue, UserBean.class));
				}
			}

			String mobile = body.getString("mobile");
			String password = body.getString("password");
			userService.updatePassword(mobile, password);

			result.setCode(1);
			result.setMessage("修改成功");
			result.setData(true);
		} catch (AccessTokenException e) {
			logger.error("AccessToken exception:", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (SystemException e) {
			logger.error("Failed to login.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to update password.", e);
			result.setCode(0);
			result.setMessage("修改失败");
			result.setData(false);
		}
		return result;
	}

	/**
	 * 忘记密码
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/forget-password", method = RequestMethod.POST)
	public ApiResponse<Boolean> forgetPassword(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<Boolean> result = new ApiResponse<Boolean>();
		try {
			JSONObject body = JSON.parseObject(requestBody);
			StringUtil.verifyReqParam(body, "mobile", "password");

			String mobile = body.getString("mobile");
			String password = body.getString("password");

			userService.forgetPassword(mobile, password);

			result.setCode(1);
			result.setMessage("忘记密码修改成功");
			result.setData(true);
		} catch (SystemException e) {
			logger.error("Failed to login.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to forget password.", e);
			result.setCode(0);
			result.setMessage("忘记密码修改失败");
			result.setData(false);
		}
		return result;
	}

	/**
	 * 更新个人资料
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update-profile", method = RequestMethod.POST)
	public ApiResponse<String> updateProfile(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<String> result = new ApiResponse<String>();
		try {
			JSONObject body = JSON.parseObject(requestBody);
			if (!body.containsKey(CommonConstant.ACCESS_TOKEN)
					|| StringUtil.isBlank(body.getString(CommonConstant.ACCESS_TOKEN))) {
				throw new AccessTokenException(9, "您当前无权访问，请先登陆");
			} else {
				String accessToken = body.getString(CommonConstant.ACCESS_TOKEN);
				String cacheValue = cacheService.get(accessToken);

				if (StringUtil.isBlank(cacheValue)) { // 回话过期
					throw new AccessTokenException(9, "会话过期，请重新登陆");
				} else {
					JSONObject object = JSON.parseObject(cacheValue);
					request.setAttribute(CommonConstant.REQUEST_KEY_MOBILE, object.getString("mobile"));
					request.setAttribute(CommonConstant.REQUEST_KEY_USER, JSON.parseObject(cacheValue, UserBean.class));
				}
			}

			UserBean user = JSON.parseObject(requestBody, UserBean.class);
			UserBean cacheUser = (UserBean) request.getAttribute(CommonConstant.REQUEST_KEY_USER);
			user.setId(cacheUser.getId());
			user.setMobile(cacheUser.getMobile());

			if (StringUtil.isNotBlank(user.getMobile())) {
				UserBean tempUser = userService.getUserByMobile(user.getMobile());
				if (tempUser != null && user.getId() != tempUser.getId()) {
					throw new SystemException("手机号码已经被他人注册");
				}
			}
			String avatar = userService.updateProfile(user);
			result.setCode(1);
			result.setMessage("更新成功");
			result.setData(avatar);
		} catch (AccessTokenException e) {
			logger.error("AccessToken exception:", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (SystemException e) {
			logger.error("Failed to update profile.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to update profile.", e);
			result.setCode(0);
			result.setMessage("更新失败");
		}
		return result;
	}

	/**
	 * 搜索用户
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ApiResponse<List<SearchUserResBean>> searchUsers(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<List<SearchUserResBean>> result = new ApiResponse<List<SearchUserResBean>>();

		try {
			JSONObject body = JSONObject.parseObject(requestBody);
			StringUtil.verifyReqParam(body, "mobile");

			if (!body.containsKey(CommonConstant.ACCESS_TOKEN)
					|| StringUtil.isBlank(body.getString(CommonConstant.ACCESS_TOKEN))) {
				throw new AccessTokenException(9, "您当前无权访问，请先登陆");
			} else {
				String accessToken = body.getString(CommonConstant.ACCESS_TOKEN);
				String cacheValue = cacheService.get(accessToken);

				if (StringUtil.isBlank(cacheValue)) { // 回话过期
					throw new AccessTokenException(9, "会话过期，请重新登陆");
				} else {
					JSONObject object = JSON.parseObject(cacheValue);
					request.setAttribute(CommonConstant.REQUEST_KEY_MOBILE, object.getString("mobile"));
					request.setAttribute(CommonConstant.REQUEST_KEY_USER, JSON.parseObject(cacheValue, UserBean.class));
				}
			}

			List<SearchUserResBean> users = userService.searchUsers(body.getString("mobile"));
			result.setCode(1);
			result.setMessage("搜索成功");
			result.setData(users);
		} catch (AccessTokenException e) {
			logger.error("AccessToken exception:", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (SystemException e) {
			logger.error("Failed to login.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to search users.", e);
			result.setCode(0);
			result.setMessage("搜索失败");
		}
		return result;
	}

	/**
	 * 提升会员等级.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/enhance-member-level", method = RequestMethod.POST)
	public ApiResponse<Boolean> enhanceMemberLevel(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<Boolean> result = new ApiResponse<>();
		try {
			JSONObject body = JSON.parseObject(requestBody);

			if (!body.containsKey(CommonConstant.ACCESS_TOKEN)
					|| StringUtil.isBlank(body.getString(CommonConstant.ACCESS_TOKEN))) {
				throw new AccessTokenException(9, "您当前无权访问，请先登陆");
			} else {
				String accessToken = body.getString(CommonConstant.ACCESS_TOKEN);
				String cacheValue = cacheService.get(accessToken);

				if (StringUtil.isBlank(cacheValue)) { // 回话过期
					throw new AccessTokenException(9, "会话过期，请重新登陆");
				} else {
					JSONObject object = JSON.parseObject(cacheValue);
					request.setAttribute(CommonConstant.REQUEST_KEY_MOBILE, object.getString("mobile"));
					request.setAttribute(CommonConstant.REQUEST_KEY_USER, JSON.parseObject(cacheValue, UserBean.class));
				}
			}

			StringUtil.verifyReqParam(body, "id");

			Integer levelId = body.getInteger("id");
			if (levelId > 4 || levelId < 1) {
				throw new SystemException("会员等级不合法");
			}

			UserBean user = (UserBean) request.getAttribute(CommonConstant.REQUEST_KEY_USER);
			userService.enhanceMemberLevel(user.getId(), levelId);
			result.setCode(1);
			result.setMessage("购买会员成功");
		} catch (AccessTokenException e) {
			logger.error("AccessToken exception:", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (SystemException e) {
			logger.error("Failed to enhance member level.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to enhance member level.", e);
			result.setCode(0);
			result.setMessage("购买会员失败");
		}
		return result;
	}

}
