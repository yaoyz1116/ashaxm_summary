package com.ashaxm.personaltools.miniApp;

import java.util.Map;


/**
 * 小程序的服务通知类
 * yaoyz    2018.5.30
 */
public class WxTemplate {
	private String template_id;

	private String touser;

	private String url;

	private String topcolor;
	
	private String formId;
	
	private String page;

	private Map<String,TemplateData> data;

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Map<String, TemplateData> getData() {
		return data;
	}

	public void setData(Map<String, TemplateData> data) {
		this.data = data;
	}
	
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	public String getFormId() {
		return formId;
	}
	
	public void setPage(String page) {
		this.page = page;
	}
	
	public String getPage() {
		return page;
	}
}

