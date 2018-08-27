package com.ashaxm.personaltools.miniApp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import net.sf.json.JSONObject;


public class WxProperty {
	private static Log log = LogFactory.getLog(WxProperty.class);

	private static Properties props = null;

	private static AccessToken accessToken = new AccessToken();
	
	private static String miniapp_properti = "/opt/webapps/ashaxm-timetask/config/miniapp.properties";
//	private static String miniapp_properti = "E:/maven/temp/mini.properties";
	
	/**
	 * 程序加载的时候，初始化
	 * yaoyz    2018年5月22日
	 */
	public static void init() {

		// 从配置文件获取基础信息，这些微信的认证信息需要定期更新
		// 目前是7200秒一次
		Resource resource = getWxResource();
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);

			WxProperty.accessToken.setAccesstoken(props.getProperty("access_token"));
			int access_expires_in = 0;
			try {
				access_expires_in = Integer.parseInt(props.getProperty("access_expires_in", "7200"));
			} catch (NumberFormatException nfe) {
				access_expires_in = 0;
			}
			WxProperty.accessToken.setExpiresin(access_expires_in);

			int access_timestamp = 0;
			try {
				access_timestamp = Integer.parseInt(props.getProperty("access_timestamp", "0"));
			} catch (NumberFormatException nfe) {
				access_timestamp = 0;
			}
			WxProperty.accessToken.setTimestamp(access_timestamp);
			log.info("access_token----"+WxProperty.accessToken.getAccesstoken());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存内容到配置文件
	 * yaoyz    2018年5月22日
	 */
	public static void saveAccesstoken() {
		props.setProperty("access_token", accessToken.getAccesstoken());
		props.setProperty("access_expires_in", accessToken.getExpiresin() + "");
		props.setProperty("access_timestamp", accessToken.getTimestamp() + "");

		Resource resource = getWxResource();
		OutputStream fos;
		try {
			String filepath = resource.getFile().getAbsolutePath();
			fos = new FileOutputStream(filepath);
			props.store(fos, "Update AccessToken");
			if (fos != null) {
				fos.close();
			}
			log.info("accessToken save success    ");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 所有需要用到accessToken的地方都直接从这里拿
	 * 首先判断accessToken是否失效，失效就重新从微信获取，并将本次获取结果放在配置文件中
	 * yaoyz    2018年5月22日
	 */
	public static AccessToken getAccessToken(){
		if(!accessToken.isValid()){
			//当前的这个accessToken不可以使用了
			JSONObject retJson = WxMiniUtil.getUniqueAccessToken();
			String access_token = retJson.get("access_token").toString();
			int expires_in = Integer.parseInt(retJson.get("expires_in").toString());
			log.info("new accessToken-----"+access_token+"-----"+expires_in);
			accessToken.setAccesstoken(access_token);
			accessToken.setExpiresin(expires_in);
			accessToken.setTimestamp(System.currentTimeMillis() / 1000);
			saveAccesstoken();
		}     
		return accessToken;
	}
	
	/**
	 * 获取微信的配置文件
	 * yaoyz    2018年5月22日
	 */
	private static Resource getWxResource(){
		Resource resource = new FileSystemResource(miniapp_properti);
		return resource;
	}
		
}
