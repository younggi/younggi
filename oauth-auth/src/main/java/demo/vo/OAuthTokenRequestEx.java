package demo.vo;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import demo.validator.OAuthValidator;

public class OAuthTokenRequestEx extends AbstractOAuthRequestEx {

	public OAuthTokenRequestEx(HttpServletRequest request) throws OAuthSystemException, OAuthProblemException {
		super(new OAuthTokenRequest(request), OAuthTokenRequest.class);
	}
	
	public OAuthTokenRequestEx(OAuthRequest request) {
		super(request, OAuthRequest.class);
		validate();
	}	
	
	private void validate() {
		validate(requestObject,requestClazz,
				Arrays.asList(OAuthValidator.CheckField.client_id,
						OAuthValidator.CheckField.code,
						OAuthValidator.CheckField.redirect_uri,
						OAuthValidator.CheckField.grant_type
						));
	}
	
	public String getGrantType() {		
		return (String)getValue("getGrantType").orElse(null);
	}
	
	public String getCode() {		
		return (String)getValue("getCode").orElse(null);
	}
}
