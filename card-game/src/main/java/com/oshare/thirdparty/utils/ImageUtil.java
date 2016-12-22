package com.oshare.thirdparty.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

public class ImageUtil {

	/** JPG图片后缀 */
	public static final String JPG = ".jpg";

	/** PNG图片后缀. */
	public static final String PNG = ".png";

	/**
	 * 将字符串转为图片.
	 * 
	 * @param imgStr
	 *            图片字符串
	 * @param imgFile
	 *            图片文件地址
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static boolean generateImage(String imgStr, String imgFile) throws IOException {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		try {
			// Base64解码
			byte[] b = Base64.decodeBase64(imgStr.getBytes("UTF-8"));
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpg图片
			String imgFilePath = imgFile;// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 获取图片字符串<br>
	 * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理.
	 * 
	 * @param imgFile
	 *            图片文件地址
	 * @return the image str
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String generateImageStr(String imgFile) throws IOException {
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		// 返回Base64编码过的字节数组字符串
		return new String(Base64.encodeBase64(data));
	}

}
