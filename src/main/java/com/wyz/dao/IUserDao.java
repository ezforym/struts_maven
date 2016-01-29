package com.wyz.dao;

import java.util.List;

import com.wyz.pojo.User;

public interface IUserDao extends IBaseDao<User> {
	// 通過User查詢User
	List<User> selectUserByUser(User user);
}