/**
 * 
 */
package com.wyz.filter;

/**
 * @author Administrator
 *
 */
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.wyz.pojo.User;

public class DispatcherFilter implements Filter {
	/**
	 * 
	 */

	private static final Log logger = LogFactory.getLog(DispatcherFilter.class);

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRes = (HttpServletResponse) res;
		String reqUrl = httpReq.getServletPath();
		boolean isFolder = false;
		if (reqUrl.equals("/auth")) {
			filterChain.doFilter(req, res);
		} else {
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			User u = (User) session.get("user");
			if (u != null) {
				if (reqUrl.endsWith("/")) { // url格式是....pro/lan时所做的处理。
					reqUrl = reqUrl.substring(0, reqUrl.length() - 1);
					isFolder = true;
				}
				logger.debug("url-logger:" + reqUrl);
				if (reqUrl.indexOf(".") == -1) { // 一个页面里的js文件引入，CSS文件引入，图片引入都是url，都要

					// 在这里处理

					logger.debug("access url :" + reqUrl);

					// 这里用getClass().getResourceAsStream这个方法是为了得到page.properties的真实地址
					InputStream is = getClass().getResourceAsStream(
							"/com/wyz/config/url.properties");
					Properties prop = new Properties();
					prop.load(is);
					String disPatchUrl = prop.getProperty(reqUrl);
					logger.debug("dispatch Url :" + disPatchUrl);
					if (disPatchUrl != null && !"".equals(disPatchUrl)) {
						if (isFolder)
							httpRes.sendRedirect(disPatchUrl);
						else
							httpReq.getRequestDispatcher(disPatchUrl).forward(
									req, res);
					} else {
						filterChain.doFilter(req, res);
					}
				} else { // 当一些请求是js引入文件的url，或者是一些图片的url时所做的处理，这样图片的地址

					// 就不会被过滤掉，不然你页面上的图片不能正常显示
					filterChain.doFilter(req, res);
				}
			} else {
				httpRes.sendRedirect("/error.jsp");
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}