package com.huanxin.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.huanxin.modal.Part;

@Service
public class PartService {
	
	@Autowired
    JdbcTemplate jdbcTemplate;
	
	private static final class PartMapper implements RowMapper<Part>{
		public Part mapRow(ResultSet rs, int rowNum) throws SQLException {
			Part p = new Part();
			p.setId(rs.getLong("id"));
			p.setName(rs.getString("name"));
		    p.setCode(rs.getString("code"));
		    p.setVal1(rs.getString("val1"));
		    p.setVal2(rs.getString("val2"));
		    p.setNum(rs.getLong("num"));
		    p.setProcessId(rs.getLong("processid"));
		    p.setStorageId(rs.getLong("storageid"));
		    p.setStockId(rs.getLong("stockid"));
		    p.setOptId(rs.getLong("optid"));
			p.setAddtime(rs.getString("addtime"));
			p.setUpdatetime(rs.getString("updatetime"));
			p.setProcessName(rs.getString("processname"));
			p.setStorageName(rs.getString("storagename"));
			return p;
		}	
	}
	
	/**
	 * ��ѯ����
	 * yaoyz    2018��6��18��
	 */
	public  List<Part> loadParts(String aimSearch, Long length) {
		String filter = "";
		if(aimSearch!="")
			filter = " where p.name like '%"+aimSearch+"%' or p.code like '%"+aimSearch+"%'";
		String sql = "SELECT p.*,pn.name AS processname,s.name AS storagename FROM part AS p "
				+ " LEFT JOIN processname AS pn ON p.processid=pn.id "
				+ " LEFT JOIN store AS s ON s.id=p.storageid "
				+ filter+" limit 0,"+length;
		return jdbcTemplate.query(sql, new PartMapper());
	}

	/**
	 * �������еĿ�����Ϣ
	 * yaoyz    2018��6��18��
	 */
	public List<Map<String, Object>> loadDeparts() {
		String sql = "select * from department";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * ����һ�������е�������Ա��Ϣ
	 * yaoyz    2018��6��18��
	 */
	public List<Map<String, Object>> loadUsers(Long departId) {
		String sql = "select * from user where departid="+departId;
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * ������Ա���ù�װ
	 * yaoyz    2018��6��18��
	 */
	public void borrowParts(Long userId, Long partId, Long departId, Long num) {
		String sql = "insert into part_his(userid,departid,partid,num,status,addtime) values(?,?,?,?,0,now())";
		Object[] param = new Object[]{userId,departId,partId,num};
		jdbcTemplate.update(sql,param);
	}

	/**
	 * ��ܲ�ѯ������Ĺ�����Ϣ
	 * yaoyz    2018��6��18��
	 */
	public List<Map<String, Object>> dealParts(int status) {
		String sql = "SELECT ps.*, u.name AS username, u.code AS usercode, p.name AS partname, p.code AS partcode,d.name AS dname,d.code AS dcode,p.num  AS prenum"
				+ " FROM part_his AS ps  LEFT JOIN user AS u  ON ps.userid = u.id "
				+ " LEFT JOIN part AS p ON ps.partid = p.id LEFT JOIN department AS d ON d.id=ps.departid WHERE ps.status = 0 ORDER BY ps.addtime";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * ��ѯ�����Ϣ
	 * yaoyz    2018��6��18��
	 */
	public List<Map<String, Object>> loadKuGuan() {
		String sql = "select * from user where type=1";
		return jdbcTemplate.queryForList(sql);
	}

	/**
	 * ���ȷ�Ͻ����װ
	 * yaoyz    2018��6��18��
	 */
	public void confirmBorrowParts(Long hisId, Long kuguanId) {
		String sql = "update part_his set managerid="+kuguanId+",status=1,managertime=now() where id="+hisId;
		jdbcTemplate.update(sql);
	}

	/**
	 * �ı乤װ���е�����
	 * yaoyz    2018��6��18��
	 */
	public void updateNum(Long partId, Long borrowNum,boolean addFlag) {
		String sql ="";
		if(addFlag)
			sql = "update part set num=num+"+borrowNum+" where id="+partId;
		else
			sql = "update part set num=num-"+borrowNum+" where id="+partId;
		jdbcTemplate.update(sql);
	}

	public void backParts(Long hisId, Long kuguanId, Long backNum, Boolean backAll) {
		int status=1;
		if(backAll)
			status=2;
		String sql = "update part_his set status="+status+",backid="+kuguanId+",backnum=backnum+"+backNum+",backtime=now() where id="+hisId;
		System.out.println(sql);
		jdbcTemplate.update(sql);
		String sql2 = "insert into part_back(hisid,manageid,num,addtime) values("+hisId+","+kuguanId+","+backNum+",now())";
		System.out.println(sql2);
		jdbcTemplate.update(sql2);
	}

	public Map<String, Object> login(String usercode, String password) {
		String sql = "select * from user where code=? and password=?";
		Object[] param = new Object[]{usercode,password};
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,param);
		if(list.size() > 0)
			return list.get(0);
		return null;
	}

	public Map<String, Object> loadUserById(Long userId) {
		String sql = "select * from user where id="+userId;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list.size()>0)
			return list.get(0);
		return null;
	}

	/**
	 * 修改密码
	 * yaoyz    2018年6月19日
	 */
	public boolean cgpwd(Long userId, String prePwd, String nowPwd) {
		String sql = "update user set password=? where id=? and password=?";
		Object[] param = new Object[]{nowPwd,userId,prePwd};
		int num = jdbcTemplate.update(sql,param);
		if(num>0)
			return true;
		return false;
	}

	public void insertPartCg(Long manageid, Long partId, Long aimNum, int type) {
		String sql = "insert into part_cg(manageid,partid,type,num,addtime) values(?,?,?,?,now())";
		Object[] param = new Object[]{manageid,partId,type,aimNum};
		jdbcTemplate.update(sql,param);
	}

	public long loadProcessById(String process) {
		String sql = "select * from processname where code="+process;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list.size()>0){
			return Long.parseLong(list.get(0).get("id").toString());
		}
		return 0;
	}

