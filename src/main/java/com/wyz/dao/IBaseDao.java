package com.wyz.dao;

import java.util.List;

public interface IBaseDao<T> {
	/**
	 * 保存对象
	 * 
	 * @param id
	 * @return
	 */
	int save(T t);

	/**
	 * 删除指定的对象
	 * 
	 * @param id
	 * @return
	 */
	int delete(long id);

	/**
	 * 修改对象 操作update
	 * 
	 * @param t
	 */
	int update(T t);

	/**
	 * 查询指定的对象
	 * 
	 * @param id
	 * @return
	 */
	T find(long id);

	/**
	 * 查询数据条数
	 * 
	 * @return 总条数
	 */
	long getCount();

	/**
	 * 查询指定外键值的数据总条数
	 * 
	 * @param key
	 *            外键
	 * @param value
	 *            外键值
	 * @return 总条数
	 */
	long getCount(int key, long value);

	/**
	 * 查询指定条数的数据
	 * 
	 * @param limit
	 *            前几条
	 */
	List<T> initTop(int limit);

	/**
	 * 分页查询 数据集
	 * 
	 * @param start
	 *            数据起始位置 0为第一条
	 * @param limit
	 *            限制的条数 取几条
	 * @return
	 */
	List<T> paginate(int start, int limit);

	/**
	 * 分页查询 指定外键的数据集
	 * 
	 * @param key
	 *            外键名
	 * @param value
	 *            外键值
	 * @param start
	 *            起始位置 0为第一条
	 * @param limit
	 *            限制的条数 取几条
	 * @return
	 */
	List<T> paginate(int key, long value, int start, int limit);
}
