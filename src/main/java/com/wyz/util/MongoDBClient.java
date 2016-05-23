/**
 * 
 */
package com.wyz.util;

import java.lang.reflect.Field;
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
import com.mongodb.client.MongoDatabase;

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
                    ResourceBundle resourceBundle = ResourceBundle.getBundle("com/wyz/config/mongo");
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
          
          public Object queryObject(String cn, String _id, Object o) {
                    MongoCollection<Document> dbCollection = null;
                    if (_id != null) {
                              Bson filter = new BasicDBObject("_id", new ObjectId(_id));
                              dbCollection = db.getCollection(cn);
                              FindIterable<Document> d = dbCollection.find(filter);
                              Document s = d.first();
                              getObject(o, s.toJson());
                              System.out.println(s.toJson());
                    }
                    return o;
          }
          
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
                                                                      newf.set(newo, js.getJSONObject("_id")
                                                                                          .get("$oid"));
                                                            } else {
                                                                      String type = newf.getType().toString();
                                                                      System.out.println("================?" + type);
                                                                      if (type.endsWith("long")
                                                                                          || type.endsWith("Long")) {
                                                                                newf.set(newo, js.getJSONObject(
                                                                                                    newf.getName())
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
                                                                      newf.set(newo, js.getJSONObject("_id")
                                                                                          .get("$oid"));
                                                            } else {
                                                                      String type = newf.getType().toString();
                                                                      System.out.println("================?" + type);
                                                                      if (type.endsWith("long")
                                                                                          || type.endsWith("Long")) {
                                                                                newf.set(newo, js.getJSONObject(
                                                                                                    newf.getName())
                                                                                                    .get("$numberLong"));
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
          
          // 新增对象
          public boolean inSert(String collectionName, Object object) {
                    MongoCollection<Document> dbCollection = null;
                    if (object != null) {
                              dbCollection = db.getCollection(collectionName); // 获取数据库中指定的collection集合
                              Document documents = new Document();
                              documents = getFiledsInfo(object);
                              dbCollection.insertOne(documents);
                              try {
                              } catch (Exception e) {
                                        e.printStackTrace();
                              } finally {
                              }
                    }
                    return false;
          }
          
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
                                        if (type.endsWith("long") || type.endsWith("String") || type.endsWith("Long")
                                                            || type.endsWith("int") || type.endsWith("boolean")
                                                            || type.endsWith("Integer")) {
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
                                        if (type.endsWith("long") || type.endsWith("String") || type.endsWith("Long")
                                                            || type.endsWith("int") || type.endsWith("boolean")
                                                            || type.endsWith("Integer")) {
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
}
