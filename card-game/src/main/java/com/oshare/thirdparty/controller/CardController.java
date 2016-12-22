package com.oshare.thirdparty.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.oshare.thirdparty.common.constant.CommonConstant;
import com.oshare.thirdparty.common.exception.AccessTokenException;
import com.oshare.thirdparty.common.exception.SystemException;
import com.oshare.thirdparty.common.http.ApiResponse;
import com.oshare.thirdparty.entity.bean.AllCardsResBean;
import com.oshare.thirdparty.entity.bean.UserBean;
import com.oshare.thirdparty.entity.bean.UserCardBean;
import com.oshare.thirdparty.service.CacheService;
import com.oshare.thirdparty.service.UserCardService;
import com.oshare.thirdparty.utils.DateUtil;
import com.oshare.thirdparty.utils.StringUtil;

/**
 * 
 * 棋盘游戏相关controller
 * 
 * @author mengzhg
 */
@Controller
@RequestMapping("/card")
public class CardController {

	private static Logger logger = LoggerFactory.getLogger(CardController.class);

	@Autowired
	private UserCardService cardService;

	@Autowired
	private CacheService cacheService;

	/**
	 * 购买卡片.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public ApiResponse<List<UserCardBean>> buyCard(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);
		
		ApiResponse<List<UserCardBean>> result = new ApiResponse<List<UserCardBean>>();
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

			StringUtil.verifyReqParam(body, "cards");

			List<UserCardBean> cards = JSON.parseArray(body.get("cards").toString(), UserCardBean.class);

			UserBean user = (UserBean) request.getAttribute(CommonConstant.REQUEST_KEY_USER);
			List<UserCardBean> resCards = cardService.buyCards(cards, user.getId());
			result.setCode(1);
			result.setMessage("购买成功");
			result.setData(resCards);
		} catch (AccessTokenException e) {
			logger.error("AccessToken exception:", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to buy cards.", e);
			result.setCode(0);
			result.setMessage("购买失败");
		}
		return result;
	}

	/**
	 * 赠送卡片.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/donate", method = RequestMethod.POST)
	public ApiResponse<Boolean> donateCard(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);

		ApiResponse<Boolean> result = new ApiResponse<Boolean>();
		try {
			JSONObject body = JSONObject.parseObject(requestBody);

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

			StringUtil.verifyReqParam(body, "mobile", "cardNos");

			String mobile = body.getString("mobile");
			JSONArray cardNoJson = body.getJSONArray("cardNos");
			List<String> cardNos = JSON.parseArray(cardNoJson.toJSONString(), String.class);

			UserBean user = (UserBean) request.getAttribute(CommonConstant.REQUEST_KEY_USER);
			cardService.donateCard(mobile, cardNos, user.getId());

			result.setCode(1);
			result.setMessage("赠送成功");
			result.setData(true);
		} catch (AccessTokenException e) {
			logger.error("AccessToken exception:", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (SystemException e) {
			logger.error("Failed to donate card.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
			result.setData(false);
		} catch (Exception e) {
			logger.error("Failed to donate card.", e);
			result.setCode(0);
			result.setMessage("赠送失败");
			result.setData(false);
		}
		return result;
	}

	/**
	 * 卡片提现.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/withdrawals", method = RequestMethod.POST)
	public ApiResponse<Boolean> withdrawals(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);
		ApiResponse<Boolean> result = new ApiResponse<Boolean>();
		try {
			JSONObject body = JSONObject.parseObject(requestBody);

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

			StringUtil.verifyReqParam(body, "cardNos");

			List<UserCardBean> cards = new ArrayList<UserCardBean>();
			cardService.withdrawals(cards, 1);
			result.setCode(1);
			result.setMessage("提现成功");
		} catch (AccessTokenException e) {
			logger.error("AccessToken exception:", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			result.setCode(0);
			result.setMessage("提现失败");
		}
		return result;
	}

	/**
	 * 获取棋盘卡片信息
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/cards", method = RequestMethod.POST)
	public ApiResponse<List<AllCardsResBean>> cards(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);
		
		ApiResponse<List<AllCardsResBean>> result = new ApiResponse<List<AllCardsResBean>>();
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

			int xMin = body.getIntValue("minXCoords");
			int xMax = body.getIntValue("maxXCoords");
			int yMin = body.getIntValue("minYCoords");
			int yMax = body.getIntValue("maxYCoords");

			List<AllCardsResBean> beans = cardService.getCards(xMin, xMax, yMin, yMax);
			result.setCode(1);
			result.setMessage("操作成功");
			result.setData(beans);
		} catch (AccessTokenException e) {
			logger.error("AccessToken exception:", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to get all cards.", e);
			result.setCode(0);
			result.setMessage("操作失败");
		}
		return result;
	}

	/**
	 * 我的卡片.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/mycards", method = RequestMethod.POST)
	public ApiResponse<List<UserCardBean>> myCards(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);
		
		ApiResponse<List<UserCardBean>> result = new ApiResponse<List<UserCardBean>>();
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

			UserBean userBean = (UserBean) request.getAttribute(CommonConstant.REQUEST_KEY_USER);
			List<UserCardBean> cards = cardService.getMycards(userBean.getId());
			result.setCode(1);
			result.setMessage("操作成功");
			result.setData(cards);
		} catch (AccessTokenException e) {
			logger.error("AccessToken exception:", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to get my cards.", e);
			result.setCode(0);
			result.setMessage("操作失败");
		}
		return result;
	}

	/**
	 * 搜索用户卡片.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ApiResponse<List<AllCardsResBean>> searchCards(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);
		
		ApiResponse<List<AllCardsResBean>> result = new ApiResponse<List<AllCardsResBean>>();
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

			int index = 0;
			int offset = 50;
			if (body.containsKey("index")) {
				int tempIndex = body.getIntValue("index");
				if (tempIndex <= 0) {
					throw new SystemException("index不能小于1");
				}

				index = body.getIntValue("index") - 1;
			}
			if (body.containsKey("offset")) {
				offset = body.getIntValue("offset");
			}

			String cardNo = "";
			String mobile = "";
			Integer score = null;
			Date beginTime = null;
			Date endTime = null;
			if (body.containsKey("cardNo")) {
				cardNo = body.getString("cardNo");
			}
			if (body.containsKey("mobile")) {
				mobile = body.getString("mobile");
			}
			if (body.containsKey("score")) {
				score = body.getInteger("score");
			}
			if (body.containsKey("time")) {
				if (body.getJSONObject("time").containsKey("beginTime")) {
					beginTime = DateUtil.parseDate(body.getJSONObject("time").getString("beginTime"));
				}
				if (body.getJSONObject("time").containsKey("endTime")) {
					endTime = DateUtil.parseDate(body.getJSONObject("time").getString("endTime"));
				}
			}
			List<AllCardsResBean> cards = cardService.searchCards(cardNo, mobile, score, beginTime, endTime, index,
					offset);
			result.setCode(1);
			result.setMessage("操作成功");
			result.setData(cards);
		} catch (AccessTokenException e) {
			logger.error("AccessToken exception:", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (SystemException e) {
			logger.error("Failed to search cards.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to search cards.", e);
			result.setCode(0);
			result.setMessage("操作失败");
		}
		return result;
	}

	/**
	 * 管理员给卡片赋值.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/set-value", method = RequestMethod.POST)
	public ApiResponse<Boolean> setCardScore(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String requestBody) {
		logger.info("======Request info: uri={},request body={}.", request.getRequestURI(), requestBody);
		
		ApiResponse<Boolean> result = new ApiResponse<Boolean>();
		try {
			JSONObject body = JSONObject.parseObject(requestBody);

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

			StringUtil.verifyReqParam(body, "cards");

			JSONArray cardsJson = body.getJSONArray("cards");
			List<UserCardBean> cards = JSON.parseArray(
					JSON.toJSONString(cardsJson, SerializerFeature.WriteNullStringAsEmpty), UserCardBean.class);
			cardService.setCardValue(cards);
			result.setCode(1);
			result.setMessage("设置成功");
		} catch (AccessTokenException e) {
			logger.error("AccessToken exception:", e);
			result.setCode(e.getCode());
			result.setMessage(e.getMessage());
		} catch (SystemException e) {
			logger.error("Failed to set card value.", e);
			result.setCode(0);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error("Failed to set card value.", e);
			result.setCode(0);
			result.setMessage("设置失败");
		}
		return result;
	}
}
