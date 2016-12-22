package com.oshare.thirdparty.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oshare.thirdparty.entity.bean.AllCardsResBean;
import com.oshare.thirdparty.entity.bean.UserCardBean;
import com.oshare.thirdparty.entity.model.UserCardModel;

/**
 * 用户卡片
 * 
 * @author mengzhg
 * @version 1.0
 * @since 2016/11/20 22:35
 */
public interface UserCardDao {

	/**
	 * 新增用户卡片.
	 * 
	 * @param model
	 *            the model
	 * @return the long
	 */
	public void addCard(@Param("card") UserCardModel model);

	/**
	 * 获取用户自己的卡片.
	 * 
	 * @param userId
	 *            the user id
	 * @return the mycards
	 */
	public List<UserCardModel> getMycards(@Param("userId") Long userId);

	/**
	 * 更新卡片信息
	 * 
	 * @param model
	 * @return
	 */
	public int updateCardInfo(@Param("model") UserCardModel model);

	/**
	 * 查询棋盘卡片信息
	 * 
	 * @param xMin
	 * @param xMax
	 * @param yMin
	 * @param yMax
	 * @return
	 */
	public List<AllCardsResBean> getCards(@Param("xMin") int xMin, @Param("xMax") int xMax, @Param("yMin") int yMin,
			@Param("yMax") int yMax);

	/**
	 * 赠送卡片.
	 * 
	 * @param mobile
	 *            the mobile
	 * @param cardNos
	 *            the card nos
	 */
	public int donateCard(@Param("donaterId") Long donaterId, @Param("donatorId") Long donatorId,
			@Param("cardNos") List<String> cardNos);

	/**
	 * 搜索卡片.
	 * 
	 * @param cardNo
	 *            the card no
	 * @param mobile
	 *            the mobile
	 * @param score
	 *            the score
	 * @param beginTime
	 *            the begin time
	 * @param endTime
	 *            the end time
	 * @return the list
	 */
	public List<AllCardsResBean> searchCards(@Param("cardNo") String cardNo, @Param("mobile") String mobile,
			@Param("score") Integer score, @Param("begin") Date beginTime, @Param("end") Date endTime,
			@Param("index") int index, @Param("offset") int offset);

	/**
	 * 管理员设置卡片积分值.
	 * 
	 * @param userCardBean
	 *            the new card value
	 */
	public int setCardValue(UserCardBean userCardBean);

}