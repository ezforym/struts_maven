/**
 * 
 */
package com.wyz.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.wyz.pojo.ShopW;
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
                    ShopW s = new ShopW();
                    s.setAddress("dfsfsfdsggsf");
                    s.setCid(4555L);
                    s.setDr(false);
                    s.setXxx(11);
                    s.setXxxx(111);
                    s.setPrice("fsfs");
                    s.setXxxxxx(4343);
                    s.setName("fdsfsfsfgsgd");
                    // MongoDBClient.getMongoDBClient().inSert("zocoo", s);
                    // ShopW u = (ShopW)
                    // MongoDBClient.getMongoDBClient().queryObject("zocoo",
                    // "57444e5fcd9792196bd0b1cd",new ShopW());
                    // System.out.println(MongoDBClient.getMongoDBClient().deleteCollection("zocoo"));
                    ShopW sw = new ShopW();
                    sw.setPrice("fsfs");
                    Map<String, Object> m = new HashMap<String, Object>();
                    m.put("price", "fsfs");
                    m.put("name", "fdsfsfxsfgsgd");
                    System.out.println(MongoDBClient.getMongoDBClient().queryList("zocoo", m));
                    // MongoDBClient.getMongoDBClient().updateObject("zocoo",
                    // "57445eedcd979278f9dbe137", sw);
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
