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
import com.wyz.pojo.Shop;
import com.wyz.pojo.User;
import com.wyz.service.IUserService;
import com.wyz.service.impl.UserServiceImpl;
import com.wyz.util.MongoDBClient;
import com.wyz.util.RedisClient;
import com.wyz.util.ToJson;

/**
 * @author wuyize
 *
 */
public class AuthAction extends ActionSupport implements SessionAware {
          /**
           * 
           */
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
                    RedisClient.getInstance().set("user", list.get(1));
                    Shop s = new Shop();
                    s.setAddress("dfsfsfdsggsf");
                    s.setCid(4555L);
                    s.setDr(false);
                    s.setXxx(11);
                    s.setXxxx(111);
                    s.setXxxxxx(4343);
                    s.setName("fdsfsfsfgsgd");
                    MongoDBClient.getMongoDBClient().inSert("zocoo", s);
                    Shop u = (Shop) MongoDBClient.getMongoDBClient().queryObject("zocoo", "57432ad8cd979268b9f62ee7",
                                        new Shop());
                    System.out.println(u.get_id());
                    session.put("user", list.get(0));
                    ToJson.getJson(list, "user", response);
          }
          
          // 查询所有的Task
          public void session() throws IOException, SQLException {
                    HttpServletResponse response = ServletActionContext.getResponse();
                    User u = (User) session.get("user");
                    ToJson.getJson(u, "user", response);
          }
}
