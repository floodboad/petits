package com.oshare.thirdparty.service.tpay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component()
public class AlipayHandler {

	private final Logger logger = LoggerFactory.getLogger(AlipayHandler.class);

	/**
	 * 统一下单.
	 */
	public void alipayUnifiedOrder() {
		logger.info("Start alipay unified order...");
	}

}
