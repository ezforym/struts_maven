/**
 * 
 */
package com.wyz.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import net.sf.json.JSONObject;

/**
 * @author ezforym
 *
 */
public class MongoDBClient {
	private static MongoDBClient mongoDBClient = new MongoDBClient();;
	private static String dbname = null;
	private static String dbhost = null;
	private static MongoDatabase db = null;
	private static MongoClient mongoClient = null;

	private MongoDBClient() {
	}

	static {
		ResourceBundle resourceBundle = ResourceBundle
				.getBundle("com/wyz/config/mongo");
		dbname = resourceBundle.getString("mongo.db");
		dbhost = resourceBundle.getString("mongo.host");
		MongoClientOptions.Builder build = new MongoClientOptions.Builder();
		build.connectionsPerHost(50); // 与目标数据库能够建立的最大connection数量为50
		build.threadsAllowedToBlockForConnectionMultiplier(50); // 如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
		build.maxWaitTime(1000 * 60 * 2);
		build.connectTimeout(1000 * 60 * 1); // 与数据库建立连接的timeout设置为1分钟
		MongoClientOptions myOptions = build.build();
		try {
			mongoClient = new MongoClient(dbhost, myOptions);
			db = mongoClient.getDatabase(dbname);

		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	public static MongoDBClient getMongoDBClient() {
		return mongoDBClient;
	}

	/**
	 * 通过条件查询对象集合
	 * 
	 * @param cn
	 *            集合名称
	 * @param m
	 *            条件
	 * @return 对象json数组
	 */
	public String queryList(String cn, Map<String, Object> m) {
		String json = "";
		MongoCollection<Document> dbCollection = db.getCollection(cn);
		Bson filter = new BasicDBObject(m);
		FindIterable<Document> x = dbCollection.find(filter);
		MongoCursor<Document> it = x.iterator();
		boolean t = false;
		int i = 0;
		while (it.hasNext()) {
			Document sssx = it.next();
			if (i == 0) {
				json = json + sssx.toJson();
			} else {
				json = json + "," + sssx.toJson();
			}
			t = true;
			i++;
		}
		if (t) {
			json = "[" + json + "]";
		}
		return json.equals("") ? null : json;
	}

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
	public boolean updateObject(String cn, String _id, Object o) {
		MongoCollection<Document> dbCollection = db.getCollection(cn);
		long result = 0;
		if (dbCollection != null) {
			Bson filter = new BasicDBObject("_id", new ObjectId(_id));
			Document documents = getFiledsInfo(o);
			UpdateResult x = dbCollection.replaceOne(filter, documents);
			result = x.getMatchedCount();
		}
		return result == 1;
	}

	/**
	 * 根據ID刪除數據
	 * 
	 * @param cn
	 *            集合名稱
	 * @param _id
	 *            對象ID
	 * @return 是否刪除成功
	 */
	public boolean deleteObject(String cn, String _id) {
		MongoCollection<Document> dbCollection = db.getCollection(cn);
		long result = 0;
		if (dbCollection != null)
			try {
				Bson filter = new BasicDBObject("_id", new ObjectId(_id));
				DeleteResult x = dbCollection.deleteOne(filter);
				result = x.getDeletedCount();
			} catch (Exception e) {
				return false;
			}
		return result == 1;
	}

	/**
	 * 刪除集合
	 * 
	 * @param cn
	 *            集合名稱
	 * @return 是否刪除成功
	 */
	public boolean deleteCollection(String cn) {
		MongoCollection<Document> dbCollection = db.getCollection(cn);
		if (dbCollection != null) {
			try {
				dbCollection.drop();
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

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
	public String queryObjectAndDelete(String cn, String _id) {
		MongoCollection<Document> dbCollection = null;
		Document s = null;
		if (_id != null) {
			dbCollection = db.getCollection(cn);
			if (dbCollection != null) {
				Bson filter = new BasicDBObject("_id", new ObjectId(_id));
				s = dbCollection.findOneAndDelete(filter);
			}
		}
		return s != null ? s.toJson() : null;
	}

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
	public String queryObject(String cn, String _id) {
		MongoCollection<Document> dbCollection = null;
		Document s = null;
		if (_id != null) {
			dbCollection = db.getCollection(cn);
			if (dbCollection != null) {
				Bson filter = new BasicDBObject("_id", new ObjectId(_id));
				FindIterable<Document> d = dbCollection.find(filter);
				if (d != null) {
					s = d.first();
				}
			}
		}
		return s != null ? s.toJson() : null;
	}

	/**
	 * 新增对象
	 * 
	 * @param collectionName
	 *            集和名词
	 * @param object
	 *            新增的对象数据
	 * @return 是否新增成功
	 */
	public boolean inSert(String collectionName, Object object) {
		MongoCollection<Document> dbCollection = null;
		if (object != null) {
			try {
				dbCollection = db.getCollection(collectionName); // 获取数据库中指定的collection集合
				Document documents = new Document();
				documents = getFiledsInfo(object);
				dbCollection.insertOne(documents);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	// 解析對象
	public Document getFiledsInfo(Object newo) {
		Document d = new Document();
		@SuppressWarnings("rawtypes")
		Class newuserCla = (Class) newo.getClass();
		{
			Field[] newfs = newuserCla.getDeclaredFields();
			for (int i = 0; i < newfs.length; i++) {
				Field newf = newfs[i];
				newf.setAccessible(true);
				String type = newf.getType().toString();
				if (type.endsWith("long") || type.endsWith("String")
						|| type.endsWith("Long") || type.endsWith("int")
						|| type.endsWith("boolean") || type.endsWith("Integer")) {
					Object val = null;
					try {
						val = newf.get(newo);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					if (!newf.getName().equals("_id"))
						d.append(newf.getName(), val);
				}
			}
		}
		Class<?> newuserClap = (Class<?>) newo.getClass().getSuperclass();
		while (newuserClap != null) {
			Field[] newfs = newuserClap.getDeclaredFields();
			for (int i = 0; i < newfs.length; i++) {
				Field newf = newfs[i];
				newf.setAccessible(true);
				String type = newf.getType().toString();
				if (type.endsWith("long") || type.endsWith("String")
						|| type.endsWith("Long") || type.endsWith("int")
						|| type.endsWith("boolean") || type.endsWith("Integer")) {
					Object val = null;
					try {
						val = newf.get(newo);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					if (!newf.getName().equals("_id"))
						d.append(newf.getName(), val);
				}
			}
			newuserClap = newuserClap.getSuperclass();
		}
		return d;
	}

	// 解析Ducument
	public Object getObject(Object newo, String json) {
		JSONObject js = JSONObject.fromObject(json);
		@SuppressWarnings("rawtypes")
		Class newuserCla = (Class) newo.getClass();
		{
			Field[] newfs = newuserCla.getDeclaredFields();
			for (int i = 0; i < newfs.length; i++) {
				Field newf = newfs[i];
				newf.setAccessible(true);
				if (js.get(newf.getName()) != null) {
					try {
						if (newf.getName().equals("_id")) {
							newf.set(newo, js.getJSONObject("_id").get("$oid"));
						} else {
							String type = newf.getType().toString();
							System.out.println("================?" + type);
							if (type.endsWith("long") || type.endsWith("Long")) {
								if (js.getJSONObject(newf.getName()) != null
										&& !js.getString(newf.getName())
												.equals("null"))
									newf.set(newo,
											js.getJSONObject(newf.getName())
													.getLong("$numberLong"));
							} else {
								newf.set(newo, js.get(newf.getName()));
							}
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		Class<?> newuserClap = (Class<?>) newo.getClass().getSuperclass();
		while (newuserClap != null) {
			Field[] newfs = newuserClap.getDeclaredFields();
			for (int i = 0; i < newfs.length; i++) {
				Field newf = newfs[i];
				newf.setAccessible(true);
				if (js.get(newf.getName()) != null) {
					try {
						if (newf.getName().equals("_id")) {
							newf.set(newo, js.getJSONObject("_id").get("$oid"));
						} else {
							String type = newf.getType().toString();
							System.out.println("================?" + type);
							if (type.endsWith("long") || type.endsWith("Long")) {
								if (js.getJSONObject(newf.getName()) != null
										&& !js.getString(newf.getName())
												.equals("null"))
									newf.set(newo,
											js.getJSONObject(newf.getName())
													.getLong("$numberLong"));
							} else {
								newf.set(newo, js.get(newf.getName()));
							}
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			newuserClap = newuserClap.getSuperclass();
		}
		return newo;
	}

	// 解析對象
	public Map<String, Object> getMap(Object newo) {
		Map<String, Object> m = new HashMap<String, Object>();
		@SuppressWarnings("rawtypes")
		Class newuserCla = (Class) newo.getClass();
		{
			Field[] newfs = newuserCla.getDeclaredFields();
			for (int i = 0; i < newfs.length; i++) {
				Field newf = newfs[i];
				newf.setAccessible(true);
				String type = newf.getType().toString();
				if (type.endsWith("long") || type.endsWith("String")
						|| type.endsWith("Long") || type.endsWith("int")
						|| type.endsWith("boolean") || type.endsWith("Integer")) {
					Object val = null;
					try {
						val = newf.get(newo);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					if (!newf.getName().equals("_id"))
						if (val != null)
							m.put(newf.getName(), val);
				}
			}
		}
		Class<?> newuserClap = (Class<?>) newo.getClass().getSuperclass();
		while (newuserClap != null) {
			Field[] newfs = newuserClap.getDeclaredFields();
			for (int i = 0; i < newfs.length; i++) {
				Field newf = newfs[i];
				newf.setAccessible(true);
				String type = newf.getType().toString();
				if (type.endsWith("long") || type.endsWith("String")
						|| type.endsWith("Long") || type.endsWith("int")
						|| type.endsWith("boolean") || type.endsWith("Integer")) {
					Object val = null;
					try {
						val = newf.get(newo);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					if (!newf.getName().equals("_id"))
						if (val != null)
							m.put(newf.getName(), val);
				}
			}
			newuserClap = newuserClap.getSuperclass();
		}
		return m;
	}

}
