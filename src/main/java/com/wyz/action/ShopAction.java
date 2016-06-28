package com.wyz.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.wyz.pojo.Shop;
import com.wyz.service.IShopMongoService;
import com.wyz.service.IShopService;
import com.wyz.service.impl.ShopServiceImpl;
import com.wyz.util.MongoJsonToObject;
import com.wyz.util.ToJson;

public class ShopAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> session;
	private String _id;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	private static IShopService shopService;
	private static IShopMongoService shopMongoService;
	static {
		shopService = new ShopServiceImpl();
		shopMongoService = new ShopServiceImpl();
	}

	public void insertShop() throws IOException, SQLException {
		HttpServletResponse response = ServletActionContext.getResponse();
		Shop s = new Shop();
		s.setDr(true);
		s.setMoney(2000);
		s.setAddress("重庆");
		s.setName("ym");
		s.setPid(2L);
		session.put("shop", s);
		shopService.save(s);
		shopMongoService.inSert(s);
		ToJson.getJson(s, "data", response);
		// shopMongoService.inSert(s);
		// boolean rs = shopService.save(s);
		// session.put("shop", s);
		// ToJson.getJson(rs, "data", response);
	}

	// 根据ID查询Shop
	public void getShopById() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		Shop x = (Shop) MongoJsonToObject.getObject(new Shop(),
				shopMongoService.queryObject(get_id()));
		ToJson.getJson(x, "data", response);
	}

	public void getListShopById() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("address", "重庆");
		m.put("money", 2000);
		m.put("pid", 2);
		String x = shopMongoService.queryList(m);
		ToJson.getJson(x, "data", response);
	}
}
