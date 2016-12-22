package com.oshare.thirdparty.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oshare.thirdparty.entity.bean.SearchUserResBean;
import com.oshare.thirdparty.entity.model.UserModel;

/**
 * 用户信息操作Dao.
 */
public interface UserDao {

	/**
	 * 登陆.
	 * 
	 * @param mobile
	 *            the mobile
	 * @param password
	 *            the password
	 * @return the user model
	 */
	public UserModel login(@Param("mobile") String mobile, @Param("password") String password);

	/**
	 * 更新用户资料.
	 * 
	 * @param user
	 *            the user
	 * @return the int
	 */
	public int updateProfile(UserModel user);

	/**
	 * 注册.
	 * 
	 * @param userModel
	 *            the user model
	 */
	public void signUp(UserModel userModel);

	/**
	 * 获取用户信息.
	 * 
	 * @param mobile
	 *            the mobile
	 * @return the user
	 */
	public UserModel getUser(@Param("mobile") String mobile);

	/**
	 * 更新用户密码.
	 * 
	 * @param mobile
	 *            the mobile
	 * @param password
	 *            the password
	 */
	public void updatePassword(@Param("mobile") String mobile, @Param("password") String password);

	/**
	 * 搜索用户
	 * 
	 * @param mobile
	 *            the mobile
	 * @return the list
	 */
	public List<SearchUserResBean> searchUsers(@Param("mobile") String mobile);

	/**
	 * 根据邀请码查询用户.
	 * 
	 * @param invitationCode
	 *            the invitation code
	 * @return the user by invitation code
	 */
	public UserModel getUserByInvitationCode(@Param("invitationCode") String invitationCode);

	/**
	 * 购买会员.
	 * 
	 * @param id
	 *            the id
	 * @param levelId
	 *            the level id
	 */
	public void enhanceMemberLevel(@Param("id") Long id, @Param("levelId") Integer levelId);

}
