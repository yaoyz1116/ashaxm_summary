package com.ashaxm.personal.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import com.ashaxm.personal.modal.User;

/**
 * �û������Ϣ����
 * Ҧ����   2018.6.23
 */
@Service
public class UserService {

	Log log = LogFactory.getLog(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final class UserMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();		
			user.setId(rs.getLong("id"));
			user.setUserName(rs.getString("username"));
			user.setPhone(rs.getString("phone"));
			user.setAddTime(rs.getString("addtime"));
			user.setGroupId(rs.getLong("groupid"));
			user.setOpenId(rs.getString("openid"));
			user.setNickName(rs.getString("nickname"));
			user.setImage(rs.getString("image"));
			return user;
		}
	}
	
	/**
	 * ����openid�ж��Ƿ��û��Ƿ����
	 * yaoyz    2018��4��1��
	 */
	public User findVisitorByOpenid(String openid){
		String sql = "select * from user where openid='"+openid+"'";
		List<User> list = jdbcTemplate.query(sql, new UserMapper());
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
	/**
	 * ����id��ȡ��Ӧ���û���Ϣ
	 * yaoyz    2018��6��23��
	 */
	public User loadUserById(long id){
		String sql = "select * from user where id="+id;
		List<User> list = jdbcTemplate.query(sql, new UserMapper());
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
	/**
	 * ���ݻ�ȡ����΢�ŵ������Ϣ���������û�
	 * yaoyz    2018��4��2��
	 */
	public User addNewuserFromWechat(final User user){
		final String sql = "insert into user(nickname,openid,image,addtime) values(?,?,?,now())";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,new String[] { "id" });
				int index = 1;
				ps.setString(index++, user.getNickName());
				ps.setString(index++, user.getOpenId());
				ps.setString(index++, user.getImage());				
				return ps;
			}
		}, keyHolder);
		user.setId(keyHolder.getKey().longValue());		
		return user;
	}
	
	/**
	 * �õ��û�����Ȩ֮���޸��û��ǳƺ�ͼƬ
	 * yaoyz    2018��6��23��
	 */
	public void updateUserName(String nickname,String image,long id){
		String sql = "update user set nickname=?,image=? where id=?";
		Object[] param = new Object[]{nickname,image,id};
		jdbcTemplate.update(sql,param);
	}
	
	/**
	 * ��ѯ��¼�û����������е�������
	 * yaoyz    2018��3��31��
	 */
	public Long queryGroupnum(Long groupid) {
		String sql = "select count(*) as num from user where groupid="+groupid;
		return jdbcTemplate.queryForObject(sql, Long.class);
	}
}
