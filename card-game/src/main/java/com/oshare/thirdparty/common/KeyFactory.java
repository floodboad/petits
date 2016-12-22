package com.oshare.thirdparty.common;

import java.util.Date;

import com.oshare.thirdparty.utils.DateUtil;
import com.oshare.thirdparty.utils.EncryptUtils;
import com.oshare.thirdparty.utils.UUIDUtil;

/**
 * KEY工厂生成器
 */
public class KeyFactory {

	/**
	 * 生成卡片卡号
	 * 
	 * @return the string
	 */
	public static String cardNo() {
		return DateUtil.formatDate(new Date(), DateUtil.NO_BLANK_SHORT_YEAR_PATTERN).concat(UUIDUtil.getRandomInt(3));
	}

	/**
	 * Access token.
	 * 
	 * @param mobile
	 *            the mobile
	 * @return the string
	 */
	public static String accessToken(String mobile) {
		return EncryptUtils.encodeMD5(UUIDUtil.uuid().concat(mobile));
	}

}
