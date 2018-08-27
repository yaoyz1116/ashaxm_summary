package com.ashaxm.personal.control;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ashaxm.personal.modal.User;
import com.ashaxm.personal.service.UserService;
import com.ashaxm.personal.system.WxMiniUtils;

import net.sf.json.JSONObject;


@RestController
public class UserController {

	Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserService userService;
	
	/**
	 * �û���¼С����ĵ�һ������������code��ȡopenid
	 * ������openid��ȡ��Ӧ��visitor
	 * yaoyz    2018��5��21��
	 */
	@RequestMapping(value = "/user/loadBaseInfo", method = RequestMethod.POST)
	public Map<String, Object> loadBaseInfo(String code) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		JSONObject accessToken = WxMiniUtils.getAccessToken(code);
		String openId = accessToken.get("openid").toString();
		log.info("openId----"+openId);
		User user = userService.findVisitorByOpenid(openId);
		if(user==null){
			log.info("add new user");
			User newUser = new User();
			newUser.setOpenId(openId);
			user = userService.addNewuserFromWechat(newUser);
		}
		retMap.put("user", user);			
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * ����id��ѯ�ҵ���Ϣ
	 * yaoyz    2018��5��17��
	 */
	@RequestMapping(value = "/user/loadMyinfo", method = RequestMethod.POST)
	public Map<String, Object> loadMyinfo(long userId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		User user = userService.loadUserById(userId);
		retMap.put("user", user);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * �޸��û���������ͷ��
	 * yaoyz    2018��5��21��
	 */
	@RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
	public Map<String, Object> updateUser(long userId, String name, String headimg) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		userService.updateUserName(name, headimg, userId);
		retMap.put("status", 1);
		return retMap;
	}
	
}
