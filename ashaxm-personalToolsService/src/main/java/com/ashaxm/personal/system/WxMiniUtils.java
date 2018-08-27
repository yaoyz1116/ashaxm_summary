package com.ashaxm.personal.system;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * 微信小程序的操作类
 * yaoyz    2018.6.23
 */
public class WxMiniUtils {

	protected final static Logger log = LoggerFactory.getLogger(WxMiniUtils.class);
	 
	//个人效率助手的
	private static String APPID = "wx18011ffeef259d9f";
	private static String SECRET_KEY = "0f92563c469ce593bd64b93d56ac242d";
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
	 * 发送服务通知消息
	 * yaoyz    2018年5月23日
	 */
//	public static JSONObject sendTemplateMsg(String token,WxTemplate t){
//		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+token;		
//		JSONObject map = new JSONObject();
//		map.put("touser", t.getTouser());
//		map.put("template_id", t.getTemplate_id());
//		map.put("form_id", t.getFormId());
//		map.put("page", t.getPage());
//		map.put("data", t.getData());	
//		String ret = HttpRequest.sendPost(requestUrl, map.toString());		
//		System.out.println(ret);
//		return JSONObject.fromObject(ret);
//	}

	/**
	 * 检测文本内容是否安全
	 * yaoyz    2018年5月23日
	 */
//	public static boolean msgSecCheck(String token, String content){
//		String requestUrl = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token="+token;
//		JSONObject map = new JSONObject();
//		map.put("content", content);		
//		String ret = HttpRequest.sendPost(requestUrl, map.toString());
//		log.info(ret);
//		if(JSONObject.fromObject(ret).getInt("errcode") == 0)
//			return true;
//		return false;
//	}
	
	/**
	 * 获取运动步数
	 * yaoyz    2018年4月10日
	 */
	//	    public static JSONObject loadRunData(String code,String encryptedData, String iv){
	//	    	JSONObject sessionKeyOropenid = getSessionKeyOropenid(code);
	//	    	String openid = sessionKeyOropenid.getString("openid");
	//	    	String sessionKey = sessionKeyOropenid.getString("session_key");
	//	    	System.out.println("openid    "+openid);
	//	    	System.out.println("sessionkey    "+sessionKey);
	//	    	WXBizDataCrypt biz = new WXBizDataCrypt(APPID, sessionKey);     	
	//	        String jsonstr = biz.decryptData(encryptedData, iv);  
	//	        System.out.println("------------------------------jsonstr     -----------------------------");
	//	        JSONObject jsonaim = JSONObject.fromObject(jsonstr);
	//	        System.out.println("-------------jsonaim-------");
	//	        System.out.println(jsonaim);
	//	        System.out.println("-----------end-----------"); 
	//	    	return jsonaim;
	//	    }
	
	
	
	
	
	public static JSONObject dynamicCreateQRcode(){
		System.out.println("------------------start---load  QRCODE--------------------------");
		String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET_KEY;
	    JSONObject jsonurl1 = HttpRequest.httpRequest(url1,"GET",null);
		System.out.println(jsonurl1);
		String accessToken = jsonurl1.get("access_token").toString();
		int expiresIn = jsonurl1.getInt("expires_in");
		System.out.println("accessToken ------------"+accessToken);
		System.out.println("expiresIn----------------"+expiresIn);
	//	    	String requestUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;  
		String requestUrl="https://api.weixin.qq.com/wxa/getwxacode?access_token="+accessToken;
		System.out.println(requestUrl);
		
	//	    	String params = "";
	//	    	params += "{'scene':'name=yaoyz','page':'/pages/index/index'}";;
	//	    	String retData = HttpRequest.sendPost(requestUrl, params);
	//	    	System.out.println(retData);
		
	//	        Map<String,String> requestUrlParam = new HashMap<String,String>();  
	//	        requestUrlParam.put("scene", "name=yaoyz&age=24&city=hefei"); 
	//	        requestUrlParam.put("page", "/pages/index/index"); 
	//	        JSONObject object = JSONObject.fromObject(HttpRequest.sendPost(requestUrl, requestUrlParam));
	//	    	System.out.println(object);
		System.out.println("-------------------END   LOAD QRCODE----------------------------");
		
		return null;
	}
	
	
	public static void main(String[] args) {
//		String token="10_ho8_dX0VFHqaxsauTxCVYwZN43vvc1AIBL6OqxLGcJMLy7reCmN6tATK7W2n4JzGAzqPX4QAKEDhmud54NIuwcfXpxywnZuXIq5HDz008y8Pm6CsbIu1EU47HjxYJvFK9VKYlmrAC43i1lgWKLLiABAPQQ";
//		String content="这是一句脏话习平";
//		if(msgSecCheck(token, content)){
//			System.out.println("ok");
//		}else{
//			System.out.println("eroor");
//		}
		
		
//		String code = "071Hexz51bNFzL1hVZz51GIBz51Hexzg";
//		getAccessToken(code);
//		getUniqueAccessToken();
//		String token = "10_ZdkBVpVBBY7icsQXwluNgRUXXSaA6pW1zDWwjej4ltkr2cPBYi7EEsXu9dUgf8LOmFMIiUDZaUgtP_cjYfBeX4NSKkkFhaZQU_210vgwRE2WiUz87S0SG8NkzmE8TcwudQMHhOdFGE9W50X6FYZdAGAOHI";
//		String openId = "opTul5G9G0D--79DzqZt6R-66Rco";
//		String templateId = "tVYJgKpzybyWADbqxQXagkUJQg9PFbuX3Xv9K4zyNBA";
		
//		String page = "/pages/debateDetail/debateDetail?debateId=1";
//		String formId = "217919fbb6b6baf949ec59c70739289f";
//		WxTemplate t = new WxTemplate();
//		t.setTouser(openId);
//		t.setTemplate_id(templateId);
//		t.setFormId(formId);
//		t.setPage(page);
//		Map<String,TemplateData> m = new HashMap<String,TemplateData>();
//		TemplateData keyword1 = new TemplateData();
//		keyword1.setValue("title1111");
//		m.put("keyword1", keyword1);
//		TemplateData keyword2 = new TemplateData();
//		keyword2.setValue("name11111");
//		m.put("keyword2", keyword2);
//		TemplateData keyword3 = new TemplateData();
//		keyword3.setValue("remark1111");
//		m.put("keyword3", keyword3);
//		TemplateData keyword4 = new TemplateData();
//		keyword4.setValue("remark2222");
//		m.put("keyword4", keyword4);		
//		TemplateData keyword5 = new TemplateData();
//		keyword5.setValue("remark3333");
//		m.put("keyword5", keyword5);
//		t.setData(m);		
//		sendTemplateMsg(token, t);
	}
	    
}
