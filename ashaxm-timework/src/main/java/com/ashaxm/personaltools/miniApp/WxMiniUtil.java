package com.ashaxm.personaltools.miniApp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

public class WxMiniUtil {
	
	private static String APPID = "wx18011ffeef259d9f";
	private static String SECRET_KEY = "0f92563c469ce593bd64b93d56ac242d";
	
	/**
	 * 根据appid和secretkey获取对应的一个accessToken
	 * accessToken不需要重复获取，每次都从一个地方拿就可以了
	 * yaoyz    2018年5月22日ww
	 */
	public static JSONObject getUniqueAccessToken(){
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET_KEY;
		String ret = sendPost(requestUrl, null);
		return JSONObject.fromObject(ret);
	}
	
	public static JSONObject sendTemplateMsg(WxTemplate t){
		String token = WxProperty.getAccessToken().getAccesstoken();
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+token;		
		JSONObject map = new JSONObject();
		map.put("touser", t.getTouser());
		map.put("template_id", t.getTemplate_id());
		map.put("form_id", t.getFormId());
		map.put("page", t.getPage());
		map.put("data", t.getData());	
		String ret = sendPost(requestUrl, map.toString());		
		return JSONObject.fromObject(ret);
	}
	
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "gb2312"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		getUniqueAccessToken();
	}
}
