package top.ashaxm.common.utils;

import java.io.UnsupportedEncodingException;


public class StringUtils {

	/**
	 * 将字符串按照指定的格式编码，解码
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static String convertEncode(String srcStr, String srcEncode,
			String destEncode) {
		String destStr = "";
		try {
			destStr = new String(srcStr.getBytes(srcEncode), destEncode);
		} catch (UnsupportedEncodingException e) {
			destStr = srcStr;
			e.printStackTrace();
		}
		return destStr;
	}

	/**
	 * 字符串去首尾空白
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static String clearString(String str) {
		if (str == null)
			return "";
		return str.trim();
	}
}
