package top.ashaxm.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import top.ashaxm.common.service.CountdownService;
import top.ashaxm.common.utils.StringUtils;

/**
 * 倒计时
 * @author yaoyz
 * 2018年1月16日
 */
@RestController
public class CountdownController {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private CountdownService countdownService;
	
	/**
	 * 添加倒计时
	 * @author yaoyz
	 * 2018年1月16日
	 */
	@RequestMapping(value = "/countdown/addnew", method = RequestMethod.POST)
	public Map<String, Object> addnew(String title, String settime, String content, String loginuserid ) {
		System.out.println("title____"+title+"    content____"+content+"    loginuserid____"+loginuserid);
		title = StringUtils.convertEncode(title, "iso-8859-1", "utf-8");
		content = StringUtils.convertEncode(content, "iso-8859-1", "utf-8");
		System.out.println("title____"+title+"    content____"+content);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		retMap.put("msg", "网络异常！");
		countdownService.addnew(title,content,settime,loginuserid);
		retMap.put("status", 1);
		retMap.put("msg", "添加成功");
		return retMap;
	}
	
	/**
	 * 加载所有的倒计时
	 * @author yaoyz
	 * 2018年1月16日
	 */
	@RequestMapping(value = "/countdown/loadallcount", method = RequestMethod.POST)
	public Map<String, Object> loadallcount(String loginuserid ) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		retMap.put("msg", "网络异常！");
		List<Map<String, Object>> list = countdownService.loadallcount(loginuserid);
		retMap.put("list", list);
		retMap.put("status", 1);
		retMap.put("msg", "添加成功");
		return retMap;
	}
}
