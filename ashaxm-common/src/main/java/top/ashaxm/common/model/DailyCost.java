package top.ashaxm.common.model;

/**
 * 日常开销表对应的实体
 * 2018.3.31
 */
public class DailyCost {

	private Long id;
	private Long userid;
	private Long groupid;
	private String costdate;
	private String costweek;
	private String costprice;
	private String addtime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public Long getGroupid() {
		return groupid;
	}
	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}
	public String getCostdate() {
		return costdate;
	}
	public void setCostdate(String costdate) {
		this.costdate = costdate;
	}
	public String getCostweek() {
		return costweek;
	}
	public void setCostweek(String costweek) {
		this.costweek = costweek;
	}
	public String getCostprice() {
		return costprice;
	}
	public void setCostprice(String costprice) {
		this.costprice = costprice;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	
}
