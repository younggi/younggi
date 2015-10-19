package com.sds.janus.oauth2.authserver.request;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;

import com.sds.janus.oauth2.authserver.validator.OAuthValidator;

public class OAuthTokenRequestEx extends AbstractOAuthRequestEx {

	public OAuthTokenRequestEx(HttpServletRequest request) {
		try {
			init(new OAuthTokenRequest(request), OAuthTokenRequest.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public OAuthTokenRequestEx(OAuthRequest request) {
		super(request, OAuthRequest.class);
		validate();
	}	
	
	private void validate() {
		switch(getGrantType()) {
		case "authorization_code":
			validate(requestObject,requestClazz,
					Arrays.asList(OAuthValidator.CheckField.client_id,
							OAuthValidator.CheckField.code,
							OAuthValidator.CheckField.redirect_uri,
							OAuthValidator.CheckField.grant_type
							));
		break;
		case "password":
			validate(requestObject,requestClazz,
					Arrays.asList(OAuthValidator.CheckField.client_id,
							OAuthValidator.CheckField.username,
							OAuthValidator.CheckField.password,
							OAuthValidator.CheckField.grant_type
							));
		break;
		case "client_credentials":
			validate(requestObject,requestClazz,
					Arrays.asList(OAuthValidator.CheckField.client_id,
							OAuthValidator.CheckField.client_id,
							OAuthValidator.CheckField.client_secret,
							OAuthValidator.CheckField.grant_type
							));
		break;			
		}
	}
	
	public String getGrantType() {		
		return (String)getValue("getGrantType").orElse(null);
	}
	
	public String getCode() {		
		return (String)getValue("getCode").orElse(null);
	}
}
