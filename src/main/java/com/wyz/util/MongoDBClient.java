/**
 * 
 */
package com.wyz.util;

import java.lang.reflect.Field;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * @author ezforym
 *
 */
public class MongoDBClient {
          private static MongoClient mongoClient = null;
          private static MongoDBClient mongoDBClient = new MongoDBClient();
          
          private MongoDBClient() {
                    if (mongoClient == null) {
                              MongoClientOptions.Builder build = new MongoClientOptions.Builder();
                              build.connectionsPerHost(50); // 与目标数据库能够建立的最大connection数量为50
                              build.threadsAllowedToBlockForConnectionMultiplier(50); // 如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
                              build.maxWaitTime(1000 * 60 * 2);
                              build.connectTimeout(1000 * 60 * 1); // 与数据库建立连接的timeout设置为1分钟
                              MongoClientOptions myOptions = build.build();
                              try {
                                        mongoClient = new MongoClient("127.0.0.1", myOptions);
                              } catch (MongoException e) {
                                        e.printStackTrace();
                              }
                    }
          }
          
          public static MongoDBClient getMongoDBClient() {
                    return mongoDBClient;
          }
          
          public MongoClient getMongoClient() {
                    return mongoClient;
          }
          
          public static boolean inSert(String dbName, String collectionName, Object object) {
                    MongoClient mc = MongoDBClient.getMongoDBClient().getMongoClient();
                    MongoDatabase db = null;
                    MongoCollection<Document> dbCollection = null;
                    if (object != null) {
                              db = mc.getDatabase(dbName);
                              dbCollection = db.getCollection(collectionName); // 获取数据库中指定的collection集合
                              Document documents = new Document();
                              documents = getFiledsInfo(object);
                              dbCollection.insertOne(documents);
                              try {
                              } catch (Exception e) {
                                        // TODO: handle exception
                                        e.printStackTrace();
                              } finally {
                              }
                    }
                    return false;
          }
          
          public static Document getFiledsInfo(Object newo) {
                    Document d = new Document();
                    @SuppressWarnings("rawtypes")
                    Class newuserCla = (Class) newo.getClass();
                    {
                              Field[] newfs = newuserCla.getDeclaredFields();
                              for (int i = 0; i < newfs.length; i++) {
                                        Field newf = newfs[i];
                                        newf.setAccessible(true);
                                        Object val = null;
                                        try {
                                                  val = newf.get(newo);
                                        } catch (IllegalArgumentException e) {
                                                  e.printStackTrace();
                                        } catch (IllegalAccessException e) {
                                                  e.printStackTrace();
                                        }
                                        d.append(newf.getName(), val);
                              }
                    }
                    Class<?> newuserClap = (Class<?>) newo.getClass().getSuperclass();
                    for (;;) {
                              if (newuserClap != null) {
                                        Field[] newfs = newuserClap.getDeclaredFields();
                                        for (int i = 0; i < newfs.length; i++) {
                                                  Field newf = newfs[i];
                                                  newf.setAccessible(true);
                                                  Object val = null;
                                                  try {
                                                            val = newf.get(newo);
                                                  } catch (IllegalArgumentException e) {
                                                            e.printStackTrace();
                                                  } catch (IllegalAccessException e) {
                                                            e.printStackTrace();
                                                  }
                                                  d.append(newf.getName(), val);
                                        }
                                        newuserClap = newuserClap.getSuperclass();
                              } else {
                                        break;
                              }
                    }
                    return d;
          }
}
