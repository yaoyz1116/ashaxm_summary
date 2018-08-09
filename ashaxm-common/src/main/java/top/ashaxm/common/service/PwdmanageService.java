package top.ashaxm.common.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 密码管理器
 * @author yaoyz
 * 2018年1月21日
 */
@Service
public class PwdmanageService {
	Log log = LogFactory.getLog(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 检查是否已经存在seq
	 * @author yaoyz
	 * 2018年1月22日
	 */
	public boolean checkseq(String userid, String seq) {
		String sql = "select * from pwdmanage where userid=? and seq=?";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,userid,seq);
		if(list.size()>0)
			return true;
		return false;
	}

	/**
	 * 增加新密码
	 * @author yaoyz
	 * 2018年1月22日
	 */
	public void addnewpwd(String userid, String seq, String pwd) {
		String sql = "insert into pwdmanage(userid,seq,pwd,addtime) values(?,?,?,now())";
		Object[] param = new Object[]{userid,seq,pwd};
		jdbcTemplate.update(sql,param);
	}

	/**
	 * 查询密码
	 * @author yaoyz
	 * 2018年1月22日
	 * @return 
	 */
	public List<Map<String, Object>> loadpwd(String userid) {
		String sql = "select * from pwdmanage where userid=? and status=1";
		return jdbcTemplate.queryForList(sql,userid);
	}

	/**
	 * 修改密码
	 * @author yaoyz
	 * 2018年1月22日
	 */
	public void updatepwd(String userid, String seq, String pwd) {
		String sql = "update pwdmanage set pwd=? where userid=? and seq=?";
		Object[] param = new Object[]{pwd,userid,seq};
		jdbcTemplate.update(sql,param);
	}

	/**
	 * 删除密码
	 * @author yaoyz
	 * 2018年1月22日
	 */
	public void deletepwd(String userid, String seq) {
		String sql = "update pwdmanage set status=0 where userid=? and seq=?";
		jdbcTemplate.update(sql,userid,seq);
	}
	
	
}
