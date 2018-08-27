package com.huanxin.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;



public class MessageUtil {
	static Log log = LogFactory.getLog(MessageUtil.class);
	
	/**
	 * 签到的时候发送的短信通知
	 * yaoyz    2018年6月11日
	 */
	public static boolean sendMsgArrived(String name,String phone){
		log.info("name----"+name+"      phone-----"+phone);
		String host = "http://dingxin.market.alicloudapi.com";
	    String path = "/dx/sendSms";
	    String method = "POST";
	    String appcode = "4808969516324db5b30656b8f735a92c";
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("mobile", phone);
	    querys.put("param", "name:"+name+"");
	    querys.put("tpl_id", "TP1806113");
	    Map<String, String> bodys = new HashMap<String, String>();
	    try {
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	//获取response的body
	    	String ret = EntityUtils.toString(response.getEntity());
	    	log.info("msgsend-----"+ret);
	    	JSONObject json = JSONObject.fromObject(ret);
	    	if(json.get("return_code").equals("00000")){
	    		return true;
	    	}
	    } catch (Exception e) {
	    	log.error(MessageUtil.class,e);
	    }
	    return false;
	}
	
	/**
	 * 不足五次的时候，发送短信提示
	 * yaoyz    2018年6月11日
	 */
	public static boolean sendMsgRenew(String phone){
		String host = "http://dingxin.market.alicloudapi.com";
	    String path = "/dx/sendSms";
	    String method = "POST";
	    String appcode = "4808969516324db5b30656b8f735a92c";
	    Map<String, String> headers = new HashMap<String, String>();
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("mobile", phone);
	    querys.put("param", "");
	    querys.put("tpl_id", "TP1806116");
	    Map<String, String> bodys = new HashMap<String, String>();
	    try {
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	//获取response的body
	    	String ret = EntityUtils.toString(response.getEntity());
	    	log.info("msgsend-----"+ret);
	    	JSONObject json = JSONObject.fromObject(ret);
	    	if(json.get("return_code").equals("00000")){
	    		return true;
	    	}
	    } catch (Exception e) {
	    	log.error(MessageUtil.class,e);
	    }
	    return false;
	}
	
	public static void main(String[] args) {
//		String name="姚宇照";
//		String phone = "18226195598";
//		if(sendMsgArrived(name,phone)){
//			System.out.println("true");
//		}else{
//			System.out.println("发送失败");
//		}
		
//		if(sendMsgRenew(phone)){
//			System.out.println("发送成功");
//		}else{
//			System.out.println("发送失败");
//		}
	}
}
