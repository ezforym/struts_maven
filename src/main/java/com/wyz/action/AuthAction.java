/**
 * 
 */
package com.wyz.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.wyz.pojo.User;
import com.wyz.service.IUserService;
import com.wyz.service.impl.UserServiceImpl;
import com.wyz.util.RedisClient;
import com.wyz.util.ToJson;

/**
 * @author wuyize
 *
 */
public class AuthAction extends ActionSupport implements SessionAware {
	private Map<String, Object> session;

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	private static IUserService userService;

	static {
		userService = new UserServiceImpl();
	}

	private static final long serialVersionUID = 1L;

	// 查询所有的Task
	public void auth() throws IOException, SQLException {
		HttpServletResponse response = ServletActionContext.getResponse();
		User user = new User();
		user.setAddress("1");
		List<User> list = userService.selectUserByUser(user);
		System.out.println(list.size());
		session.put("user", user);
		RedisClient.getInstance().set("user", user);
		ToJson.getJson(user, "user", response);
	}

	// 查询所有的Task
	public void session() throws IOException, SQLException {
		HttpServletResponse response = ServletActionContext.getResponse();
		User u = (User) session.get("user");
		ToJson.getJson(u, "user", response);
	}
}
