package com.sds.janus.sample.interceptor;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component("authorizationCheckInterceptor")
public class AuthorizationCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationCheckInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		logger.info("Execute Prehandle");
		
		Optional<String> token = Optional.ofNullable(request.getHeader("Authorization"));
		
		if (token.isPresent()) {
			logger.info("TOKEN : {}", token);
			String[] tokens = token.toString().split(" ");
			if (tokens.length == 2) {
				logger.info("Type : {}" , tokens[0]);
				logger.info("Value : {}" , tokens[1]);
			} else {
				logger.info("Authorization Header is null");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			}

		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}

		return super.preHandle(request, response, handler);
	}
}