	public long loadStorageById(String storage) {
		String sql = "select * from store where code="+storage;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list.size() > 0)
			return Long.parseLong(list.get(0).get("id").toString());
		return 0;
	}

	public void insertNewPart(final Long manageid, final String name, final String code, final String val1, final String val2, final Long num,
			final long storageId, final long processId) {
		final String sql1 = "insert into part(name,code,val1,val2,num,processid,storageid,optid,addtime) values(?,?,?,?,?,?,?,?,now())";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con
						.prepareStatement(sql1,new String[] { "id" });
				int index = 1;
				ps.setString(index++, name);
				ps.setString(index++, code);
				ps.setString(index++, val1);
				ps.setString(index++, val2);
				ps.setLong(index++, num);
				ps.setLong(index++, processId);
				ps.setLong(index++, storageId);
				ps.setLong(index++, manageid);
				return ps;
			}
		}, keyHolder);
		long partId = keyHolder.getKey().longValue();
		
		String sql2 = "insert into part_cg(manageid,partid,type,num,addtime) values(?,?,0,?,now())";
		Object[] param = new Object[]{manageid,partId,num};
		jdbcTemplate.update(sql2,param);

	}

	public long loadPartByCode(String code) {
		String sql = "select * from part where code="+code;
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		if(list.size()>0)
			return Long.parseLong(list.get(0).get("id").toString());
		return 0;
	}

	/**
	 * 下载excel数据的时候，下载入库以及流水信息
	 * yaoyz    2018年6月20日
	 * @return 
	 */
	public List<Map<String, Object>> downRukuLiushui(Integer type, String start, String end,int acttype) {
		String sql = "SELECT u.dname,u.dcode,u.username,u.usercode,p.name,p.code, pc.num,pc.addtime FROM part_cg AS pc "
				+ " LEFT JOIN part AS p ON pc.partid=p.id "
				+ " LEFT JOIN (SELECT user.id,user.name AS username,user.code AS usercode,department.name AS dname,department.code AS dcode FROM user "
				+ " LEFT JOIN department ON user.departid=department.id) AS u ON u.id=pc.manageid where pc.type="+acttype;
		if(type==0 || type.equals(0)){
			String filter =" and pc.addtime > '"+start+"' and pc.addtime < '"+end+"'";
			sql = sql +filter;
		}
		System.out.println("sql----"+sql);
		return jdbcTemplate.queryForList(sql);
		
	}

	public List<Map<String, Object>> loadBorrowLiushui(Integer type, String start, String end) {
		String sql ="SELECT d.name AS dname,d.code AS dcode,u.name AS uname,u.code AS ucode,p.name AS pname,p.code AS pcode,"
				+ " ph.num,ph.status,ph.addtime,ph.managertime,u2.name AS mname,u2.code AS mcode FROM part_his AS ph "
				+ " LEFT JOIN department AS d ON ph.departid=d.id LEFT JOIN user AS u ON u.id=ph.userid "
				+ " LEFT JOIN user AS u2 ON u2.id=ph.managerid LEFT JOIN part AS p ON ph.partid=p.id";
		if(type==0 || type.equals(0)){
			String filter =" where ph.addtime > '"+start+"' and ph.addtime < '"+end+"'";
			sql = sql +filter;
		}
		System.out.println("sql2----"+sql);
		return jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> loadBackLiushui(Integer type, String start, String end) {
		String sql = "SELECT pp.*,pb.num,pb.addtime,ppu.name,ppu.code FROM part_back AS pb  "
				+ " LEFT JOIN( SELECT ph.id,d.name AS dname,d.code AS dcode,u.name AS uname,u.code AS ucode,p.name AS pname,p.code AS pcode "
				+ " FROM part_his AS ph LEFT JOIN department AS d ON ph.departid=d.id LEFT JOIN user AS u ON u.id=ph.userid LEFT JOIN part AS p "
				+ " ON ph.partid=p.id ) AS pp ON pp.id=pb.hisid LEFT JOIN user AS ppu ON ppu.id=pb.manageid";
		if(type==0 || type.equals(0)){
			String filter =" where pb.addtime > '"+start+"' and pb.addtime < '"+end+"'";
			sql = sql +filter;
		}
		System.out.println("sql2---"+sql);
		return jdbcTemplate.queryForList(sql);
	}

}
