package com.wyz.util;

import net.sf.json.JSONObject;

public class ToJson {
	public static String getJson(Object o, String title) {
		JSONObject json = new JSONObject();
		json.accumulate(title, o);
		return json.toString();
	}
}
