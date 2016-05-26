package com.wyz.util;

import net.sf.json.JSONObject;
import java.lang.reflect.Field;

public class MongoJsonToObject {
	public static Object getObject(Object newo, String json) {
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
								if (js.getJSONObject(newf.getName()) != null&&!js.getString(newf.getName()).equals("null"))
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
								if (js.getJSONObject(newf.getName()) != null&&!js.getString(newf.getName()).equals("null"))
									newf.set(newo,
											js.getJSONObject(newf.getName())
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
}
