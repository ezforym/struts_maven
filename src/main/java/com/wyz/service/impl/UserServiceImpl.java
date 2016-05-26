package com.wyz.service.impl;

import java.util.List;

import com.wyz.dao.IUserDao;
import com.wyz.dao.impl.UserDaoImpl;
import com.wyz.pojo.User;
import com.wyz.service.IUserService;

public class UserServiceImpl implements IUserService {
	private static IUserDao userDao;

	static {
		userDao = new UserDaoImpl();
	}

	public boolean save(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public User find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getCount(int key, long value) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<User> initTop(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> paginate(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> paginate(int key, long value, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public int update(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<User> selectUserByUser(User user) {
		return userDao.selectUserByUser(user);
	}

}
