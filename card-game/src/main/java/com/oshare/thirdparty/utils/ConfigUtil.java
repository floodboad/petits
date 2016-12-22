package com.oshare.thirdparty.utils;

import java.util.Properties;

public class ConfigUtil {

	private static Properties p = new Properties();

	public static String getProperty(String key) {
		return p.getProperty(key);
	}
}
