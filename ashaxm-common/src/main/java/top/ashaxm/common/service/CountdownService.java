package top.ashaxm.common.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 倒计时
 * @author yaoyz
 * 2018年1月16日
 */
@Service
public class CountdownService {
	Log log = LogFactory.getLog(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * 添加新的倒计时
	 * @author yaoyz
	 * 2018年1月16日
	 */
	public void addnew(String title, String content, String settime,String loginuserid) {
		String sql ="insert into countdown(title,content,userid,settime,addtime) values(?,?,?,?,now())";
		Object[] params=new Object[]{title,content,loginuserid,settime};
		try {
			jdbcTemplate.update(sql,params);
		} catch (Exception e) {
			log.error(this,e);
		}
	}

	/**
	 * 加载所有的倒计时
	 * @author yaoyz
	 * 2018年1月16日
	 */
	public List<Map<String, Object>> loadallcount(String loginuserid) {
		String sql = "SELECT id,title,content,userid,TYPE,STATUS,DATE_FORMAT(settime,'%Y-%m-%d') AS settime FROM countdown WHERE STATUS =1 and userid=? order by settime";
		return jdbcTemplate.queryForList(sql,loginuserid);				
	}
}
