package top.ashaxm.common.utils;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONObject;
//import com.alibaba.fastjson.JSONObject;




/**
 * 获取小程序用户信息的相关操作
 * yaoyz   2018.4.1
 */
public class WXAppletUserInfo {
	//我自己的
	private static String APPID = "wx18011ffeef259d9f";
	private static String SECRET_KEY = "0f92563c469ce593bd64b93d56ac242d";
//	private static String JS_CODE="021ZhtzS04hhhX1B0wAS0w1BzS0Zhtzj";
	private static String GRANT_TYPE="authorization_code";
	private static String OPENID="oBFz30GS_DqdzQWxph8iHLsMP0i0";
 
	//风传吧的
//	private static String APPID = "wxb837942faf0a072e";
//	private static String SECRET_KEY = "61d5526a6987392449c5b871b705bbfd";
//	private static String GRANT_TYPE="authorization_code";
	
	/**
	 * 根据code得到openid和 session_key
	 * yaoyz    2018年4月1日
	 */ 
    public static JSONObject getSessionKeyOropenid(String code){  
        //微信端登录code值  
        String wxCode = code;  
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";  //请求地址 https://api.weixin.qq.com/sns/jscode2session  
        Map<String,String> requestUrlParam = new HashMap<String,String>();  
        requestUrlParam.put("appid", APPID);  //开发者设置中的appId  
        requestUrlParam.put("secret", SECRET_KEY); //开发者设置中的appSecret  
        requestUrlParam.put("js_code", wxCode); //小程序调用wx.login返回的code  
        requestUrlParam.put("grant_type", GRANT_TYPE);    //默认参数  
        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识  
        JSONObject object = JSONObject.fromObject(HttpRequest.sendPost(requestUrl, requestUrlParam));
//        JSONObject jsonObject = JSON.parseObject(HttpRequest.sendPost(requestUrl, requestUrlParam));  
        return object;  
    }   
    
    /**
     * 发送模板消息
     * yaoyz    2018年4月9日
     */
    public static JSONObject sendTemplate(){  
        String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET_KEY;
        JSONObject jsonurl1 = HttpRequest.httpRequest(url1,"GET",null);
    	System.out.println(jsonurl1);
    	String accessToken = jsonurl1.get("access_token").toString();
    	int expiresIn = jsonurl1.getInt("expires_in");
    	System.out.println("accessToken    "+accessToken);
    	System.out.println("expiresIn   "+expiresIn);
    	String url2 = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+accessToken;
    	Map<String,String> url2param = new HashMap<String,String>();  
    	url2param.put("touser", OPENID);   //接受者OPENID
    	url2param.put("template_id", "s77HkHYgYrUiq8v8U9mFHrpVPTCury2IebWkBbb-tQk");
    	url2param.put("form_id", "SENDFORMID");
    	JSONObject jsond = new JSONObject();
    	url2param.put("", "");
    	url2param.put("", ""); 
    	url2param.put("", "");
    	url2param.put("", "");
    	return null;
    } 
    
    
    /**
     * 获取运动步数
     * yaoyz    2018年4月10日
     */
    public static JSONObject loadRunData(String code,String encryptedData, String iv){
    	JSONObject sessionKeyOropenid = getSessionKeyOropenid(code);
    	String openid = sessionKeyOropenid.getString("openid");
    	String sessionKey = sessionKeyOropenid.getString("session_key");
    	System.out.println("openid    "+openid);
    	System.out.println("sessionkey    "+sessionKey);
    	WXBizDataCrypt biz = new WXBizDataCrypt(APPID, sessionKey);     	
        String jsonstr = biz.decryptData(encryptedData, iv);  
        System.out.println("------------------------------jsonstr     -----------------------------");
        JSONObject jsonaim = JSONObject.fromObject(jsonstr);
        System.out.println("-------------jsonaim-------");
        System.out.println(jsonaim);
        System.out.println("-----------end-----------"); 
    	return jsonaim;
    }
    
