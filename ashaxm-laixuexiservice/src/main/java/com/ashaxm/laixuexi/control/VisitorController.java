package com.ashaxm.laixuexi.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ashaxm.laixuexi.modal.Admin;
import com.ashaxm.laixuexi.modal.Class;
import com.ashaxm.laixuexi.modal.Dance;
import com.ashaxm.laixuexi.modal.Visitor;
import com.ashaxm.laixuexi.service.ClassService;
import com.ashaxm.laixuexi.service.DanceService;
import com.ashaxm.laixuexi.service.FlowPriceService;
import com.ashaxm.laixuexi.service.MsgService;
import com.ashaxm.laixuexi.service.VisitorService;
import com.ashaxm.laixuexi.system.SystemField;
import com.ashaxm.laixuexi.utils.MessageUtil;
import com.ashaxm.laixuexi.utils.WxUtil;

import net.sf.json.JSONObject;


/**
 * 用户信息相关操作
 * yaoyz    2018.5.18
 */
@RestController
public class VisitorController {
	
	Log log = LogFactory.getLog(VisitorController.class);
	 
	@Autowired
	private VisitorService visitorService;
	
	@Autowired
	private FlowPriceService flowPriceService;
	
	@Autowired
	private DanceService danceService;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private MsgService msgService;
	
	/**
	 * 加载所有的学生信息
	 * yaoyz    2018年6月2日
	 */
	@RequestMapping(value = "/visitor/loadVisitors", method = RequestMethod.POST)
	public Map<String, Object> loadVisitors() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Visitor> visitors = visitorService.loadVisitors();
		retMap.put("visitors", visitors);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 添加学生
	 * yaoyz    2018年6月2日
	 */
	@RequestMapping(value = "/visitor/addVisitor", method = RequestMethod.POST)
	public Map<String, Object> addVisitor(Long adminId,String name, String sex,Integer age, String parent,String phone,Integer cost,Integer time,Integer danceId,Integer classId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		boolean checkAdmin = visitorService.checkAdmin(adminId);
		if(checkAdmin){		
			long userId = visitorService.addVisitor(adminId,name,sex,age,parent,phone,cost,time,danceId,classId);
			flowPriceService.addFlow(userId,adminId,cost,0,time,time,1);
			retMap.put("status", 1);
		}
		return retMap;
	}
	
	/**
	 * 修改学生基本信息
	 * yaoyz    2018年6月2日
	 */
	@RequestMapping(value = "/visitor/updateVisitor", method = RequestMethod.POST)
	public Map<String, Object> updateVisitor(Long userId,Long adminId,String name, String sex, String parent,String phone,Integer age,Integer danceId,Integer classId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		boolean checkAdmin = visitorService.checkAdmin(adminId);
		if(checkAdmin){		
			visitorService.updateVisitor(userId,name,sex,parent,phone,age,danceId,classId);
			retMap.put("status", 1);
		}
		return retMap;
	}
	
	/**
	 * 用户上课签到
	 * yaoyz    2018年6月2日
	 */
	@RequestMapping(value = "/visitor/confirmStudy", method = RequestMethod.POST)
	public Map<String, Object> confirmStudy(Long userId,Long adminId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		boolean checkAdmin = visitorService.checkAdmin(adminId);
		if(checkAdmin){		
			//查询学生当前的基本信息
			Visitor visitor = visitorService.loadItemVisitor(userId);
			//修改用户表中的相关数据
			visitorService.updateVisitorCost(userId);
			//记录本次操作的流水表
			flowPriceService.addFlow(userId,adminId, SystemField.ITEM_STUDY_COST, visitor.getSpareTime(), 1, visitor.getSpareTime()-1, 0);
			//发送短信
			if(MessageUtil.sendMsgArrived(visitor.getName(), visitor.getPhone())){
				msgService.sendMsg(adminId,visitor.getId(),visitor.getPhone(),0,0);
			}else{
				msgService.sendMsg(adminId,visitor.getId(),visitor.getPhone(),0,1);
			}
			//判断剩余学习时长，如果只剩五次，则短信通知
			if(visitor.getSpareTime()==6){
				if(MessageUtil.sendMsgRenew(visitor.getPhone())){
					msgService.sendMsg(adminId,visitor.getId(),visitor.getPhone(),1,0);
				}else{
					msgService.sendMsg(adminId,visitor.getId(),visitor.getPhone(),1,1);
				}
			}
			retMap.put("status", 1);
		}
		return retMap;
	}
	
