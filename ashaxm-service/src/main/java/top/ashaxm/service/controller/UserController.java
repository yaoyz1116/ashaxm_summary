package top.ashaxm.service.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import top.ashaxm.common.model.User;
import top.ashaxm.common.service.UserService;
import top.ashaxm.common.utils.HttpRequest;
import top.ashaxm.common.utils.StringUtils;

/**
 * 用户的一些操作
 * @author yaoyz
 * 2018年1月8日
 */
@RestController
public class UserController {
	Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 用户注册
	 * @author yaoyz
	 * 2018年1月8日
	 */
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public Map<String, Object> userRegister(String name, String password,String phone) {
		name = StringUtils.convertEncode(name, "iso-8859-1", "utf-8");
		password = StringUtils.convertEncode(password, "iso-8859-1", "utf-8");
		Map<String, Object> retMap = new HashMap<String, Object>();
		String ipaddress = HttpRequest.getClientIP(request);
		retMap.put("status", 0);
		retMap.put("msg", "用户注册发生异常");
		//检查电话号码是否已经被占用
		boolean flag = userService.checkName(phone);
		if(!flag){
			//注册用户
			Long userid = userService.userRegister(name,password,phone,ipaddress);
			log.info("User registration success    "+name+"____"+phone+"____"+ipaddress);
			retMap.put("loginuserid", userid);
			retMap.put("status", 1);
			retMap.put("msg", "用户注册成功");
		}else{
			log.info("The nickname has been occupied   "+name+"______"+phone);
			retMap.put("msg", "用户名已经被注册");
		}
		return retMap;
	}
	
	/**
	 * 用户登录
	 * @author yaoyz
	 * 2018年1月8日
	 */
	@RequestMapping(value = "/user/load", method = RequestMethod.POST)
	public Map<String, Object> userLoad(String name, String password) {
		System.out.println("name____"+name);
		name = StringUtils.convertEncode(name, "iso-8859-1", "utf-8");
		password = StringUtils.convertEncode(password, "iso-8859-1", "utf-8");
		Map<String, Object> retMap = new HashMap<String, Object>();
		System.out.println("name____"+name);
		retMap.put("status", 0);
		retMap.put("msg", "用户登录发生异常");
		//检查用户是否存在
		User user = userService.userLoad(name,password);
		if(user != null){
			retMap.put("loginuserid", user.getId());
			retMap.put("status", 1);
			retMap.put("msg", "用户登录成功");
			log.info("User login successfully_____"+name);
		}else{
			retMap.put("msg", "昵称或密码不对");			
		}
		return retMap;
	}
	
	
	

}
