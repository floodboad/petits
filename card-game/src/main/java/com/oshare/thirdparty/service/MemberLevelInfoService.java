package com.oshare.thirdparty.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.oshare.thirdparty.common.constant.CacheKeyConstant;
import com.oshare.thirdparty.common.exception.CacheException;
import com.oshare.thirdparty.dao.MemberLevelInfoDao;
import com.oshare.thirdparty.entity.bean.MemberLevelBean;
import com.oshare.thirdparty.entity.model.MemberLevelInfoModel;

@Service
public class MemberLevelInfoService {

	@Autowired
	private MemberLevelInfoDao dao;

	@Autowired
	private CacheService cacheService;

	/**
	 * 获取会员等级信息
	 * 
	 * @return
	 * @throws CacheException
	 */
	public List<MemberLevelBean> getMemberLevelInfos() throws CacheException {

		String cache = cacheService.get(CacheKeyConstant.KEY_MEMBER_LEVEL_INFO);
		if (StringUtils.isNotBlank(cache)) {
			return JSON.parseArray(cache, MemberLevelBean.class);
		}

		List<MemberLevelInfoModel> memberInfoModels = dao.queryMemberInfos();
		if (memberInfoModels == null) {
			return null;
		}

		List<MemberLevelBean> beans = new ArrayList<>();
		for (MemberLevelInfoModel memberLevelInfoModel : memberInfoModels) {
			MemberLevelBean bean = new MemberLevelBean();
			bean.from(memberLevelInfoModel);
			beans.add(bean);
		}
		cacheService.set(CacheKeyConstant.KEY_MEMBER_LEVEL_INFO, JSON.toJSONString(beans));
		return beans;
	}

}
