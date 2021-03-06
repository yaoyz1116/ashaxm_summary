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
	 * 查询登录用户对应的组中所有的花费情况
	 * yaoyz    2018年3月31日
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
	 * 用户新增加一条消费记录
	 * yaoyz    2018年3月31日
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
	 * 用户修改开销表中的金额
	 * yaoyz    2018年3月31日
	 */
	public void updateCost(Long dailyid, Long updateprice) {
		String sql = "update dailycost set costprice=? where id=?";
		Object[] param = new Object[]{updateprice,dailyid};
		jdbcTemplate.update(sql,param);
	}

	/**
	 * 用户修改开销表中的记录时，增加一条记录值
	 * yaoyz    2018年3月31日
	 * type  1修改某条记录    2删除某条记录
	 */
	public void addDailycostLog(long userid, long dailyid, long preprice, long updateprice,int type) {
		String sql = "insert into dailycostlog(userid,dailyid,preprice,updateprice,type,addtime) values(?,?,?,?,1,now())";
		Object[] param = new Object[]{userid,dailyid,preprice,updateprice};
		jdbcTemplate.update(sql,param);
	}

	/**
	 * 用户删除某条记录
	 * yaoyz    2018年3月31日
	 */
	public void deleteCost(Long dailyid) {
		String sql = "update dailycost set status=2 where id=?";
		jdbcTemplate.update(sql,dailyid);
	}
}
