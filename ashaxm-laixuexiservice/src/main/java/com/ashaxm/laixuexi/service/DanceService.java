package com.ashaxm.laixuexi.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.ashaxm.laixuexi.modal.Dance;

@Service
public class DanceService {
	
	Log log = LogFactory.getLog(DanceService.class);
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	private static final class DanceMapper implements RowMapper<Dance>{
		public Dance mapRow(ResultSet rs, int rowNum) throws SQLException {
			Dance d = new Dance();
			d.setId(rs.getLong("id"));
			d.setName(rs.getString("name"));
			d.setAdminId(rs.getLong("adminid"));
			d.setAddtime(rs.getString("addtime"));
			return d;
		}	
	}
	
	/**
	 * 加载舞蹈种类
	 * yaoyz    2018年6月4日
	 */
	public List<Dance> loadDances(){
		String sql ="select * from dance";
		return jdbcTemplate.query(sql, new DanceMapper());
	}
	
	/**
	 * 增加舞蹈种类
	 * yaoyz    2018年6月4日
	 */
	public void addDance(long adminId, String name){
		String sql = "insert into dance(adminid,name,addtime) values(?,?,now())";
		Object[] param = new Object[]{adminId,name};
		jdbcTemplate.update(sql,param);
	}
	
	/**
	 * 删除舞蹈
	 * yaoyz    2018年6月4日
	 */
	public void deleteDance(long danceId){
		String sql = "delete from dance where id="+danceId;
		jdbcTemplate.update(sql);
	}
}
