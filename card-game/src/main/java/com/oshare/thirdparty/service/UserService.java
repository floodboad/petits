package com.oshare.thirdparty.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oshare.thirdparty.common.exception.SystemException;
import com.oshare.thirdparty.dao.UserDao;
import com.oshare.thirdparty.entity.bean.SearchUserResBean;
import com.oshare.thirdparty.entity.bean.UserBean;
import com.oshare.thirdparty.entity.model.UserModel;
import com.oshare.thirdparty.utils.DateUtil;
import com.oshare.thirdparty.utils.EncryptUtils;
import com.oshare.thirdparty.utils.ImageUtil;
import com.oshare.thirdparty.utils.StringUtil;
import com.oshare.thirdparty.utils.UUIDUtil;

/**
 * 用户相关service
 */
@Service
public class UserService {

	@Value("#{variables['url.prefix.resource']}")
	private String URL_PREFIX_RESOURCE;

	@Value("#{variables['path.prefix.resource']}")
	private String PATH_PREFIX_RESOURCE;

	@Autowired
	private UserDao userDao;

	/**
	 * 登陆
	 * 
	 * @param user
	 *            the user
	 * @return the user bean
	 * @throws MemcachedException
	 * @throws InterruptedException
	 * @throws TimeoutException
	 */
	@Transactional(readOnly = true)
	public UserBean login(UserBean user) throws SystemException {
		String encodePassword = EncryptUtils.encodeMD5(user.getPassword());
		UserModel userModel = this.userDao.login(user.getMobile(), encodePassword);
		if (userModel == null) {
			throw new SystemException("用户名或者密码错误");
		}

		UserBean userBean = new UserBean();
		userBean.from(userModel);
		return userBean;
	}

	/**
	 * 用户注册.
	 * 
	 * @param user
	 *            the user
	 */
	@Transactional(rollbackFor = Throwable.class)
	public void signUp(UserBean user) throws SystemException {
		UserModel userModel = new UserModel();
		user.to(userModel);
		userModel.setPassword(EncryptUtils.encodeMD5(user.getPassword()));

		UserBean tempUser = null;
		String tempInvitationCode;
		do {
			tempInvitationCode = UUIDUtil.getRandomInt(6);
			tempUser = this.getUserByInvitationCode(tempInvitationCode);
		} while (tempUser != null);

		userModel.setInvitationCode(tempInvitationCode);
		userModel.setAccountLevel(1);
		userModel.setSex(1);
		userModel.setStatus(1);
		userModel.setRole(1);
		this.userDao.signUp(userModel);
	}

	/**
	 * 更新用户资料信息.
	 * 
	 * @param user
	 *            the user
	 * @throws IOException
	 */
	@Transactional(rollbackFor = Throwable.class, readOnly = false)
	public String updateProfile(UserBean user) throws SystemException, IOException {

		String avatarByte = user.getAvatar();
		if (StringUtil.isNotBlank(avatarByte)) {
			String fileName = user.getMobile() + ImageUtil.JPG;
			ImageUtil.generateImage(avatarByte, PATH_PREFIX_RESOURCE + fileName);
			user.setAvatar(URL_PREFIX_RESOURCE + fileName);
		}
		UserModel model = new UserModel();
		user.to(model);
		model.setUpdateTime(DateUtil.now());
		this.userDao.updateProfile(model);

		return user.getAvatar();
	}

	/**
	 * 用户更新密码.
	 * 
	 * @param mobile
	 *            手机号
	 * @param password
	 *            密码
	 */
	@Transactional(rollbackFor = Throwable.class)
	public void updatePassword(String mobile, String password) throws SystemException {
		this.userDao.updatePassword(mobile, EncryptUtils.encodeMD5(password));
	}

	/**
	 * 忘记密码
	 * 
	 * @param mobile
	 * @param password
	 */
	@Transactional(rollbackFor = Throwable.class)
	public void forgetPassword(String mobile, String password) throws SystemException {
		this.userDao.updatePassword(mobile, EncryptUtils.encodeMD5(password));
	}

	/**
	 * 搜索用户.
	 * 
	 * @param mobile
	 *            the mobile
	 * @return the list
	 */
	@Transactional(readOnly = true)
	public List<SearchUserResBean> searchUsers(String mobile) throws SystemException {
		List<SearchUserResBean> userBeans = this.userDao.searchUsers(mobile);
		return userBeans;
	}

	/**
	 * 根据邀请码获取用户.
	 * 
	 * @param invitationCode
	 *            the invitation code
	 * @return the user by invitation code
	 */
	@Transactional(readOnly = true)
	public UserBean getUserByInvitationCode(String invitationCode) {
		UserModel model = this.userDao.getUserByInvitationCode(invitationCode);
		if (model == null) {
			return null;
		}
		UserBean bean = new UserBean();
		bean.from(model);
		return bean;
	}

	/**
	 * 会员升级.
	 * 
	 * @param id
	 *            the id
	 * @param levelId
	 *            the level id
	 * @throws SystemException
	 *             the system exception
	 */
	@Transactional(rollbackFor = Throwable.class)
	public void enhanceMemberLevel(Long id, Integer levelId) throws SystemException {
		this.userDao.enhanceMemberLevel(id, levelId);
	}

	/**
	 * 通过手机号码查询用户
	 * 
	 * @param mobile
	 *            the mobile
	 * @return the user
	 */
	public UserBean getUserByMobile(String mobile) throws SystemException {
		UserModel model = this.userDao.getUser(mobile);
		if (model == null) {
			return null;
		}
		UserBean user = new UserBean();
		user.from(model);
		return user;
	}
}
