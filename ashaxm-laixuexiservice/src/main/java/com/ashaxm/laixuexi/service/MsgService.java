package com.ashaxm.laixuexi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 短信发送
 */
@Service
public class MsgService {
	
	@Autowired
    JdbcTemplate jdbcTemplate;

	/**
	 * 发送短信
	 * yaoyz    2018年6月11日
	 */
	public void sendMsg(Long adminId, long visitorid, String phone, int type, int status) {
		String sql = "insert into msg(adminid,visitorid,phone,type,status,addtime) values(?,?,?,?,?,now())";
		Object[] param = new Object[]{adminId,visitorid,phone,type,status};
		jdbcTemplate.update(sql,param);
	}

}
