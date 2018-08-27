package com.huanxin.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import net.sf.json.JSONObject;

public class WxUtil {
	
	protected final static Logger log = LoggerFactory.getLogger(WxUtil.class);
	 
	//来学习
	private static String APPID = "wx2dc78f607d83b5e2";
	private static String SECRET_KEY = "41212095d88a3053c88dd2fb040f8218";
	private static String GRANT_TYPE="authorization_code";
	
	
	/**
	 * 发送 res.code 到后台换取 openId, sessionKey, unionId
	 * yaoyz    2018年5月21日
	 */
	public static JSONObject getAccessToken(String code){  
	    //微信端登录code值  
	    String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";  //请求地址 https://api.weixin.qq.com/sns/jscode2session  
	    Map<String,String> requestUrlParam = new HashMap<String,String>();  
	    requestUrlParam.put("appid", APPID);  //开发者设置中的appId  
	    requestUrlParam.put("secret", SECRET_KEY); //开发者设置中的appSecret  
	    requestUrlParam.put("js_code", code); //小程序调用wx.login返回的code  
	    requestUrlParam.put("grant_type", GRANT_TYPE);    //默认参数  
	    //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识  
	    JSONObject object = JSONObject.fromObject(HttpRequest.sendPost(requestUrl, requestUrlParam));
	    log.info(object.toString());
	    return object;  
	} 
	
	/**
	 * 根据appid和secretkey获取对应的一个accessToken
	 * accessToken不需要重复获取，每次都从一个地方拿就可以了
	 * yaoyz    2018年5月22日ww
	 */
	public static JSONObject getUniqueAccessToken(){
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET_KEY;
		JSONObject ret = HttpRequest.httpRequest(requestUrl, "POST", null);
		log.info(ret.toString());
		return ret;
	}
	
	/**
	 * 检测文本内容是否安全
	 * yaoyz    2018年5月23日
	 */
	public static boolean msgSecCheck(String token, String content){
		String requestUrl = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token="+token;
		JSONObject map = new JSONObject();
		map.put("content", content);		
		String ret = HttpRequest.sendPost(requestUrl, map.toString());
		log.info(ret);
		if(JSONObject.fromObject(ret).getInt("errcode") == 0)
			return true;
		return false;
	}
	

	    
}
