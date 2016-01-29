/**
 * 
 */
package com.wyz.service;

/**
 * @author wuyize
 *
 */

import java.util.List;

import com.wyz.pojo.User;

public interface IUserService extends IBaseService<User> {
	// 通過User查詢User
	List<User> selectUserByUser(User user);
}