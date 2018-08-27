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

import com.ashaxm.personal.service.DailyCostService;
import com.ashaxm.personal.service.UserService;

/**
 * �������
 * yaoyz    2018.6.23
 */
@RestController
public class DailyCostController {

	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private DailyCostService dailyCostService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * ��ѯ��¼�û���Ӧ���������еĿ������
	 * yaoyz    2018��3��31��
	 */
	@RequestMapping(value = "/dailycost/loadAllCost", method = RequestMethod.POST)
	public Map<String, Object> loadAllCost(Long groupid,Long userid) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Map<String, Object>> data = dailyCostService.loadAllCost(groupid,userid);
		long groupnum=1;
		if(!(groupid.equals(0) || groupid==0)){
			groupnum = userService.queryGroupnum(groupid);
		}
		retMap.put("groupnum", groupnum);
		retMap.put("data", data);
		retMap.put("status", 1); 
		return retMap;
	}
	
	/**
	 * �û�������һ�����Ѽ�¼
	 * yaoyz    2018��3��31��
	 */
	@RequestMapping(value = "/dailycost/addCost", method = RequestMethod.POST)
	public Map<String, Object> addCost(Long userid,Long groupid,String costdate,String costweek, String costprice) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		dailyCostService.addCost(userid,groupid,costdate,costweek,costprice);
		retMap.put("status", 1); 
		return retMap;
	}
	
	/**
	 * �û��޸Ļ��ѱ��еĽ��
	 * yaoyz    2018��3��31��
	 */
	@RequestMapping(value = "/dailycost/updateCost", method = RequestMethod.POST)
	public Map<String, Object> updateCost(Long userid,Long dailyid,Long preprice, Long updateprice) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		dailyCostService.updateCost(dailyid,updateprice);
		dailyCostService.addDailycostLog(userid,dailyid,preprice,updateprice,1);
		retMap.put("status", 1); 
		return retMap;
	}
	
	/**
	 * �û�����ɾ��ĳ����¼
	 * yaoyz    2018��3��31��
	 */
	@RequestMapping(value = "/dailycost/deleteCost", method = RequestMethod.POST)
	public Map<String, Object> deleteCost(Long userid,Long dailyid) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		dailyCostService.deleteCost(dailyid);
		dailyCostService.addDailycostLog(userid,dailyid,0,0,2);
		retMap.put("status", 1); 
		return retMap;
	}
}
