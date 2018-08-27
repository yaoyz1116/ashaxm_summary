package com.ashaxm.personal.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * �����¼�
 * yaoyz    2018.6.27
 */
@Service
public class NoticeService {
	
	Log log = LogFactory.getLog(getClass());

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * ����������
	 * yaoyz    2018��6��27��
	 */
	public void addNotice(Long userId, String formId, String title, String desc, String noticeTime) {
		try {
			String sql = "insert into notice(userid,formid,title,des,noticetime,status,addtime) values(?,?,?,?,?,0,now())";
			Object[] param = new Object[]{userId,formId,title,desc,noticeTime};
			jdbcTemplate.update(sql,param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ɾ������
	 * yaoyz    2018��6��27��
	 */
	public void deleteNotice(Long noticeId) {
		String sql = "update notice set status=-1 where id="+noticeId;
		jdbcTemplate.update(sql);
	}

	/**
	 * �޸�������Ϣ
	 * yaoyz    2018��6��27��
	 */
	public void updateNotice(Long noticeId, String title, String desc, String noticeTime) {
		String sql = "update notice set title=?,desc=?,noticetime=? where id=?";
		Object[] param = new Object[]{title,desc,noticeTime,noticeId};
		jdbcTemplate.update(sql,param);
	}

	/**
	 * ��ѯ
	 * yaoyz    2018��6��27��
	 */
	public List<Map<String, Object>> loadNotices(Long userId) {
		String sql = "select id,userid,title,des,status,DATE_FORMAT(noticetime,'%m-%d %H:%i') as noticetime from notice where userid="+userId+" and status!=-1 order by status,noticetime";
		return jdbcTemplate.queryForList(sql);
	}
}
