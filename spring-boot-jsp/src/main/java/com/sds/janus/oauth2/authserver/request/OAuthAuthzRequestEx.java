package com.sds.janus.oauth2.authserver.request;

import java.util.Arrays;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;

import com.sds.janus.oauth2.authserver.validator.OAuthValidator;

public class OAuthAuthzRequestEx extends AbstractOAuthRequestEx {
	
	public OAuthAuthzRequestEx(HttpServletRequest request) {
		try {
			init(new OAuthAuthzRequest(request), OAuthAuthzRequest.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public OAuthAuthzRequestEx(OAuthRequest request) {
		super(request, OAuthRequest.class);
		validate();
	}	
	
	private void validate() {
		validate(requestObject,requestClazz,
				Arrays.asList(OAuthValidator.CheckField.client_id,OAuthValidator.CheckField.response_type));
	}
	
	public String getResponseType() {		
		return (String)getValue("getResponseType").orElse(null);
	}
	
	public String getState() {
		return (String)getValue("getState").orElse(null);
	}
	
	public Set<String> getScopes() {
		return (Set<String>)getValue("getScopes").orElse(null);
	}
	
}
