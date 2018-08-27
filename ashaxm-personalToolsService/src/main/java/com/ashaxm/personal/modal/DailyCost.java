package com.ashaxm.personal.modal;

/**
 * 日常开销表
 * yaoyz    2018.6.23
 */
public class DailyCost {
	
	private long id;
	private long userId;
	private long groupId;
	private String costDate;
	private String costWeek;
	private String costPrice;
	private long status;
	private String addTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getCostDate() {
		return costDate;
	}
	public void setCostDate(String costDate) {
		this.costDate = costDate;
	}
	public String getCostWeek() {
		return costWeek;
	}
	public void setCostWeek(String costWeek) {
		this.costWeek = costWeek;
	}
	public String getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
	
}
