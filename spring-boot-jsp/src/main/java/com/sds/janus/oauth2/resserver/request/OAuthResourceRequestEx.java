package com.sds.janus.oauth2.resserver.request;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;

public class OAuthResourceRequestEx {
	
	public enum ParamStyle {
		BODY("BODY"),
		QUERY("QUERY"),
		HEADER("HEADER");
		
		private String paramStyle;
		
		ParamStyle(String paramStyle) {
			this.paramStyle = paramStyle;
		}
		
		@Override
		public String toString() {
			return paramStyle;
		}
	}

	private OAuthAccessResourceRequest oAuthRequest = null;
	
	public OAuthResourceRequestEx(HttpServletRequest request, ParamStyle...paramStyles) {
		ParameterStyle [] parameterStyles = new ParameterStyle[paramStyles.length];
		for (int i=0; i<paramStyles.length; i++) {
			parameterStyles[i] = ParameterStyle.valueOf(paramStyles[i].toString()); 
		}
		try {
			oAuthRequest = new OAuthAccessResourceRequest(request, parameterStyles);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public Optional<String> getAccessToken() {
		Optional<String> accessToken = Optional.empty();
		try {
			accessToken = Optional.ofNullable(oAuthRequest.getAccessToken());
		} catch (OAuthSystemException e) {
			throw new RuntimeException(e);
		}
		return accessToken;
	}
}
