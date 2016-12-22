package com.oshare.thirdparty.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.oshare.thirdparty.common.http.ApiResponse;
import com.oshare.thirdparty.entity.bean.CarouselBean;
import com.oshare.thirdparty.entity.bean.NoticeBean;
import com.oshare.thirdparty.utils.DateUtil;

/**
 * 首页相关的controller
 * 
 * @author mengzhg
 * 
 */
@Controller
@RequestMapping("/home")
public class HomePageController {

	private Logger logger = LoggerFactory.getLogger(HomePageController.class);

	/**
	 * 获取首页信息.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the api response
	 */
	@ResponseBody
	@RequestMapping(value = "/homeinfo", method = RequestMethod.POST)
	public ApiResponse<JSONObject> homePage(HttpServletRequest request, HttpServletResponse response) {
		logger.info("======Request info: uri={}.", request.getRequestURI());

		ApiResponse<JSONObject> result = new ApiResponse<JSONObject>();
		try {
			JSONObject data = new JSONObject(true);

			// 1.项目本身的数据
			data.put("cardCount", 398);
			data.put("memberCount", 1000);
			data.put("projectCount", 24);

			// 2.系统通知
			List<NoticeBean> notices = new ArrayList<NoticeBean>();
			for (int i = 0; i < 5; i++) {
				NoticeBean notice = new NoticeBean();
				notice.setTitle("这里是测试标题");
				notice.setContent("我们的APP将要上线了，这里是content");
				notice.setTime(DateUtil.now());
				notices.add(notice);
			}
			data.put("notices", notices);

			// 3.轮播图
			List<CarouselBean> carousels = new ArrayList<CarouselBean>();
			for (int i = 0; i < 3; i++) {
				CarouselBean carousel = new CarouselBean();
				carousel.setImageUrl("https://img12.360buyimg.com/da/jfs/t3835/59/1855621213/194741/dd623ff1/58402efaN4cc01373.jpg");
				carousel.setLinkUrl("https://www.jd.com/");
				carousel.setSort(i + 1);
				carousels.add(carousel);
			}
			data.put("carousels", carousels);

			// 4.配置信息
			JSONObject configs = new JSONObject(true);
			configs.put("maxCardScore", 200);
			data.put("configs", configs);

			result.setCode(1);
			result.setMessage("查询成功");
			result.setData(data);
		} catch (Exception e) {
			logger.error("Failed to get home page information.", e);
			result.setCode(0);
			result.setMessage("查询失败");
		}
		return result;
	}
}