    public static JSONObject getAccessToken(String code){  
        //微信端登录code值  
        String wxCode = code;  
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";  //请求地址 https://api.weixin.qq.com/sns/jscode2session  
        Map<String,String> requestUrlParam = new HashMap<String,String>();  
        requestUrlParam.put("appid", APPID);  //开发者设置中的appId  
        requestUrlParam.put("secret", SECRET_KEY); //开发者设置中的appSecret  
        requestUrlParam.put("js_code", wxCode); //小程序调用wx.login返回的code  
        requestUrlParam.put("grant_type", GRANT_TYPE);    //默认参数  
        //发送post请求读取调用微信 https://api.weixin.qq.com/sns/jscode2session 接口获取openid用户唯一标识  
        JSONObject object = JSONObject.fromObject(HttpRequest.sendPost(requestUrl, requestUrlParam));
//        JSONObject jsonObject = JSON.parseObject(HttpRequest.sendPost(requestUrl, requestUrlParam));  
        return object;  
    } 
    
    
    public static JSONObject dynamicCreateQRcode(){
    	System.out.println("------------------start---load  QRCODE--------------------------");
    	String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET_KEY;
        JSONObject jsonurl1 = HttpRequest.httpRequest(url1,"GET",null);
    	System.out.println(jsonurl1);
    	String accessToken = jsonurl1.get("access_token").toString();
    	int expiresIn = jsonurl1.getInt("expires_in");
    	System.out.println("accessToken ------------"+accessToken);
    	System.out.println("expiresIn----------------"+expiresIn);
//    	String requestUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;  
    	String requestUrl="https://api.weixin.qq.com/wxa/getwxacode?access_token="+accessToken;
    	System.out.println(requestUrl);
    	
//    	String params = "";
//    	params += "{'scene':'name=yaoyz','page':'/pages/index/index'}";;
//    	String retData = HttpRequest.sendPost(requestUrl, params);
//    	System.out.println(retData);
    	
//        Map<String,String> requestUrlParam = new HashMap<String,String>();  
//        requestUrlParam.put("scene", "name=yaoyz&age=24&city=hefei"); 
//        requestUrlParam.put("page", "/pages/index/index"); 
//        JSONObject object = JSONObject.fromObject(HttpRequest.sendPost(requestUrl, requestUrlParam));
//    	System.out.println(object);
    	System.out.println("-------------------END   LOAD QRCODE----------------------------");
    	
    	return null;
    }
    
    
    
    
    
