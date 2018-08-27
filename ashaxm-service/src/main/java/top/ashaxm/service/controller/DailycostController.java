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

import top.ashaxm.common.service.DailycostService;

@RestController
public class DailycostController {
	
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private DailycostService dailycostService;
	
	/**
	 * 查询登录用户对应的组中所有的开销情况
	 * yaoyz    2018年3月31日
	 */
	@RequestMapping(value = "/dailycost/loadAllCost", method = RequestMethod.POST)
	public Map<String, Object> loadAllCost(Long groupid,Long userid) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		System.out.println("groupid    "+groupid);
		retMap.put("status", 0);
		List<Map<String, Object>> data = dailycostService.loadAllCost(groupid,userid);
		long groupnum=1;
		if(!(groupid.equals(1) || groupid==1)){
			log.info("load a group");
			groupnum = dailycostService.queryGroupnum(groupid);
		}
		retMap.put("groupnum", groupnum);
		retMap.put("data", data);
		retMap.put("status", 1); 
		return retMap;
	}
	
	/**
	 * 用户新增加一条消费记录
	 * yaoyz    2018年3月31日
	 */
	@RequestMapping(value = "/dailycost/addCost", method = RequestMethod.POST)
	public Map<String, Object> addCost(Long userid,Long groupid,String costdate,String costweek, String costprice) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		System.out.println(userid+"_____"+groupid+"_______"+costdate+"________"+costweek+"______"+costprice);
		retMap.put("status", 0);
		dailycostService.addCost(userid,groupid,costdate,costweek,costprice);
		retMap.put("status", 1); 
		System.out.println("success");
		return retMap;
	}
	
	/**
	 * 用户修改花费表中的金额
	 * yaoyz    2018年3月31日
	 */
	@RequestMapping(value = "/dailycost/updateCost", method = RequestMethod.POST)
	public Map<String, Object> updateCost(Long userid,Long dailyid,Long preprice, Long updateprice) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		dailycostService.updateCost(dailyid,updateprice);
		dailycostService.addDailycostLog(userid,dailyid,preprice,updateprice,1);
		retMap.put("status", 1); 
		return retMap;
	}
	
	/**
	 * 用户主动删除某条记录
	 * yaoyz    2018年3月31日
	 */
	@RequestMapping(value = "/dailycost/deleteCost", method = RequestMethod.POST)
	public Map<String, Object> deleteCost(Long userid,Long dailyid) {
		System.out.println("deletecost    "+userid+"     "+dailyid);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		dailycostService.deleteCost(dailyid);
		dailycostService.addDailycostLog(userid,dailyid,0,0,2);
		retMap.put("status", 1); 
		return retMap;
	}
	
	
	
}
