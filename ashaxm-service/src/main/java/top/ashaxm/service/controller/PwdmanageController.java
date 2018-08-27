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

import top.ashaxm.common.service.PwdmanageService;
import top.ashaxm.common.utils.StringUtils;

/**
 * 密码管理器
 * @author yaoyz
 * 2018年1月21日
 */
@RestController
public class PwdmanageController {
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private PwdmanageService pwdmanageService;
	
	/**
	 * 增加新密码
	 * @author yaoyz
	 * 2018年1月21日
	 */
	@RequestMapping(value = "/pwdmanage/addnew", method = RequestMethod.POST)
	public Map<String, Object> addnewpwd(String userid, String seq,String pwd) {
		seq = StringUtils.convertEncode(seq, "iso-8859-1", "utf-8");
		pwd = StringUtils.convertEncode(pwd, "iso-8859-1", "utf-8");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		retMap.put("msg", "用户注册发生异常");
		boolean flag = pwdmanageService.checkseq(userid,seq);
		if(!flag){
			//增加新密码
			pwdmanageService.addnewpwd(userid,seq,pwd);
			retMap.put("status", 1);
			retMap.put("msg", "新密码添加成功");
		}else{
			log.info("The seq has been occupied   "+userid+"______"+seq);
			retMap.put("msg", "序号不可以重复");
		}
		return retMap;
	}
	
	/**
	 * 查询密码
	 * @author yaoyz
	 * 2018年1月22日
	 */
	@RequestMapping(value = "/pwdmanage/loadpwd", method = RequestMethod.POST)
	public Map<String, Object> loadpwd(String userid) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		retMap.put("msg", "查询密码异常");
		List<Map<String, Object>> pwdlist = pwdmanageService.loadpwd(userid);
		retMap.put("pwdlist", pwdlist);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 修改密码
	 * @author yaoyz
	 * 2018年1月22日
	 */
	@RequestMapping(value = "/pwdmanage/updatepwd", method = RequestMethod.POST)
	public Map<String, Object> updatepwd(String userid,String seq, String pwd) {
		seq = StringUtils.convertEncode(seq, "iso-8859-1", "utf-8");
		pwd = StringUtils.convertEncode(pwd, "iso-8859-1", "utf-8");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		retMap.put("msg", "密码修改异常");
		pwdmanageService.updatepwd(userid,seq,pwd);
		retMap.put("status", 1);
		retMap.put("msg", "密码修改成功");
		return retMap;
	}
	
	/**
	 * 删除密码
	 * @author yaoyz
	 * 2018年1月22日
	 */
	@RequestMapping(value = "/pwdmanage/deletepwd", method = RequestMethod.POST)
	public Map<String, Object> deletepwd(String userid,String seq) {
		seq = StringUtils.convertEncode(seq, "iso-8859-1", "utf-8");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		retMap.put("msg", "密码删除异常");
		pwdmanageService.deletepwd(userid,seq);
		retMap.put("status", 1);
		retMap.put("msg", "密码删除成功");
		return retMap;
	}
}
