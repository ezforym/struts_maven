package com.wyz.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.wyz.pojo.User;

import net.sf.json.JSONObject;

public class ToJson {
          private ToJson() {
          }
          
          private static class ToJsonHolder {
                    private static ToJson json = new ToJson();
          }
          
          public static ToJson getJson() {
                    return ToJsonHolder.json;
          }
          
          public static String getJson(Object o, String title) {
                    JSONObject json = new JSONObject();
                    json.accumulate(title, o);
                    return json.toString();
          }
          
          public static void getJson(Object o, String title, HttpServletResponse response) throws IOException {
                    {
                              response.setCharacterEncoding("utf-8");
                              PrintWriter out;
                              out = response.getWriter();
                              User user = new User();
                              user.setAddress("1");
                              System.out.println();
                              out.println(ToJson.getJson(o, "user"));
                              out.flush();
                              out.close();
                    }
          }
}
