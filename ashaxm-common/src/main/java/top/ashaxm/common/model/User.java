package top.ashaxm.common.model;

/**
 * 用户的基本信息
 * @author yaoyz
 * 2018年1月8日
 */
public class User {
	private long id;    //编号
	private long groupid;  //租号
	private String username;  //标识符
	private String phone; //电话
	private String registertime;  //注册时间
	private String openid;  //微信唯一标识
	private String avatarUrl;   //头像
	private String nickName; //姓名
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegistertime() {
		return registertime;
	}
	public void setRegistertime(String registertime) {
		this.registertime = registertime;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}
	public long getGroupid() {
		return groupid;
	}
	
}
