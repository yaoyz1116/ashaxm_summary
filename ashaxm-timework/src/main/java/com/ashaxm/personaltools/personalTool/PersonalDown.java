package com.ashaxm.personaltools.personalTool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

import com.ashaxm.personaltools.common.C3P0Utils;
import com.ashaxm.personaltools.common.MessageUtils;
import com.ashaxm.personaltools.miniApp.TemplateData;
import com.ashaxm.personaltools.miniApp.WxMiniUtil;
import com.ashaxm.personaltools.miniApp.WxTemplate;

import net.sf.json.JSONObject;

/**
 * 个人效率助手中扫描备忘录
 */
public class PersonalDown {
	
	private static Logger log = Logger.getLogger(PersonalDown.class);
	
	private static String template_id = "DEzNHvIjCQhgR13-8soJNijHx-6Q5QpgKYal0W2yqTo";


	public static void scan() {
		log.info("--------------start-scan-----------");
		List<Map<String, Object>> nlist = loadUndoNotice();
		log.info("need send notice size-----"+nlist.size());
		for(Map<String, Object> map : nlist){
			sendMsg(map.get("openid").toString(), map.get("formid").toString(), map.get("title").toString(),
					map.get("des").toString(), map.get("noticetime").toString());
			if("oBFz30DuHJglW8wZn7eYC7urIBzE".equals(map.get("openid").toString())){
				//针对丁慧敏的openid发送短信通知
				MessageUtils.sendMsg(map.get("title").toString(), map.get("noticetime").toString().substring(5, 16), map.get("des").toString(), "15221656208");
			}
			updateNotice(map.get("id").toString());
		}
		log.info("--------------end-scan-----------");
	}
	
	/**
	 * 查询需要发送服务通知的备忘
	 * yaoyz    2018年6月29日
	 */
	public static List<Map<String, Object>> loadUndoNotice(){
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		List<Map<String, Object>> nlist = new ArrayList<>();
		try {
			String sql ="SELECT n.id,n.formid,n.title,n.des,DATE_FORMAT(n.noticetime,'%Y-%m-%d %T') as noticetime,n.status,u.openid FROM notice AS n LEFT JOIN user AS u ON n.userid=u.id WHERE n.status=0 AND n.noticetime<NOW()";
			nlist = qr.query(sql, new MapListHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nlist;
	}
	
	/**
	 * 发送服务通知
	 * yaoyz    2018年6月29日
	 */
	public static void sendMsg(String openId, String formId,String title,String des,String noticetime){
		String page = "pages/index/index";
		WxTemplate t = new WxTemplate();
		t.setTouser(openId);
		t.setTemplate_id(template_id);
		t.setFormId(formId);
		t.setPage(page);
		Map<String,TemplateData> m = new HashMap<String,TemplateData>();
		TemplateData keyword1 = new TemplateData();
		keyword1.setValue(title);
		m.put("keyword1", keyword1);
		TemplateData keyword2 = new TemplateData();
		keyword2.setValue(noticetime);
		m.put("keyword2", keyword2);
		TemplateData keyword3 = new TemplateData();
		keyword3.setValue(des);
		m.put("keyword3", keyword3);	
		t.setData(m);		
		JSONObject ret = WxMiniUtil.sendTemplateMsg(t);
		log.info(ret);
	}
	
	/**
	 * 修改状态
	 * yaoyz    2018年6月29日
	 */
	public static void updateNotice(String id){
		QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "update notice set status=1 where id="+id;
		try {
			qr.update(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
