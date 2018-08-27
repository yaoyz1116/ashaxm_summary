package top.ashaxm.service.system;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/**
 * 用户登录的时候的一些操作
 * @author yaoyz
 * 2018年1月8日
 */
public class LoginUserService extends JdbcDaoImpl implements UserDetailsService {
	Log log = LogFactory.getLog(getClass());
	
    public LoginUserService(DataSource dataSource){
        setDataSource(dataSource); 
    }

    public LoginUserDetails loadUserByUsername(String username) {
    	//app用户账号登录
    	List<LoginUser> userList = 
    			getJdbcTemplate().query("select * from user where username = ?", new String[] {username}, new LoginUserMapper());
    	if(userList.size()>0){
    		return userList.get(0);
    	}else{
    		//如果不存在此用户，则建一个空的用户，没有权限
    		LoginUser user= new LoginUser(0,"","",false,false,false,false,AuthorityUtils.NO_AUTHORITIES);
    		return user;
    	}
    }

    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    private static final class LoginUserMapper implements RowMapper<LoginUser> {
        @Override
        public LoginUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            LoginUser user = new LoginUser(rs.getLong("id"),
            		rs.getString("username"),
            		rs.getString("password"),
            		rs.getInt("enabled")==1,true,true,true,
            		AuthorityUtils.NO_AUTHORITIES);
            user.setId(rs.getLong("id"));
            user.setRole(rs.getInt("role"));
            return user;
        }
    }
}
