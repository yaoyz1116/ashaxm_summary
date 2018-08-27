package com.ashaxm.personal.system;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

/**
 * ������
 * yaoyz    2018.6.23
 */
public class Tools {

	/**
	 * �ַ�ת��
	 * yaoyz    2018��5��17��
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
	 * �õ������
	 * yaoyz    2018��5��17��
	 */
	public static int getRandom(int max) {
		Random ran = new Random();
		int num = ran.nextInt(max);
		return num;
	}
	
	/**
	 * ����λ��
	 * yaoyz    2018��5��17��
	 */
	public static void swap(String[] data, int index1, int index2) {
		String temp = null;
		temp = data[index1];
		data[index1] = data[index2];
		data[index2] = temp;
	}
	
	/**
	 * �õ���ǰʱ��ĸ�ʽ��
	 * yaoyz    2018��5��17��
	 */
	public static String getDateTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// �������ڸ�ʽ
		return df.format(new Date());
	}
	
	/**
	 * ��ǰ��������������֮��ĸ�ʽ������
	 * yaoyz    2018��5��17��
	 */
	public static String getDateTime(int day) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
		Calendar rightNow = Calendar.getInstance();
	    //rightNow.setTime(new Date());
	    //rightNow.add(Calendar.YEAR,-1);//���ڼ�1��
	    //rightNow.add(Calendar.MONTH,3);//���ڼ�3����
	    rightNow.add(Calendar.DAY_OF_YEAR,day);//���ڼ�10��
	    Date dt=rightNow.getTime();
		return df.format(dt);
	}
	
	/**
	 * ��ȡ�ͻ���IP��ַ;����ͨ����Nginx��ȡ;X-Real-IP,
	 * yaoyz    2018��5��17��
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
	 * ��ȡ�����
	 * yaoyz    2018��6��23��
	 */
	public static String getRandomString(int length) { //length��ʾ�����ַ����ĳ���
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
