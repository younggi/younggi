package com.sds.janus.sample.interceptor;

import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class SessionCheckInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SessionCheckInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		logger.info("Execute Prehandle");
		Optional<String> token = Optional.ofNullable(request.getHeader("Authorization"));
		
		if (!token.isPresent()) {
			logger.info("Authorization Header is null");
			HttpSession session = request.getSession();
			token = Optional.ofNullable((String) session.getAttribute("login"));
		}
		
		if (!token.isPresent()) {

			StringBuffer redirectCommand = new StringBuffer();
			redirectCommand.append("/loginpage?redirect=");

			StringBuffer redirectParam = new StringBuffer();
			redirectParam.append(request.getRequestURI());			
			Enumeration<String> params = request.getParameterNames();
			String param = null;
			if (params.hasMoreElements()) {
				param = params.nextElement();
				redirectParam.append("?")
					.append(param)
					.append("=")
					.append(request.getParameter(param));
			}
			while (params.hasMoreElements()) {
				param = params.nextElement();
				redirectParam.append("&")
					.append(param)
					.append("=")
					.append(request.getParameter(param));
			}
			logger.info("original : {}", redirectParam.toString() );
			logger.info("encoded : {}", URLEncoder.encode(redirectParam.toString(),"UTF-8") );
			response.sendRedirect(redirectCommand.toString()+URLEncoder.encode(redirectParam.toString(),"UTF-8"));
			return false;
		}
		
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
		logger.info("Execute PostHandle");
		
		super.postHandle(request, response, handler, modelAndView);
	}
}
