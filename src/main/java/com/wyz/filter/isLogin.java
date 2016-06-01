package com.wyz.filter;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.wyz.pojo.User;

public class isLogin extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		System.out.println("1=====>" + ctx.getName());
		User t = (User) ActionContext.getContext().getSession().get("user");
		if (!ctx.getName().equals("auth")
				&& t == null
				&& invocation.getInvocationContext().getParameters()
						.get("code") == null) {
			return "login";
		} else
			return invocation.invoke();
	}
}