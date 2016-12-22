package com.oshare.thirdparty.service.tpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TenpayHandler {

	private final Logger logger = LoggerFactory.getLogger(TenpayHandler.class);

	/**
	 * 统一下单.
	 */
	public void tenpayUnifiedOrder() {
		logger.info("Start tenpay unified order...");
	}
}
