package com.ashaxm.laixuexi.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FlowPriceService {

	Log log = LogFactory.getLog(FlowPriceService.class);
	
	@Autowired
    JdbcTemplate jdbcTemplate;

	/**
	 * 增加流水账记录
	 * userid  用户id
	 * adminId  管理员id
	 * cost  本次消费/充值金额
	 * pretime  上次剩余学习时间
	 * time  本次充值/消费学习时间
	 * sparetime 剩余学习时间
	 * type 0消费  1充值
	 * yaoyz    2018年6月2日
	 */
	public void addFlow(long userId, Long adminId, Integer cost, Integer preTime, Integer time, Integer spareTime,Integer type) {
		try {
			String sql = "insert into flowprice(userid,adminid,cost,pretime,time,sparetime,type,addtime) values(?,?,?,?,?,?,?,now())";
			Object[] param = new Object[]{userId,adminId,cost,preTime,time,spareTime,type};
			jdbcTemplate.update(sql,param);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}
}
