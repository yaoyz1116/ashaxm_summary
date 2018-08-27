package top.ashaxm.service.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.sf.json.JSONObject;
import top.ashaxm.common.model.User;
import top.ashaxm.common.service.UserService;
import top.ashaxm.common.utils.EmojiFilter;
import top.ashaxm.common.utils.StringUtils;
import top.ashaxm.common.utils.WXAppletUserInfo;

/**
 * 针对微信的一些操作
 */
@RestController
public class WxController {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/checkServer", method = RequestMethod.GET)
	public String checkServer(String signature, String timestamp, String nonce,
			String echostr) {

		log.info("From WX --- signatrue:" + signature + " timestamp:"
				+ timestamp + " nonce:" + nonce + " echostr:" + echostr);
		return echostr;		
	}
	
	/**
	 * 根据小程序得到的code,获取用户的基本信息
	 * yaoyz    2018年4月1日
	 */
	@RequestMapping(value = "/wx/loaduserinfo", method = RequestMethod.POST)
	public Map<String, Object> loaduserinfo(String code, String avatarUrl, String nickName) {
		System.out.println("code     "+code);
		System.out.println("url    "+avatarUrl);
		System.out.println("nickname    "+nickName);
		nickName = StringUtils.convertEncode(nickName, "iso-8859-1", "utf-8");
		System.out.println("nickname    "+nickName);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		JSONObject obj = WXAppletUserInfo.getSessionKeyOropenid(code);
		String openid = obj.getString("openid");
		System.out.println("opendi    "+openid);
//		String openid = "oBFz30GS_DqdzQWxph8iHLsMP0i0";
//    	System.out.println(obj.get("openid")+"______________"+obj.get("session_key"));
    	User existuser = userService.checkExistByOpenid(openid);
    	if(existuser != null){
    		//当前登录用户已经存在
    		retMap.put("userInfo", existuser);
    	}else{
    		//当前登录用户不存在，需要创建
    		User user = new User();
    		if(EmojiFilter.containsEmoji(nickName)){
    			System.out.println("contains  emoji-----"+openid);
    			nickName+= "emoji"+EmojiFilter.filterEmoji(nickName);
    		}
    		user.setNickName(nickName);
    		user.setOpenid(openid);
    		user.setAvatarUrl(avatarUrl);
    		user.setGroupid(1);
    		user = userService.addNewuserFromWechat(user);
    		System.out.println("userid    "+user.getId()+"    "+user.getGroupid());
    		retMap.put("userInfo", user);   		
    	}
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 获取微信运动步数
	 * yaoyz    2018年4月10日
	 */
	@RequestMapping(value = "/wx/loadrundata", method = RequestMethod.POST)
	public Map<String, Object> loadrundata(String code, String encryptedData, String iv) {
		Map<String, Object> retMap = new HashMap<String, Object>();	
		retMap.put("status", 0);
		JSONObject data = WXAppletUserInfo.loadRunData(code, encryptedData, iv);		
		retMap.put("data", data);
		retMap.put("status", 1);
		return retMap;
	}

}
