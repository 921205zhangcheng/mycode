package com.xh.sdk.common;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xh.sdk.redis.RedisClientTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: Ambitor springMVC拦截器 判断session中用户是否过期 Date:
 * 13-6-27 Time: 下午7:31 To change this template use File | Settings | File
 * Templates.
 * 
 * @author 罗涛
 */

public class userFilter implements Filter {

	private RedisClientTemplate redis;
	Logger logger = LoggerFactory.getLogger(Filter.class);
	public static final Map<String, String> balckIp = new HashMap<String, String>(); 
	static {  
		balckIp.put("183.61.51", "true");  
		balckIp.put("183.232.175", "true");  
		balckIp.put("183.3.234", "true");  
		balckIp.put("183.60.163", "true");  
		balckIp.put("180.153.228", "true");  
	}
	public String[] allowUrls = { "sdk","order","rd","otherSdk","manage","index"};

	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}

	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if (!(servletRequest instanceof HttpServletRequest)
				|| !(servletResponse instanceof HttpServletResponse)) {
			throw new ServletException(
					"OncePerRequestFilter just supports HTTP requests");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		HttpSession session = httpRequest.getSession(true);
		String ip = getClientIp(httpRequest);
		ip = ip.substring(0,ip.lastIndexOf("."));
		if("true".equals(balckIp.get(ip))){
			return;
		}
		String requestUrl = httpRequest.getRequestURI().replace(
				httpRequest.getContextPath(), "");
		if (null != allowUrls && allowUrls.length >= 1)
			for (String url : allowUrls) {
				if (requestUrl.contains(url)) {
					filterChain.doFilter(servletRequest, servletResponse);
					return;
				}
			}

//		channelInfo user = (channelInfo) session.getAttribute("userInfo");
		if (false) {
			//System.out.println("user is not null");
			
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		} else {
			// 未登录 跳转到登录页面
			filterChain.doFilter(servletRequest, servletResponse);
			//httpResponse.sendRedirect("/web/login.html");
			return;
		}
	}


	/**
	 * 判断是否为Ajax请求
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 是true, 否false
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/api");
		// String requestType = request.getHeader("X-Requested-With");
		// return requestType != null && requestType.equals("XMLHttpRequest");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		/* 如果需要注入，请取消注释 */
//		 ServletContext servletContext = filterConfig.getServletContext();
//		 WebApplicationContext applicationContext = (WebApplicationContext)
//		 servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//		 redis = applicationContext.getBean(RedisClientTemplate.class);
//		 if (null == topConstantsImpl) {
//			 topConstantsImpl = applicationContext.getBean(TopConstantsImpl.class);
//		 }
	}

	@Override
	public void destroy() {
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}
	
	
	/**
	 * 透过反向代理获取用户IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");

		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");

		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();

		}
		if (StringUtils.isNotBlank(ip)) {
			ip = ip.split(",")[0];
		}
		return ip;

	}

}
