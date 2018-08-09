package top.ashaxm.common.service;

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

import top.ashaxm.common.model.User;


/**
 * 用户相关
 * @author yaoyz
 * 2018年1月16日
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
			user.setUsername(rs.getString("username"));
			user.setPhone(rs.getString("phone"));
			user.setRegistertime(rs.getString("registertime"));
			user.setGroupid(rs.getLong("groupid"));
			user.setOpenid(rs.getString("openid"));
			user.setNickName(rs.getString("nickname"));
			user.setAvatarUrl(rs.getString("image"));
			return user;
		}
	}

	/**
	 * 检查昵称是否被占用,被占用则返回true
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public boolean checkName(String name) {
		String sql = "select * from user where username=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,name);
		if(list.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * 注册用户
	 * @author yaoyz
	 * 2018年1月8日
	 * @param ipaddress 
	 */
	public Long userRegister(final String name,final String password,final String phone,final String ipaddress) {
		final String sql = "insert into user(username,nickname,password,phone,ipaddress,registertime) values(?,?,?,?,?,now())";		
		long userid = 0;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con
						.prepareStatement(sql,new String[] { "id" });
				int index = 1;
				ps.setString(index++, phone);
				ps.setString(index++, name);
				ps.setString(index++, password);
				ps.setString(index++, phone);
				ps.setString(index++, ipaddress);
				return ps;
			}
		}, keyHolder);
		userid = keyHolder.getKey().longValue();
		return userid;
	}

	/**
	 * 用户登录，账户名、密码正确返回true
	 * @author yaoyz
	 * 2018年1月8日
	 */
	public User userLoad(String name, String password) {
		String sql = "select * from user where nickname=? and password=?";
		Object[] param = new Object[]{name,password};
		List<User> list = jdbcTemplate.query(sql,param, new UserMapper());
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据openid判断是否用户是否存在
	 * yaoyz    2018年4月1日
	 */
	public User checkExistByOpenid(String openid){
		String sql = "select * from user where openid='"+openid+"'";
		List<User> list = jdbcTemplate.query(sql, new UserMapper());
		if(list.size()>0)
			return list.get(0);
		return null;
	}
	
	/**
	 * 根据获取到的微信的相关信息，创建新用户
	 * yaoyz    2018年4月2日
	 */
	public User addNewuserFromWechat(User user){
		String sql = "insert into user(nickname,openid,image,registertime) values(?,?,?,now())";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,new String[] { "id" });
				int index = 1;
				ps.setString(index++, user.getNickName());
				ps.setString(index++, user.getOpenid());
				ps.setString(index++, user.getAvatarUrl());				
				return ps;
			}
		}, keyHolder);
		user.setId(keyHolder.getKey().longValue());		
		return user;
	}
	
}
