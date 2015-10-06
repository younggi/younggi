package demo.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.vo.OAuthAuthzRequestEx;
import demo.vo.OAuthAuthzResponseEx;
import demo.vo.OAuthRequest;
import demo.vo.OAuthTokenRequestEx;

@RestController
public class OAuth2Controller {
	
	public static final String RESPONSE_TYPE_CODE = "code";
	public static final String RESPONSE_TYPE_TOKEN = "token";
	
	@RequestMapping("oauth2/authorize1")
	public ResponseEntity<String> authorize1(OAuthRequest oAuthRequest) throws URISyntaxException {
		
		OAuthAuthzRequestEx oAuthAuthzRequestEx = new OAuthAuthzRequestEx(oAuthRequest);
		
		validateRedirectionURI(oAuthAuthzRequestEx);

		OAuthAuthzResponseEx oAuthAuthzResponseEx = 
				new OAuthAuthzResponseEx(oAuthAuthzRequestEx, "d05b1c3a9fb338102eb4a26ebc0fe913");
				
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(oAuthAuthzResponseEx.getLocationUri());
		
		return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
	}
	
	private void validateRedirectionURI(OAuthAuthzRequestEx oAuthzRequest) {
		System.out.println(oAuthzRequest.getClientId()
				+"\n"	+ oAuthzRequest.getClientSecret()
				+"\n"	+ oAuthzRequest.getRedirectURI()
				+"\n"	+ oAuthzRequest.getResponseType()
				+"\n"	+ oAuthzRequest.getState()
				+"\n"	+ oAuthzRequest.getScopes());
	}
	
	@RequestMapping("oauth2/authorize2")
	public void authorize(HttpServletRequest request, HttpServletResponse response) 
			throws OAuthSystemException, OAuthProblemException, IOException {

		OAuthAuthzRequestEx oAuthAuthzRequestEx = new OAuthAuthzRequestEx(request);
		
		validateRedirectionURI(oAuthAuthzRequestEx);
		
		OAuthAuthzResponseEx oAuthAuthzResponseEx = 
				new OAuthAuthzResponseEx(oAuthAuthzRequestEx, "d05b1c3a9fb338102eb4a26ebc0fe913");

		response.sendRedirect(oAuthAuthzResponseEx.getLocationUri().toString());
	}
	
	@RequestMapping(value="/oauth2/token1")
	public void token(HttpServletRequest request, HttpServletResponse response) 
			throws OAuthSystemException, OAuthProblemException {
		OAuthTokenRequestEx oAuthTokenRequestEx = new OAuthTokenRequestEx(request);
		
		validateRedirectionURI(oAuthTokenRequestEx);
		
	}
	
	private void validateRedirectionURI(OAuthTokenRequestEx oAuthzRequest) {
		System.out.println(oAuthzRequest.getClientId()
				+"\n"	+ oAuthzRequest.getClientSecret()
				+"\n"	+ oAuthzRequest.getRedirectURI()
				+"\n"	+ oAuthzRequest.getGrantType()
				+"\n"	+ oAuthzRequest.getCode());
	}
	
	
}
