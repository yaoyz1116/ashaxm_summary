package com.ashaxm.laixuexi.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import net.sf.json.JSONObject;

/**
 * 工具类
 */
public class Tools {

	protected final static Logger log = LoggerFactory.getLogger(Tools.class);
	
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
	 * 发起https请求并获取结果
	 * yaoyz    2018年5月17日
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
	
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化(证书过滤)
			TrustManager[] tm = { new MyX509TrustManager() };
			// 取得SSL的SSLContext实例
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			// 初始化SSLContext
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
	
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
	
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
	
			
			if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();
			
			// 当有数据需要提交时(当outputStr不为null时，向输出流写数据)
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
	
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
	
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("连接超时: {}", ce);
		} catch (Exception e) {
			log.error("https请求异常: {}", e);
		}
		return jsonObject;
	}
	
		
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
