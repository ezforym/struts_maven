/**
 * 
 */
package com.wyz.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wyz.pojo.User;
import com.wyz.service.IUserService;
import com.wyz.service.impl.UserServiceImpl;
import com.wyz.util.ToJson;

/**
 * @author wuyize
 *
 */
public class AuthAction extends ActionSupport {
	/**
	 * 
	 */
	private static IUserService userService;

	static {
		userService = new UserServiceImpl();
	}

	private static final long serialVersionUID = 1L;

	// 查询所有的Task
	public void auth() throws IOException, SQLException {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		PrintWriter out;
		out = response.getWriter();
		User user = new User();
		user.setAddress("1");
		List<User> list = userService.selectUserByUser(user);
		out.println(ToJson.getJson(list, "user"));
		out.flush();
		out.close();
	}
}
