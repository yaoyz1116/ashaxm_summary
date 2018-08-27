package com.ashaxm.laixuexi.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 在tools.java中嗲用，配合网络请求使用
 * yaoyz  2018.6.17
 */
public class MyX509TrustManager implements X509TrustManager{	
	
	/**
	 * 该方法体为空时信任所有客户端证书(检查客户端证书)
	 */
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {	
	}
	
	/**
	 * 该方法体为空时信任所有服务器证书 (检查服务器证书)
	 */
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {	
	}
	
	/**返回信任的证书
	 * @return   
	 */
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}