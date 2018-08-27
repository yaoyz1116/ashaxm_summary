package com.ashaxm.laixuexi.modal;

/**
 * 人员表
 * yaoyz 2018.5.17
 */
public class Visitor {

	private long id;
	private long adminId;
	private String name;
	private String sex;
	private String parent;
	private String phone; 
	private int age;
	private int cost;
	private int time;
	private int studyTime;
	private int spareTime;
	private String addtime;
	private int danceId;
	private int classId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}  
	public void setStudyTime(int studyTime) {
		this.studyTime = studyTime;
	}
	public int getStudyTime() {
		return studyTime;
	}
	public int getSpareTime() {
		return spareTime;
	}
	public void setSpareTime(int spareTime) {
		this.spareTime = spareTime;
	}
	public long getAdminId() {
		return adminId;
	}
	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	public int getDanceId() {
		return danceId;
	}
	public void setDanceId(int danceId) {
		this.danceId = danceId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getAge() {
		return age;
	}
	
	
}
