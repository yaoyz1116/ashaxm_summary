package com.ashaxm.laixuexi.service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.ashaxm.laixuexi.modal.Admin;
import com.ashaxm.laixuexi.modal.Visitor;


/**
 * 用户信息相关操作
 * yaoyz    2018.5.18
 */
@Service
public class VisitorService {
	Log log = LogFactory.getLog(VisitorService.class);
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	private static final class VisitorMapper implements RowMapper<Visitor>{
		public Visitor mapRow(ResultSet rs, int rowNum) throws SQLException {
			Visitor v = new Visitor();
			v.setId(rs.getLong("id"));
			v.setAdminId(rs.getLong("adminid"));
			v.setName(rs.getString("name"));
			v.setSex(rs.getString("sex"));
			v.setAge(rs.getInt("age"));
			v.setParent(rs.getString("parent"));
			v.setPhone(rs.getString("phone"));
			v.setCost(rs.getInt("cost"));
			v.setTime(rs.getInt("time"));
			v.setStudyTime(rs.getInt("studytime"));
			v.setSpareTime(rs.getInt("sparetime"));
			v.setAddtime(rs.getString("addtime"));
			v.setDanceId(rs.getInt("danceid"));
			v.setClassId(rs.getInt("classid"));
			return v;
		}	
	}
	
	private static final class AdminMapper implements RowMapper<Admin>{
		public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
			Admin a = new Admin();
			a.setId(rs.getLong("id"));
			a.setName(rs.getString("name"));
			a.setOpenId(rs.getString("openid"));
			a.setHeadImg(rs.getString("headimg"));
			a.setType(rs.getInt("type"));
			a.setAddtime(rs.getString("addtime"));
			return a;
		}	
	}

	/**
	 * 加载所有的学生信息
	 * yaoyz    2018年6月2日
	 */
	public List<Visitor> loadVisitors() {
		String sql = "select * from visitor";
		return jdbcTemplate.query(sql, new VisitorMapper());
	}

	/**
	 * 添加学生
	 * yaoyz    2018年6月2日
	 * @return 
	 */
	public long addVisitor(final Long adminId, final String name, final String sex, final Integer age, final String parent, 
			final String phone, final Integer cost, final Integer time,final Integer danceId, final Integer classId) {
		try {
			final String sql = "insert into visitor(adminid,name,sex,age,parent,phone,cost,time,studytime,sparetime,addtime,danceid,classid) values(?,?,?,?,?,?,?,?,0,?,now(),?,?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					PreparedStatement ps = con.prepareStatement(sql,new String[] { "id" });
					int index = 1;
					ps.setLong(index++, adminId);
					ps.setString(index++, name);
					ps.setString(index++, sex);
					ps.setInt(index++, age);
					ps.setString(index++, parent);
					ps.setString(index++, phone);
					ps.setInt(index++, cost);
					ps.setInt(index++, time);
					ps.setInt(index++, time);
					ps.setInt(index++, danceId);
					ps.setInt(index++, classId);
					return ps;
				}
			}, keyHolder);
			return keyHolder.getKey().longValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 修改用户基本信息
	 * yaoyz    2018年6月2日
	 */
	public void updateVisitor(Long userId, String name, String sex, String parent, String phone, Integer age, Integer danceId, Integer classId) {
		String sql ="update visitor set name=?,sex=?,parent=?,phone=?,age=?,danceid=?,classid=? where id=?";
		Object[] param = new Object[]{name,sex,parent,phone,age,danceId,classId,userId};
		jdbcTemplate.update(sql,param);
	}

	/**
	 * 查询用户的基本信息
	 * yaoyz    2018年6月2日
	 */
	public Visitor loadItemVisitor(Long userId) {
		String sql = "select * from visitor where id="+userId;
		List<Visitor> list = jdbcTemplate.query(sql, new VisitorMapper());
		if(list.size()>0)
			return list.get(0);
		return null;
	}

	/**
	 * 用户签到时，修改visitor表中的相关信息
	 * yaoyz    2018年6月2日
	 */
	public void updateVisitorCost(Long userId) {
		String sql = "update visitor set studytime=studytime+1,sparetime=sparetime-1 where id="+userId;
		jdbcTemplate.update(sql);
	}

	/**
	 * 用户充值金额
	 * yaoyz    2018年6月2日
	 */
	public void reChargeVisitor(Long userId, Integer cost, Integer time) {
		String sql = "update visitor set cost=cost+"+cost+", time=time+"+time+", sparetime=sparetime+"+time+" where id="+userId;
		jdbcTemplate.update(sql);
	}

	/**
	 * 判断当前登录人是否是系统管理员
	 * yaoyz    2018年6月4日
	 */
	public boolean checkAdmin(Long adminId) {
		String sql = "select * from admin where id="+adminId+" and type=1";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list.size() > 0){
			return true;
		}
		return false;
	}

	/**
	 * 根据openid获取对应的adminid
	 * yaoyz    2018年6月5日
	 */
	public Admin findAdminByOpenid(String openId) {
		List<Admin> list;
		try {
			String sql = "select * from admin where openid='"+openId+"'";
			list = jdbcTemplate.query(sql, new AdminMapper());
			if(list.size() > 0)
				return list.get(0);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建管理员账户
	 * yaoyz    2018年6月5日
	 */
	public Admin createNewAdmin(final String openId) {
		Admin a = new Admin();
		try {
			final String sql = "insert into admin(openid,type,addtime) values(?,0,now())";
			KeyHolder keyHolder = new GeneratedKeyHolder();	
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					PreparedStatement ps = con.prepareStatement(sql,new String[] { "id" });
					int index = 1;
					ps.setString(index++, openId);
					return ps;
				}
			}, keyHolder);
			long adminId = keyHolder.getKey().longValue();
			a.setId(adminId);
			a.setOpenId(openId);
			a.setType(0);
		} catch (Exception e) {
			log.error(this,e);
		}
		return a;
	}

	/**
	 * 查询管理员信息
	 * yaoyz    2018年6月5日
	 */
	public Admin loadMyInfo(long adminId) {
		String sql = "select * from admin where id="+adminId;
		List<Admin> list = jdbcTemplate.query(sql, new AdminMapper());
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 更改管理员的信息
	 * yaoyz    2018年6月5日
	 */
	public void updateAdmin(long adminId, String name, String headimg) {
		String sql = "update admin set name=?,headimg=? where id=?";
		Object[] param = new Object[]{name,headimg,adminId};
		jdbcTemplate.update(sql,param);
	}
	

	
	
}