	/**
	 * 用户充值
	 * yaoyz    2018年6月2日
	 */
	@RequestMapping(value = "/visitor/rechargeVisitor", method = RequestMethod.POST)
	public Map<String, Object> rechargeVisitor(Long userId,Long adminId, Integer cost,Integer time) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		boolean checkAdmin = visitorService.checkAdmin(adminId);
		if(checkAdmin){	
			//查询当前用户的信息
			Visitor visitor = visitorService.loadItemVisitor(userId);
			//修改visitor表中的相关数据
			visitorService.reChargeVisitor(userId,cost,time);
			//增加流水表
			flowPriceService.addFlow(userId,adminId, cost, visitor.getSpareTime(), time, visitor.getSpareTime()+time, 1);
			//充值成功发送短信通知
//			System.out.println("-------------发送短信------------");
			retMap.put("status", 1);
		}
		return retMap;
	}
	
	/**
	 * 加载舞蹈
	 * yaoyz    2018年6月4日
	 */
	@RequestMapping(value = "/visitor/loadDances", method = RequestMethod.POST)
	public Map<String, Object> loadDances() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Dance> dances = danceService.loadDances();
		retMap.put("dances", dances);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 管理员增加舞蹈
	 * yaoyz    2018年6月4日
	 */
	@RequestMapping(value = "/visitor/addDance", method = RequestMethod.POST)
	public Map<String, Object> addDance(Long adminId, String danceName) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		boolean checkAdmin = visitorService.checkAdmin(adminId);
		if(checkAdmin){			
			danceService.addDance(adminId, danceName);
			List<Dance> dances = danceService.loadDances();
			retMap.put("dances", dances);
			retMap.put("status", 1);
		}
		return retMap;
	}
	
	/**
	 * 管理员删除舞蹈
	 * yaoyz    2018年6月4日
	 */
	@RequestMapping(value = "/visitor/deleteDance", method = RequestMethod.POST)
	public Map<String, Object> deleteDance(Long adminId,Long danceId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		boolean checkAdmin = visitorService.checkAdmin(adminId);
		if(checkAdmin){		
			danceService.deleteDance(danceId);
			List<Dance> dances = danceService.loadDances();
			retMap.put("dances", dances);
			retMap.put("status", 1);
		}
		return retMap;
	}
	
	/**
	 * 加载班级信息和舞种信息
	 * yaoyz    2018年6月12日
	 */
	@RequestMapping(value = "/visitor/loadBaseClassAndDance", method = RequestMethod.POST)
	public Map<String, Object> loadBaseClassAndDance() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Class> classes = classService.loadClass();
		retMap.put("classes", classes);
		List<Dance> dances = danceService.loadDances();
		retMap.put("dances", dances);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 查询所有课程信息
	 * yaoyz    2018年6月4日
	 */
	@RequestMapping(value = "/visitor/loadClass", method = RequestMethod.POST)
	public Map<String, Object> loadClass() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Class> classes = classService.loadClass();
		retMap.put("classes", classes);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 增加年级
	 * yaoyz    2018年6月4日
	 */
	@RequestMapping(value = "/visitor/addClass", method = RequestMethod.POST)
	public Map<String, Object> addClass(Long adminId, String name) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		boolean checkAdmin = visitorService.checkAdmin(adminId);
		if(checkAdmin){		
			classService.addClass(adminId, name);;
			List<Class> classes = classService.loadClass();
			retMap.put("classes", classes);
			retMap.put("status", 1);
		}
		return retMap;
	}
	
	/**
	 * 删除课程信息
	 * yaoyz    2018年6月4日
	 */
	@RequestMapping(value = "/visitor/deleteClass", method = RequestMethod.POST)
	public Map<String, Object> deleteClass(Long adminId,Long classId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		boolean checkAdmin = visitorService.checkAdmin(adminId);
		if(checkAdmin){			
			classService.deleteClass(classId);
			List<Class> classes = classService.loadClass();
			retMap.put("classes", classes);
			retMap.put("status", 1);
		}
		return retMap;
	}
	
	
	/**
	 * 用户登录小程序的第一步操作，根据code获取openid
	 * 并根据openid获取对应的visitor
	 * yaoyz    2018年6月5日
	 */
	@RequestMapping(value = "/visitor/loadBaseInfoByCode", method = RequestMethod.POST)
	public Map<String, Object> loadBaseInfoByCode(String code) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		JSONObject accessToken = WxUtil.getAccessToken(code);
		String openId = accessToken.get("openid").toString();
		log.info("oopenid------"+openId);
		Admin admin = visitorService.findAdminByOpenid(openId);
		if(admin==null){
			log.info("create new admin");
			admin = visitorService.createNewAdmin(openId);
		}
		retMap.put("admin", admin);			
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 根据id查询我的信息
	 * yaoyz    2018年6月5日
	 */
	@RequestMapping(value = "/visitor/loadBaseInfoById", method = RequestMethod.POST)
	public Map<String, Object> loadBaseInfoById(Long adminId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		Admin admin = visitorService.loadMyInfo(adminId);
		retMap.put("admin", admin);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 更改用户头像和昵称
	 * yaoyz    2018年5月21日
	 */
	@RequestMapping(value = "/visitor/updateAdmin", method = RequestMethod.POST)
	public Map<String, Object> updateAdmin(long adminId, String name, String headimg) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		visitorService.updateAdmin(adminId, name, headimg);
		retMap.put("status", 1);
		return retMap;
	}
	
	
	@RequestMapping(value = "/visitor/loadALLFlowList", method = RequestMethod.POST)
	public Map<String, Object> loadALLFlowList() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
//		visitorService.updateAdmin(adminId, name, headimg);
		retMap.put("status", 1);
		return retMap;
	}
	
	

	
}
