package top.ashaxm.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;


import net.sf.json.JSONObject;

/**
 * http网络请求，发送get和post服务
 * @author yaoyz
 * 2018年1月8日
 */
public class HttpRequest {
	
	/**
	 * 向指定的URL发送get请求
	 * param的格式为name1=value1&name2=value2
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = "";
			if(param!=null&& param.length()>0)
				urlNameString = url + "?" + param;
			else
				urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();

			// 获取所有响应头字段
			// Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			// for (String key : map.keySet()) {
			// System.out.println(key + "--->" + map.get(key));
			// }

			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定的URL发送POST请求
	 * param的格式为name1=value1&name2=value2
	 * @author yaoyz
	 * 2018年1月8日
	 */
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
			in = //new BufferedReader(new InputStreamReader(conn.getInputStream()));
				new BufferedReader(new InputStreamReader(conn.getInputStream(), "gb2312"));
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
	
	
	
	/**
	 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP,
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public static String getClientIP(HttpServletRequest request) {
		// String fromSource = "X-Real-IP";
		String ip = request.getHeader("X-Real-IP");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			// fromSource = "X-Forwarded-For";
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			// fromSource = "Proxy-Client-IP";
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			// fromSource = "WL-Proxy-Client-IP";
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			// fromSource = "request.getRemoteAddr";
		}
		// log.info("App Client IP: "+ip+", fromSource: "+fromSource);
		return ip;
	}

	/**
	 * 发起https请求并获取结果
	 *
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param param
	 *            提交的数据
	 * @return JSONObject (通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String param) {
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
			if (null != param) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(param.getBytes("UTF-8"));
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
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	
	public static String sendPost(String url, Map<String, ?> paramMap) {  
	      PrintWriter out = null;  
	      BufferedReader in = null;  
	      String result = "";  
	        
	      String param = "";  
		  Iterator<String> it = paramMap.keySet().iterator();    
		  while(it.hasNext()) {  
			  String key = it.next();  
			  param += key + "=" + paramMap.get(key) + "&";  
		  }  
	  
	      try {  
	          URL realUrl = new URL(url);  
	          // 打开和URL之间的连接  
	          URLConnection conn = realUrl.openConnection();  
	          // 设置通用的请求属性  
	          conn.setRequestProperty("accept", "*/*");  
	          conn.setRequestProperty("connection", "Keep-Alive");  
	          conn.setRequestProperty("Accept-Charset", "utf-8");  
	          conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
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
	          in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));  
	          String line;  
	          while ((line = in.readLine()) != null) {  
	              result += line;  
	          }  
	      } catch (Exception e) {  
	        e.printStackTrace();
	      }  
	      //使用finally块来关闭输出流、输入流  
	      finally{  
	          try{  
	              if(out!=null){  
	                  out.close();  
	              }  
	              if(in!=null){  
	                  in.close();  
	              }  
	          }  
	          catch(IOException ex){  
	              ex.printStackTrace();  
	          }  
	      }  
	      return result;  
	  } 
	
	
	public static void main(String[] args){
		System.out.println("succeess");
		String server = "https://api.weixin.qq.com/sns/jscode2session";
		String getBalance = "/GetBalance";
		String sendMessage = "/SendMessage";
		String param = "Id=1380&Name=daikebao&Psw=jkhd888888";
		
		String Message = "";
		try {
			Message = URLEncoder.encode("测试", "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String param2 = "Id=1380&Name=daikebao&Psw=jkhd888888&Message=恭喜您中奖，获得当当电子礼品卡（面值200元），卡号为：ddb635888z97zz4375zz，兑换密码为：2345，感谢您参与建行的有奖活动！&Phone=18501622885&Timestamp=0";
		//getbalance         "Id=1380&Name=daikebao&Psw=jkhd888888"
		//SendMessage		 Id=string&Name=string&Psw=string&Message=string&Phone=string&Timestamp=string"
		//System.out.println(HttpRequest.sendGet(server + getBalance, param));
		System.out.println(HttpRequest.sendGet(server + sendMessage, param2));
	}
}