package com.wyz.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wyz.dao.IUserDao;
import com.wyz.pojo.User;
import com.wyz.util.SqlMapUtil;

public class UserDaoImpl implements IUserDao {
	private static SqlMapClient sqlmap;

	static {
		SqlMapUtil smu = new SqlMapUtil();
		sqlmap = smu.getInstance();
	}

	@SuppressWarnings("unchecked")
	public List<User> selectUserByUser(User user) {
		List<User> list = new ArrayList<User>();
		try {
			list = sqlmap.queryForList("selectUserByUser", user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int save(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getCount(int key, long value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<User> initTop(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> paginate(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> paginate(int key, long value, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}
}