package com.wyz.filter;

import java.io.IOException;

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

/**
 * Created by Jacky on 2016/4/7.
 */
public class SecurityFilter implements Filter {
	public static final String INDEX = "/";
	public static final Log LOG = LogFactory.getLog(SecurityFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		System.out.println("request============>" + request);
		String method = request.getMethod();
		String requestURI = request.getRequestURI();

		if ("OPTIONS".equals(method)) {
			handleCros(request, response);
			chain.doFilter(request, response);
			return;
		}

		if (INDEX.equals(requestURI)) {
			handleCros(request, response);
			chain.doFilter(request, response);
			return;
		}

		// 重新包装ServletRequest
		if ("POST".equals(method) || "PUT".equals(method)) {
			request = new MultiReadHttpServletRequest(request);
		}

		// // Token验证
		// if (!LOGIN.equals(requestURI) && !CODE.equals(requestURI)
		// && !CHANGEPSD.equals(requestURI)) {
		// String token = request.getParameter(TOKEN);
		// if (StringUtils.isEmpty(token)) {
		// token = request.getHeader(TOKEN);
		// }
		// if (StringUtils.isEmpty(token) || !userTokenService.isValid(token)) {
		// //
		// System.out.println("===============================================token>error");
		// response.sendError(HttpServletResponse.SC_FORBIDDEN);
		// return;
		// }
		// }
		//
		// // SignUtils安全验证
		// if (!SecuritySign.isValid(request, multiRead)) {
		// response.sendError(HttpServletResponse.SC_FORBIDDEN);
		// //
		// System.out.println("===============================================sign>error");
		// return;
		// }

		LOG.debug("Encodeing: " + request.getCharacterEncoding());
		handleCros(request, response);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

	private void handleCros(HttpServletRequest request,
			HttpServletResponse response) {
		String origin = request.getHeader("origin");
		response.addHeader("Access-Control-Allow-Origin", origin);
		response.addHeader("Access-Control-Allow-Methods",
				"POST,GET,PUT,DELETE");
		response.addHeader("Access-Control-Allow-Headers",
				"Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
		response.addHeader("Access-Control-Allow-Credentials", "true");
	}
}
