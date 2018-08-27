package com.ashaxm.personal.system;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import net.sf.json.JSONObject;

public class HttpRequest {
	
	/**
	 * ��ָ��URL����GET����������
	 * @param url  ���������URL
	 * @param param   ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	 * @return URL ������Զ����Դ����Ӧ���
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
			// �򿪺�URL֮�������
			URLConnection connection = realUrl.openConnection();
			// ����ͨ�õ���������
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// ����ʵ�ʵ�����
			connection.connect();

			// ���� BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("����GET��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر�������
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
	 * ��ָ�� URL ����POST����������
	 * @param url  ��������� URL
	 * @param param  ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	 * @return ������Զ����Դ����Ӧ���
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// �򿪺�URL֮�������
			URLConnection conn = realUrl.openConnection();
			// ����ͨ�õ���������
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// ����POST�������������������
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// ��ȡURLConnection�����Ӧ�������
			out = new PrintWriter(conn.getOutputStream());
			// �����������
			out.print(param);
			// flush������Ļ���
			out.flush();
			// ����BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "gb2312"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("���� POST ��������쳣��" + e);
			e.printStackTrace();
		}
		// ʹ��finally�����ر��������������
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
	 * ��ָ�� URL ����POST����������
	 * @param url  ��������� URL
	 * @param paramMap  map��ʽ�Ĳ���
	 * @return ������Զ����Դ����Ӧ���
	 */
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
	          // �򿪺�URL֮�������  
	          URLConnection conn = realUrl.openConnection();  
	          // ����ͨ�õ���������  
	          conn.setRequestProperty("accept", "*/*");  
	          conn.setRequestProperty("connection", "Keep-Alive");  
	          conn.setRequestProperty("Accept-Charset", "utf-8");  
	          conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
	          // ����POST�������������������  
	          conn.setDoOutput(true);  
	          conn.setDoInput(true);  
	          // ��ȡURLConnection�����Ӧ�������  
	          out = new PrintWriter(conn.getOutputStream());  
	          // �����������  
	          out.print(param);  
	          // flush������Ļ���  
	          out.flush();  
	          // ����BufferedReader����������ȡURL����Ӧ  
	          in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));  
	          String line;  
	          while ((line = in.readLine()) != null) {  
	              result += line;  
	          }  
	      } catch (Exception e) {  
	        e.printStackTrace();
	      }  
	      //ʹ��finally�����ر��������������  
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
	
	/**
	 * �������󣬷���json����
	 * yaoyz    2018��5��21��
	 */
	public static JSONObject httpRequest(String requestUrl,String requestMethod, String param) {
		JSONObject jsonObject = null;

		try {
			// ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��(֤�����)
			TrustManager[] tm = { new MyX509TrustManager() };
			// ȡ��SSL��SSLContextʵ��
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			// ��ʼ��SSLContext
			sslContext.init(null, tm, new java.security.SecureRandom());
			// ������SSLContext�����еõ�SSLSocketFactory����
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// ��������ʽ��GET/POST��
			httpUrlConn.setRequestMethod(requestMethod);

			
			if ("GET".equalsIgnoreCase(requestMethod)) httpUrlConn.connect();
			
			// ����������Ҫ�ύʱ(��outputStr��Ϊnullʱ���������д����)
			if (null != param) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// ע������ʽ����ֹ��������
				outputStream.write(param.getBytes("UTF-8"));
				outputStream.close();
			}

			// �����ص�������ת�����ַ���
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

			// �ͷ���Դ
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
	
	/**
	 *  ����post����ʹ��json����
	 *  ֻ����Ի�ȡС�����ά��ʱʹ��
	 */
	public static void sendPostJSON(String requesturl,JSONObject paramJson,String filepath) {  
	   try{	    	  
           URL url = new URL(requesturl);
           HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
           httpURLConnection.setRequestMethod("POST");// �ύģʽ
           // conn.setConnectTimeout(10000);//���ӳ�ʱ ��λ����
           // conn.setReadTimeout(2000);//��ȡ��ʱ ��λ����
           // ����POST�������������������
           httpURLConnection.setDoOutput(true);
           httpURLConnection.setDoInput(true);
           // ��ȡURLConnection�����Ӧ�������
           PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
           printWriter.write(paramJson.toString());
           // flush������Ļ���
           printWriter.flush();
           //��ʼ��ȡ����
           BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
           OutputStream os = new FileOutputStream(new File(filepath));
           int len;
           byte[] arr = new byte[1024];
           while ((len = bis.read(arr)) != -1)
           {
               os.write(arr, 0, len);
               os.flush();
           }
           os.close();
       }catch (Exception e) {
           e.printStackTrace();
       }
	} 
	
	
	
	public static void main(String[] args){
//		String server = "http://sms.gknet.com.cn:8180/Service.asmx";
//		String getBalance = "/GetBalance";
//		String sendMessage = "/SendMessage";
//		String param = "Id=1380&Name=daikebao&Psw=jkhd888888";
//		
//		String Message = "";
//		try {
//			Message = URLEncoder.encode("����", "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String param2 = "Id=1380&Name=daikebao&Psw=jkhd888888&Message=��ϲ���н�����õ���������Ʒ������ֵ200Ԫ��������Ϊ��ddb635888z97zz4375zz���һ�����Ϊ��2345����л�����뽨�е��н����&Phone=18501622885&Timestamp=0";
//		//getbalance         "Id=1380&Name=daikebao&Psw=jkhd888888"
//		//SendMessage		 Id=string&Name=string&Psw=string&Message=string&Phone=string&Timestamp=string"
//		//System.out.println(HttpRequest.sendGet(server + getBalance, param));
//		System.out.println(HttpRequest.sendGet(server + sendMessage, param2));
	}
}
