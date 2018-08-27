package com.huanxin.control;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.huanxin.modal.Part;
import com.huanxin.service.PartService;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;



@RestController
public class PartController {
	
	private static final String FILE_PATH = "/opt/files/huanxin/";
	
	Log log = LogFactory.getLog(PartController.class);
	
	@Autowired
	private PartService partService;
	
	
	@RequestMapping(value = "/user/loadUserById", method = RequestMethod.POST)
	public Map<String, Object> loadUserById(Long userId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		Map<String, Object> user = partService.loadUserById(userId);
		retMap.put("user", user);
		retMap.put("status", 1);
		return retMap;
	}
	
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public Map<String, Object> login(String usercode,String password) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		Map<String, Object> user = partService.login(usercode,password);	
		retMap.put("user", user);
		retMap.put("status", 1);
		return retMap;
	}

	/**
	 * ��ѯ����
	 * yaoyz    2018��6��18��
	 */
	@RequestMapping(value = "/part/loadParts", method = RequestMethod.POST)
	public Map<String, Object> loadParts(String aimSearch, Long length) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Part> parts = partService.loadParts(aimSearch,length);
		retMap.put("parts", parts);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * ������Ա���ù�װ
	 * yaoyz    2018��6��18��
	 */
	@RequestMapping(value = "/part/borrowParts", method = RequestMethod.POST)
	public Map<String, Object> borrowParts(Long userId,Long partId,Long departId,Long num) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		partService.borrowParts(userId,partId,departId,num);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * ��ܲ�ѯ������Ĺ���
	 * yaoyz    2018��6��18��
	 */
	@RequestMapping(value = "/part/dealParts", method = RequestMethod.POST)
	public Map<String, Object> dealParts() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Map<String, Object>> dealParts = partService.dealParts(0);
		retMap.put("dealParts", dealParts);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * ��ܲ�ѯ���黹����
	 * yaoyz    2018��6��18��
	 */
	@RequestMapping(value = "/part/backParts", method = RequestMethod.POST)
	public Map<String, Object> backParts() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Map<String, Object>> backParts = partService.dealParts(1);
		retMap.put("backParts", backParts);
		List<Map<String, Object>> kuguan = partService.loadKuGuan();
		retMap.put("kuguan", kuguan);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * ���ȷ��Ա���黹��Ϣ
	 * yaoyz    2018��6��18��
	 */
	@RequestMapping(value = "/part/backBorrowParts", method = RequestMethod.POST)
	public Map<String, Object> backBorrowParts(Long hisId,Long kuguanId,Long backNum,Long partId, Boolean backAll) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		partService.backParts(hisId,kuguanId,backNum,backAll);
		partService.updateNum(partId, backNum, true);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * ���ȷ�Ͻ����װ
	 * yaoyz    2018��6��18��
	 */
	@RequestMapping(value = "/part/confirmBorrowParts", method = RequestMethod.POST)
	public Map<String, Object> confirmBorrowParts(Long hisId,Long kuguanId,Long borrowNum,Long partId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		partService.updateNum(partId,borrowNum,false);
		partService.confirmBorrowParts(hisId,kuguanId);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * �������еĿ�����Ϣ
	 * yaoyz    2018��6��18��
	 */
	@RequestMapping(value = "/depart/loadDeparts", method = RequestMethod.POST)
	public Map<String, Object> loadDeparts() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Map<String, Object>> departs = partService.loadDeparts();
		retMap.put("departs", departs);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * ����һ�������е�������Ա��Ϣ
	 * yaoyz    2018��6��18��
	 */
	@RequestMapping(value = "/user/loadUsers", method = RequestMethod.POST)
	public Map<String, Object> loadUsers(Long departId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Map<String, Object>> users = partService.loadUsers(departId);
		retMap.put("users", users);
		retMap.put("status", 1);
		return retMap;
	}
	
	/**
	 * 修改密码
	 * yaoyz    2018年6月19日
	 */
	@RequestMapping(value = "/user/cgpwd", method = RequestMethod.POST)
	public Map<String, Object> cgpwd(Long userId,String prePwd, String nowPwd) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		boolean flag = partService.cgpwd(userId,prePwd,nowPwd);
		if(flag){		
			retMap.put("status", 1);
		}else{
			retMap.put("status", 2);		
		}
		return retMap;
	}
	
	
	@RequestMapping(value = "/part/ruku", method = RequestMethod.POST)
	public Map<String, Object> ruku(Long manageid,Long partId,Long aimNum,Integer type) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		if(type > 0){
			//报废
			partService.updateNum(partId, aimNum, false);
		}else {
			//入库
			partService.updateNum(partId, aimNum, true);		
		}
		partService.insertPartCg(manageid,partId,aimNum,type);
		retMap.put("status", 1);
		return retMap;
	}
	
	@RequestMapping(value = "/part/newRuku", method = RequestMethod.POST)
	public Map<String, Object> newRuku(Long manageid, String name, String code, String val1, String val2, String process, String storage, Long num) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		
		long prePartId = partService.loadPartByCode(code);
		if(prePartId>0){
			retMap.put("status", 4);
			return retMap;
		}
		
		long processId = partService.loadProcessById(process);
		if(processId==0){
			retMap.put("status", 2);
			return retMap;
		}
		long storageId = partService.loadStorageById(storage);
		if(storageId==0){
			retMap.put("status", 3);
			return retMap;
		}
		partService.insertNewPart(manageid,name,code,val1, val2,num,storageId,processId);
		retMap.put("status", 1);
		return retMap;
	}
	
	
	@RequestMapping(value = "/part/downLoadExcel", method = RequestMethod.POST)
	public Map<String, Object> downLoadExcel(Integer type, String start, String end) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", 0);
		List<Map<String, Object>> borrow = partService.loadBorrowLiushui(type,start,end);
		List<Map<String, Object>> back = partService.loadBackLiushui(type,start,end);
		List<Map<String, Object>> ruku = partService.downRukuLiushui(type,start,end,0);
		List<Map<String, Object>> baofei = partService.downRukuLiushui(type,start,end,1);
		
		WritableWorkbook workbook = null;
		try {
			String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+".xls";	
			File sheetExcel = new File(FILE_PATH+fileName);
			workbook = Workbook.createWorkbook(sheetExcel);
			WritableSheet sheet1 = workbook.createSheet("借用申请记录", 0);
			WritableSheet sheet2 = workbook.createSheet("工装退还记录", 1);
			WritableSheet sheet3 = workbook.createSheet("工装入库记录", 2);
			WritableSheet sheet4 = workbook.createSheet("工装报废记录", 3);		
			String[] title1 = { "科室名称","科室代码","借用人姓名", "借用人工号", "借用工装名称", "借用工装代码", "借用数量", "借用时间", "状态", "经手库管","库管确认时间"};
			String[] title2 = { "科室名称","科室代码","退还人姓名", "退还人工号", "退还工装名称", "退还工装代码", "退还数量", "退还时间", "经手库管","库管工号"};
			String[] title3 = { "科室名称","科室代码","库管姓名", "库管工号", "入库工装名称", "入库工装代码", "入库数量", "入库时间"};
			String[] title4 = { "科室名称","科室代码","库管姓名", "库管工号", "报废工装名称", "报废工装代码", "报废数量", "报废时间"};		
			String[] data1 = { "dname" , "dcode" , "uname" , "ucode" , "pname" , "pcode" , "num" , "addtime" , "status" , "mname", "mcode" };
			String[] data2 = { "dname" , "dcode" , "uname" , "ucode" , "pname" , "pcode" , "num" , "addtime" , "name", "code" };
			String[] data3 = { "dname" , "dcode" , "username" , "usercode" , "name" , "code" , "num" , "addtime"};
			String[] data4 = { "dname" , "dcode" , "username" , "usercode" , "name" , "code" , "num" , "addtime"};		
			jxl.write.Label label = null;
			
			// sheet1
			for (int i = 0; i < title1.length; i++) {
				label = new jxl.write.Label(i, 0, title1[i]);
				sheet1.addCell(label);
			}
			if(borrow.size() > 0){
				String value = "";
				for(int i=0; i<borrow.size(); i++){
					Map<String, Object> map = borrow.get(i);
					for(int j=0; j<data1.length; j++){
						if(map.get(data1[j])!=null ){						
							value = map.get(data1[j]).toString();
							if(j==8){
								if(value.equals("0")){
									value="申请未通过";
								}else{
									value="已借用";
								}
							}
							label = new jxl.write.Label(j, (i + 1), value);
							sheet1.addCell(label);
						}
					}
				}
			}
			
			//shett2
			for (int i = 0; i < title2.length; i++) {
				label = new jxl.write.Label(i, 0, title2[i]);
				sheet2.addCell(label);
			}
			if(back.size() > 0){
				String value = "";
				for(int i=0; i<back.size(); i++){
					Map<String, Object> map = back.get(i);
					for(int j=0; j<data2.length; j++){
						if(map.get(data2[j])!=null ){						
							value = map.get(data2[j]).toString();
							label = new jxl.write.Label(j, (i + 1), value);
							sheet2.addCell(label);
						}
					}
				}
			}
			
			//shett3
			for (int i = 0; i < title3.length; i++) {
				label = new jxl.write.Label(i, 0, title3[i]);
				sheet3.addCell(label);
			}
			if(ruku.size() > 0){
				String value = "";
				for(int i=0; i<ruku.size(); i++){
					Map<String, Object> map = ruku.get(i);
					for(int j=0; j<data3.length; j++){
						if(map.get(data3[j])!=null ){						
							value = map.get(data3[j]).toString();
							label = new jxl.write.Label(j, (i + 1), value);
							sheet3.addCell(label);
						}
					}
				}
			}
			
			//shett4
			for (int i = 0; i < title4.length; i++) {
				label = new jxl.write.Label(i, 0, title4[i]);
				sheet4.addCell(label);
			}
			if(baofei.size() > 0){
				String value = "";
				for(int i=0; i<baofei.size(); i++){
					Map<String, Object> map = baofei.get(i);
					for(int j=0; j<data4.length; j++){
						if(map.get(data4[j])!=null ){						
							value = map.get(data4[j]).toString();
							label = new jxl.write.Label(j, (i + 1), value);
							sheet4.addCell(label);
						}
					}
				}
			}
			
			workbook.write();
			retMap.put("filename", fileName);
			retMap.put("status", 1);
		}catch (Exception err) {
			log.error(PartController.class,err);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				}catch (Exception e) {
					log.error(PartController.class,e);
				}
			}
		}
		
		return retMap;
	}
}
