package com.ashaxm.personal.system;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

/**
 * 工具类
 * yaoyz    2018.6.23
 */
public class Tools {

	/**
	 * 字符转码
	 * yaoyz    2018年5月17日
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
	 * 得到随机数
	 * yaoyz    2018年5月17日
	 */
	public static int getRandom(int max) {
		Random ran = new Random();
		int num = ran.nextInt(max);
		return num;
	}
	
	/**
	 * 交换位置
	 * yaoyz    2018年5月17日
	 */
	public static void swap(String[] data, int index1, int index2) {
		String temp = null;
		temp = data[index1];
		data[index1] = data[index2];
		data[index2] = temp;
	}
	
	/**
	 * 得到当前时间的格式化
	 * yaoyz    2018年5月17日
	 */
	public static String getDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		return df.format(new Date());
	}
	
	/**
	 * 当前日期增加若干天之后的格式化日期
	 * yaoyz    2018年5月17日
	 */
	public static String getDateTime(int day) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		Calendar rightNow = Calendar.getInstance();
	    //rightNow.setTime(new Date());
	    //rightNow.add(Calendar.YEAR,-1);//日期减1年
	    //rightNow.add(Calendar.MONTH,3);//日期加3个月
	    rightNow.add(Calendar.DAY_OF_YEAR,day);//日期加10天
	    Date dt=rightNow.getTime();
		return df.format(dt);
	}
	
	/**
	 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP,
	 * yaoyz    2018年5月17日
	 */
	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取随机数
	 * yaoyz    2018年6月23日
	 */
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	}
}
