package com.oshare.thirdparty.test;

import java.io.IOException;

import com.oshare.thirdparty.utils.ImageUtil;


public class ImageTest {

	public static void main(String[] args) {
		try {
			System.out.println(ImageUtil.generateImageStr("C:/Users/mengzhg/Desktop/123.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
