package com.ashaxm.personal.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DailyCostService {

	Log log = LogFactory.getLog(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * ��ѯ��¼�û���Ӧ���������еĻ������
	 * yaoyz    2018��3��31��
	 * @param userid 
	 */
	public List<Map<String, Object>> loadAllCost(long groupid, Long userid) {
		String sql ="";
		if(groupid==1){
			log.info("load one user");
			sql="select * from dailycost where userid="+userid+" and status=1";
		}else{
			log.info("load a group");
			sql = "select * from dailycost where groupid="+groupid+" and status=1";
		}
		return jdbcTemplate.queryForList(sql);	
	}

	/**
	 * �û�������һ�����Ѽ�¼
	 * yaoyz    2018��3��31��
	 */
	public void addCost(Long userid, Long groupid, String costdate, String costweek, String costprice) {
		try {
			String sql = "insert into dailycost(userid,groupid,costdate,costweek,costprice,status,addtime) values(?,?,?,?,?,1,now())";
			Object[] param = new Object[]{userid,groupid,costdate,costweek,costprice};
			jdbcTemplate.update(sql,param);
		} catch (Exception e) {
			log.error(this,e);
		}
	}

	/**
	 * �û��޸Ŀ������еĽ��
	 * yaoyz    2018��3��31��
	 */
	public void updateCost(Long dailyid, Long updateprice) {
		String sql = "update dailycost set costprice=? where id=?";
		Object[] param = new Object[]{updateprice,dailyid};
		jdbcTemplate.update(sql,param);
	}

	/**
	 * �û��޸Ŀ������еļ�¼ʱ������һ����¼ֵ
	 * yaoyz    2018��3��31��
	 * type  1�޸�ĳ����¼    2ɾ��ĳ����¼
	 */
	public void addDailycostLog(long userid, long dailyid, long preprice, long updateprice,int type) {
		String sql = "insert into dailycostlog(userid,dailyid,preprice,updateprice,type,addtime) values(?,?,?,?,1,now())";
		Object[] param = new Object[]{userid,dailyid,preprice,updateprice};
		jdbcTemplate.update(sql,param);
	}

	/**
	 * �û�ɾ��ĳ����¼
	 * yaoyz    2018��3��31��
	 */
	public void deleteCost(Long dailyid) {
		String sql = "update dailycost set status=2 where id=?";
		jdbcTemplate.update(sql,dailyid);
	}
}