    public static void main(String[] args) {
//    	String code="061cHCzt1lXgPb0gAlyt1IJKzt1cHCzc";
//    	JSONObject obj = getSessionKeyOropenid(code);
//    	System.out.println(obj.get("openid")+"______________"+obj.get("session_key"));
//    	System.out.println(obj);
    	
    	
//    	String code="021E89P82xsutQ0tmVO828o3P82E89P8";
//    	String encryptedData="k5ExPZNOxz9lrFzzyK6D9czzCSMPhTwnj90RYDq89pax8zG84IIXLZRTtiM1w3kkW7MA0w9r6WULqX728k9uSC3dZ9cvVrRE/GlUbJEQQgcv/9h3EuEHb58VH0qOi4njiS3448EDbsKYo2PetBMPFq96r4ENZf1/kMo9xl2Pm732IB5mlQKo4Adx8oIkGbZLSvc8ZLgaxLs7J+rYIXrW0nbpcbQhPk5Eb5qHnquHPbdRngCa1cjP5ZukuFHWcXjM3wg0W7djYcjp8pBnPK1l4tp6LHD/zjqUNe0uDgmmCYMN+wf2UzgDp5XEz/dCJNJiw0t+gwatlwij7gGrCzt68vqxwGgr6rG/y0YzD2yeJbzv25PJrC6Ei0vzJJy2lru2vJBEHGQqtGWl7TVsiFjPbJOsfA78fI+5cA/IRbmW6f2p0JCGfDpuzIzdY9OPLoQZYVOsmaHKUXbL6KO6ee23UKaKWCE2T27p8Eigdixoq0DBLJ15asZL+pqPKmGJXgOPeN18Wxq6Q+IkwTdKYO10rc5UmvPunOthsGbjHjSpAZoKG8Lo9ZlNmRaUKhdEi5C6Ae7BYtVa43veIkQUZ4u2FSVWwZoMWMf5YllyuV3ve902wmtwEQ02AJFa3MkzB3SDmIjdVhFOK48JjoAkk9DaxuRqfW12s6rUu5/B5JedSSFzhhCQGMqPO7aMss+zRe9SqgPIxVo1tj/5sSaqJh9a10ufuFGrNcb0nV/HHQvd0LMYlC026QwJbRKuD6eLZy2IcvAYZnwDmulY0MKCWZQEkfoT/1AUT0d8lGLYLYjR3mIXaPUKD4HmortZIJBACkhdtjAuZY/bp1AP4FSz1qeKQJPXZsi6HSjUKdAYV0VBZ7pOEXLStBX6uBbFEPS1T+W71YQpPk/kbYDbzOe/S5qAOZojoR++WT1+v9/v2owrOT8yBO6/i+7S0aC21DJU5bSVm7BGg+dQhsd9cgeZYGV/KFr4ncgTdz6/cJ+qJpnLFqQDePpknAnElOQyqweKsVrWdh2nn6UCgKAx6/hHKrKSYm2EMdWRrpQzch8bp3Os4QDKnf7nag8NC1GZW6CDnhYvto7fjjO4IE1KfIY2oIiWa/+ob2HRNENax9ngQEsreuD7KeUk7hrrfB/QvuJ6vsibXxaz1LbX5k9vh4LPdcdJOEzw+JrBgY19N9L5OHfrQetLK+b9bwFsSTYKptweJcgdcC7jQjMUYcAsChEHoU6kdwO1Bwrq4UOg0xFqFD44pnTM4kxrXvfhgwbv1UJPEEvLq1htF7PRI/xhPvREQlz/GaMJeJof1vAwz/Z9m9ukVxqkjHq5vekkM2rlJHPO1ZdTChnAI9gQUMV4nXHh0Vv39tjMkOIs909fxN4F//e2OzSx72egchSro48MfI5e11YHMPkVeRMMwFfvO3F9If7m58HWTMxEijrzoNStVeTXJh3acCdCZwK/Pi6ZFXNOpsFMiDfmFZoKMfDkmg5aqboU5xsbCAZveOCdsr1swKppWbfzCk4+GROjJEEzQKvGxA5Qux5yaybQPE3oMy+zptk3jXol+3WL/DObc0R2ud2C0h06CSXngeaaR2MvVLsoielhHdKq9ppeatKo+93OWRt+or8lhv6x0fhf1F4sYPM6c33BQ1Py5Gjauc04J3QzyhAL";
//    	String iv = "fb/PtEhATFPdHX/g3FQNgw==";
//    	loadRunData(code, encryptedData, iv);
        
//       dynamicCreateQRcode();
       
       try
       {
    	   System.out.println("------------------start---load  QRCODE--------------------------");
	       	String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET_KEY;
	           JSONObject jsonurl1 = HttpRequest.httpRequest(url1,"GET",null);
	       	System.out.println(jsonurl1);
	       	String accessToken = jsonurl1.get("access_token").toString();
	       	int expiresIn = jsonurl1.getInt("expires_in");
	       	System.out.println("accessToken ------------"+accessToken);
	       	System.out.println("expiresIn----------------"+expiresIn);
	//       	String requestUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;  
	       	String requestUrl="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
	       	System.out.println(requestUrl);
           URL url = new URL(requestUrl);
           HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
           httpURLConnection.setRequestMethod("POST");// 提交模式
           // conn.setConnectTimeout(10000);//连接超时 单位毫秒
           // conn.setReadTimeout(2000);//读取超时 单位毫秒
           // 发送POST请求必须设置如下两行
           httpURLConnection.setDoOutput(true);
           httpURLConnection.setDoInput(true);
           // 获取URLConnection对象对应的输出流
           PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
           // 发送请求参数
           JSONObject paramJson = new JSONObject();
           paramJson.put("scene", "a=1234567890");
           paramJson.put("page", "pages/logs/logs");
           paramJson.put("width", 430);
           paramJson.put("auto_color", true);
           /**
            * line_color生效
            * paramJson.put("auto_color", false);
            * JSONObject lineColor = new JSONObject();
            * lineColor.put("r", 0);
            * lineColor.put("g", 0);
            * lineColor.put("b", 0);
            * paramJson.put("line_color", lineColor);
            * */

           printWriter.write(paramJson.toString());
           // flush输出流的缓冲
           printWriter.flush();
           //开始获取数据
           BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
           OutputStream os = new FileOutputStream(new File("H:/logs.png"));
           int len;
           byte[] arr = new byte[1024];
           while ((len = bis.read(arr)) != -1)
           {
               os.write(arr, 0, len);
               os.flush();
           }
           os.close();
           System.out.println("success");
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }
       
       
//       try {
//		System.out.println("------------------start---load  QRCODE--------------------------");
//		  	String url1 = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET_KEY;
//		      JSONObject jsonurl1 = HttpRequest.httpRequest(url1,"GET",null);
//		  	System.out.println(jsonurl1);
//		  	String accessToken = jsonurl1.get("access_token").toString();
//		  	int expiresIn = jsonurl1.getInt("expires_in");
//		  	System.out.println("accessToken ------------"+accessToken);
//		  	System.out.println("expiresIn----------------"+expiresIn);
////       	String requestUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;  
//		  	String requestUrl="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
//		       Map<String, Object> params = new HashMap<>();
//		       params.put("scene", "test");
//		       params.put("page", "pages/index/index");
//		       params.put("width", 430);
//
//		       CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
//
//		       HttpPost httpPost = new HttpPost(requestUrl);
//		       httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
//		       String body = JSON.toJSONString(params);
//		       StringEntity entity;
//		       entity = new StringEntity(body);
//		       entity.setContentType("image/png");
//
//		       httpPost.setEntity(entity);
//		       HttpResponse response;
//
//		       response = httpClient.execute(httpPost);
//		       
//		       
//		       
//		       InputStream content = response.getEntity().getContent();
//		       
//		            
//		       FileOutputStream out = new FileOutputStream("H://aaaaa.txt");
//
//		       byte[] buffer = new byte[8192];
//		       int bytesRead = 0;
//		       while((bytesRead = content.read(buffer, 0, 8192)) != -1) {
//		           out.write(buffer, 0, bytesRead);
//		       }
//
//		       out.flush();
//		       out.close();
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//       System.out.println("success"); 
	}
  
}
