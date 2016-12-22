package com.oshare.thirdparty.wechat;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sword.wechat4j.WechatSupport;

/**
 * Wechat对象.
 */
public class WeChat extends WechatSupport {

	public final static Logger logger = LoggerFactory.getLogger(WeChat.class);

	public WeChat(HttpServletRequest request) {
		super(request);
	}

	@Override
	protected void click() {

	}

	@Override
	protected void kfCloseSession() {

	}

	@Override
	protected void kfCreateSession() {

	}

	@Override
	protected void kfSwitchSession() {

	}

	@Override
	protected void location() {

	}

	@Override
	protected void locationSelect() {

	}

	@Override
	protected void onImage() {

	}

	@Override
	protected void onLink() {

	}

	@Override
	protected void onLocation() {

	}

	@Override
	protected void onShortVideo() {

	}

	@Override
	protected void onText() {

	}

	@Override
	protected void onUnknown() {

	}

	@Override
	protected void onVideo() {

	}

	@Override
	protected void onVoice() {

	}

	@Override
	protected void picPhotoOrAlbum() {

	}

	@Override
	protected void picSysPhoto() {

	}

	@Override
	protected void picWeixin() {

	}

	@Override
	protected void scan() {

	}

	@Override
	protected void scanCodePush() {

	}

	@Override
	protected void scanCodeWaitMsg() {

	}

	@Override
	protected void subscribe() {

	}

	@Override
	protected void templateMsgCallback() {

	}

	@Override
	protected void unSubscribe() {

	}

	@Override
	protected void view() {

	}

}
