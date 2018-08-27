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
	 * 用户登录小程序的第一步操作，根据code获取openid
	 * 并根据openid获取对应的visitor
	 * yaoyz    2018年5月21日
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
	 * 根据id查询我的信息
	 * yaoyz    2018年5月17日
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
	 * 修改用户的姓名和头像
	 * yaoyz    2018年5月21日
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
