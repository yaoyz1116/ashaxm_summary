package top.ashaxm.common.utils;

import java.security.MessageDigest;

/**
 * MD5 加密工具
 * 这是标准的md5加密
 */
public class Md5Util {

	public static String MD5(String str) {
		StringBuffer sb = new StringBuffer(32);

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(str.getBytes("UTF-8"));

			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.toUpperCase().substring(1, 3));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		// String str = "E10ADC3949BA59ABBE56E057F20F883E";

		System.out.println(MD5("123456"));
		//E10ADC3949BA59ABBE56E057F20F883E
	}

}
