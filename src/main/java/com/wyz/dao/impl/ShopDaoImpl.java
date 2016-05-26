package com.wyz.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wyz.dao.IShopDao;
import com.wyz.dao.IShopMongoDao;
import com.wyz.pojo.Shop;
import com.wyz.util.MongoDBClient;
import com.wyz.util.SqlMapUtil;

public class ShopDaoImpl implements IShopMongoDao, IShopDao {
	private static SqlMapClient sqlmap;
	private static MongoDBClient mongoDBClient;
	private static final String cn = "shop";
	static {
		sqlmap = SqlMapUtil.getSqlMapUtil().getInstance();
		mongoDBClient = MongoDBClient.getMongoDBClient();
	}

	@Override
	public boolean inSert(Shop o) {
		return mongoDBClient.inSert(cn, o);
	}

	@Override
	public String queryObject(String _id) {
		return mongoDBClient.queryObject(cn, _id);
	}

	@Override
	public String queryObjectAndDelete(String _id) {
		return mongoDBClient.queryObjectAndDelete(cn, _id);
	}

	@Override
	public boolean deleteCollection() {
		return mongoDBClient.deleteCollection(cn);
	}

	@Override
	public boolean deleteObject(String _id) {
		return mongoDBClient.deleteObject(cn, _id);
	}

	@Override
	public boolean updateObject(String _id, Shop o) {
		return mongoDBClient.updateObject(cn, _id, o);
	}

	@Override
	public String queryList(Map<String, Object> m) {
		return mongoDBClient.queryList(cn, m);
	}

	@Override
	public int save(Shop t) {
		try {
			Object x = sqlmap.insert("insertShop", t);
			x.toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Shop t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Shop find(long id) {
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
	public List<Shop> initTop(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Shop> paginate(int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Shop> paginate(int key, long value, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

}
