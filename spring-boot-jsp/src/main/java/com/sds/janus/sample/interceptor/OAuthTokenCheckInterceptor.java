package com.sds.janus.sample.interceptor;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sds.janus.oauth2.authserver.manager.OAuthTokenManager;
import com.sds.janus.oauth2.resserver.request.OAuthResourceRequestEx;
import com.sds.janus.oauth2.resserver.request.OAuthResourceRequestEx.ParamStyle;

@Component("authorizationCheckInterceptor")
public class OAuthTokenCheckInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(OAuthTokenCheckInterceptor.class);
	
	@Autowired
	private OAuthTokenManager oAuthTokenManager; 
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		logger.info("Execute Prehandle");
		
		OAuthResourceRequestEx oAuthResourceRequestEx = new OAuthResourceRequestEx(request, ParamStyle.HEADER);
		
		Optional<String> token = oAuthResourceRequestEx.getAccessToken();	
		
		if (token.isPresent()) {
			
			
			if (!oAuthTokenManager.checkToken(token.get())) {
				logger.info("TOKEN : {} is invalid", token);
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			} 
			
			logger.info("TOKEN : {} is valid", token);
			
		} else {
			logger.info("Authorization Header is null");			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}

		return super.preHandle(request, response, handler);
	}
}
