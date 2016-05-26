package com.wyz.service.impl;

import java.util.List;
import java.util.Map;

import com.wyz.dao.IShopDao;
import com.wyz.dao.IShopMongoDao;
import com.wyz.dao.impl.ShopDaoImpl;
import com.wyz.pojo.Shop;
import com.wyz.service.IShopMongoService;
import com.wyz.service.IShopService;

public class ShopServiceImpl implements IShopService, IShopMongoService {
	private static IShopDao shopDao;

	private static IShopMongoDao shopMongoDao;
	static {
		shopDao = new ShopDaoImpl();
		shopMongoDao = new ShopDaoImpl();
	}

	@Override
	public boolean save(Shop t) {
		return shopDao.save(t) == 1;
	}

	@Override
	public int delete(long id) {
		return shopDao.delete(id);
	}

	@Override
	public int update(Shop t) {
		return shopDao.update(t);
	}

	@Override
	public Shop find(long id) {
		return shopDao.find(id);
	}

	@Override
	public long getCount() {
		return shopDao.getCount();
	}

	@Override
	public long getCount(int key, long value) {
		return shopDao.getCount(key, value);
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

	@Override
	public boolean inSert(Shop o) {
		return shopMongoDao.inSert(o);
	}

	@Override
	public String queryObject(String _id) {
		return shopMongoDao.queryObject(_id);
	}

	@Override
	public String queryObjectAndDelete(String _id) {
		return shopMongoDao.queryObjectAndDelete(_id);
	}

	@Override
	public boolean deleteCollection() {
		return shopMongoDao.deleteCollection();
	}

	@Override
	public boolean deleteObject(String _id) {
		return shopMongoDao.deleteObject(_id);
	}

	@Override
	public boolean updateObject(String _id, Shop o) {
		return shopMongoDao.updateObject(_id, o);
	}

	@Override
	public String queryList(Map<String, Object> m) {
		return shopMongoDao.queryList(m);
	}

}
