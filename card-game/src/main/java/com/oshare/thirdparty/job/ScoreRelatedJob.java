package com.oshare.thirdparty.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 积分相关的任务.
 */
@Component
@Lazy(false)
public class ScoreRelatedJob {

	private Logger logger = LoggerFactory.getLogger(ScoreRelatedJob.class);

	/**
	 * 分配积分(上下级分销关系).
	 */
	@Scheduled(cron = "0 0 0/3 * * ?")
	public void allocateScore() {
		logger.info("Begin to execute allocate score job...");

		logger.info("Finished execute allocate score job.");
	}

	/**
	 * 清理系统缓存
	 */
	@Scheduled(cron="0 1 0 * * ?")
	public void cleanCache(){
		logger.info("Begin to execute clean the system cache job...");
		
		logger.info("Finished execute clean the system cache job.");
	}

	
}
