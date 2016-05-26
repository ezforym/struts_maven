package com.wyz.dao;

import java.util.Map;

/**
 * @author wuyize
 *
 */
public interface IBaseMongoDao<T> {

	/**
	 * 新增对象
	 * 
	 * @param collectionName
	 *            集和名词
	 * @param object
	 *            新增的对象数据
	 * @return 是否新增成功
	 */
	boolean inSert(T o);

	/**
	 * 根据ID查询对象
	 * 
	 * @param cn
	 *            集合名稱
	 * @param _id
	 *            对象ID
	 * @param o
	 *            对象
	 * @return 对象
	 */
	String queryObject(String _id);

	/**
	 * 根据ID查询对象並且刪除
	 * 
	 * @param cn
	 *            集合名稱
	 * @param _id
	 *            對象ID
	 * @param o
	 *            對象
	 * @return 對象json
	 */
	String queryObjectAndDelete(String _id);

	/**
	 * 刪除集合
	 * 
	 * @param cn
	 *            集合名稱
	 * @return 是否刪除成功
	 */
	boolean deleteCollection();

	/**
	 * 根據ID刪除數據
	 * 
	 * @param cn
	 *            集合名稱
	 * @param _id
	 *            對象ID
	 * @return 是否刪除成功
	 */
	boolean deleteObject(String _id);

	/**
	 * 通過ID來更新對象
	 * 
	 * @param cn
	 *            集合名稱
	 * @param _id
	 *            對象ID
	 * @param o
	 *            對象
	 * @return 更新是否成功
	 */
	boolean updateObject(String _id, T o);

	/**
	 * 通过条件查询对象集合
	 * 
	 * @param cn
	 *            集合名称
	 * @param m
	 *            条件
	 * @return 对象json数组
	 */
	String queryList(Map<String, Object> m);

}
