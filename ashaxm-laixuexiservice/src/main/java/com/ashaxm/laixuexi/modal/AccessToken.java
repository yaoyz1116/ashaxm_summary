package com.ashaxm.laixuexi.modal;


/**
 * 获取微信的accessToken,当程序启动的时候，创建此对象
 * 首先从配置文件中读取参数，如果配置文件中的参数无效，则去微信请求信息
 * 此对象作为静态对象，始终存在内存中
 */
public class AccessToken {
	// 获取到的凭证
	private String accesstoken=null;
	// 凭证有效时间，单位：秒  有效期为7200秒
	private int expiresin=0;
	private long timestamp=0;	//秒
	
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public int getExpiresin() {
		return expiresin;
	}
	public void setExpiresin(int expiresin) {
		this.expiresin = expiresin;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean isExpired(){
		//如果当前时间-获取时间的差值，比过期的描述（-180，预留3分钟）还大，则说明快过期了
		long diff  = System.currentTimeMillis() / 1000 - timestamp; 
		System.out.println("diff-----"+diff);
		System.out.println("expiresin----"+expiresin);
		return diff > expiresin -180;
	}
	
	public boolean isValid(){
		System.out.println("-------check-----------start----------");
		System.out.println(this.accesstoken);
		if(this.accesstoken!=null&&this.accesstoken.trim().length()>10){
			return !this.isExpired();
		}
		System.out.println("-----check---------end---------");
		return false;
	}
	
	public void copy(AccessToken accessToken){
		this.accesstoken = accessToken.accesstoken;
		this.expiresin = accessToken.expiresin;
		this.timestamp = accessToken.timestamp;
	}
	
	public String toString(){
		return " AccessToken:" + this.accesstoken
				+ " Expiresin:" + this.expiresin
				+ " Timestamp:" + this.timestamp;
	}
}
