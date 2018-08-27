package com.ashaxm.personaltools.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;


import net.sf.json.JSONObject;

public class MessageUtils {
	
	
	/**
	 * 小程序中，针对特定用户进行的发送短信提醒服务
	 * 对备忘录中的信息发送短信
	 * 目前只针对丁慧敏同学
	 * yaoyz    2018年7月25日
	 */
	public static boolean sendMsg(String title,String time, String des,String phone){
		System.out.println("deadline-----"+time+"  title----"+title+"  des--"+des);
		String host = "http://dingxin.market.alicloudapi.com";
	    String path = "/dx/sendSms";
	    String method = "POST";
	    String appcode = "4808969516324db5b30656b8f735a92c";
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("mobile", phone);
	    querys.put("param", "time:"+time+",title:"+title+",des:"+des);
	    querys.put("tpl_id", "TP18072511");
	    Map<String, String> bodys = new HashMap<String, String>();
	    try {
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	//获取response的body
	    	String ret = EntityUtils.toString(response.getEntity());
	    	System.out.println("msgsend-----"+ret);
	    	JSONObject json = JSONObject.fromObject(ret);
	    	if(json.get("return_code").equals("00000")){
	    		return true;
	    	}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return false;
	}
	
	public static void main(String[] args) {
		String title = "小丁同学";
		String time="07-02";
		String des = "老姚爱你";
		String phone = "15221656208";
//		String phone = "17756970180";
		sendMsg(title, time, des, phone);
	}
	
}
