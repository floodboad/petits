package com.oshare.thirdparty.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.oshare.thirdparty.entity.bean.NoticeBean;

/**
 * 消息通知服务
 * 
 * @author mengzhg
 */
@Service
public class NoticeService {

	private Logger logger = LoggerFactory.getLogger(NoticeService.class);

	/**
	 * 获取最新的通知消息.
	 * 
	 * @param count
	 *            the count
	 * @return the list
	 */
	public List<NoticeBean> latestMessages(int count) {
		logger.info("Get latest messages, count={}.", count);
		
		
		
		return null;
	}

}
