package com.ashaxm.personal.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ashaxm.personal.service.NoticeService;

/**
 * 提醒事件
 */
@RestController
public class NoticeController {
	
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 增加新提醒
	 * yaoyz    2018年6月27日
	 */
	@RequestMapping(value = "/notice/addNotice", method = RequestMethod.POST)
	public Map<String, Integer> addNotice(Long userId,String formId, String title, String desc, String noticeTime) {
		Map<String, Integer> retMap = new HashMap<String, Integer>();
		retMap.put("status", 0);
		noticeService.addNotice(userId,formId,title,desc,noticeTime);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 删除提醒
	 * yaoyz    2018年6月27日
	 */
	@RequestMapping(value = "/notice/deleteNotice", method = RequestMethod.POST)
	public Map<String, Integer> deleteNotice(Long noticeId) {
		Map<String, Integer> retMap = new HashMap<String, Integer>();
		retMap.put("status", 0);
		noticeService.deleteNotice(noticeId);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 修改提醒信息
	 * yaoyz    2018年6月27日
	 */
	@RequestMapping(value = "/notice/updateNotice", method = RequestMethod.POST)
	public Map<String, Integer> updateNotice(Long noticeId,String title, String desc, String noticeTime) {
		Map<String, Integer> retMap = new HashMap<String, Integer>();
		retMap.put("status", 0);
		noticeService.updateNotice(noticeId,title,desc,noticeTime);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 查询
	 * yaoyz    2018年6月27日
	 */
	@RequestMapping(value = "/notice/loadNotices", method = RequestMethod.POST)
	public Map<String, Object> loadNotices(Long userId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Map<String, Object>> notices = noticeService.loadNotices(userId);
		for(Map<String, Object> map : notices){
			if(map.get("status").toString().equals("0")){
				map.put("txtStyle", "background-color: #dddddd");
			}else{			
				map.put("txtStyle", "background-color: #eeeeee");
			}
		}
		retMap.put("notices", notices);
		retMap.put("status", 1);
		return retMap;
	}

}
