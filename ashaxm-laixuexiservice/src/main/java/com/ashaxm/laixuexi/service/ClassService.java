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

import com.ashaxm.laixuexi.modal.Class;

/**
 * 年级的相关操作
 * yapyz  2018.6.4
 */
@Service
public class ClassService {
	Log log = LogFactory.getLog(ClassService.class);
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	private static final class ClassMapper implements RowMapper<Class>{
		public Class mapRow(ResultSet rs, int rowNum) throws SQLException {
			Class c = new Class();
			c.setId(rs.getLong("id"));
			c.setName(rs.getString("name"));
			c.setAdminId(rs.getLong("adminid"));
			c.setAddtime(rs.getString("addtime"));
			return c;
		}	
	}
	
	/**
	 * 查询年级信息
	 * yaoyz    2018年6月4日
	 */
	public List<Class> loadClass(){
		String sql = "select * from class";
		return jdbcTemplate.query(sql, new ClassMapper());
	}
	
	/**
	 * 增加年级信息
	 * yaoyz    2018年6月4日
	 */
	public void addClass(long adminId,String name){
		String sql = "insert into class(adminid,name,addtime) values(?,?,now())";
		Object[] param = new Object[]{adminId,name};
		jdbcTemplate.update(sql,param);
	}
	
	/**
	 * 删除课程
	 * yaoyz    2018年6月4日
	 */
	public void deleteClass(long classId){
		String sql ="delete from class where id="+classId;
		jdbcTemplate.update(sql);
	}
}
