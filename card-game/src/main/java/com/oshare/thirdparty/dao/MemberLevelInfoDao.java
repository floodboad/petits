package com.oshare.thirdparty.dao;

import java.util.List;

import com.oshare.thirdparty.entity.model.MemberLevelInfoModel;

/**
 * 会员等级信息
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/11/20 22:35
 */
public interface MemberLevelInfoDao {

	/**
	 * 查询系统中所有的会员信息.
	 * 
	 * @return the list
	 */
	public List<MemberLevelInfoModel> queryMemberInfos();

}