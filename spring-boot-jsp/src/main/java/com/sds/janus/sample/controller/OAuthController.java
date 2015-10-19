package com.sds.janus.sample.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sds.janus.oauth2.authserver.issuer.OAuthASIssuer;
import com.sds.janus.oauth2.authserver.issuer.OAuthASIssuerImpl;
import com.sds.janus.oauth2.authserver.manager.OAuthTokenManager;
import com.sds.janus.oauth2.authserver.request.OAuthAuthzRequestEx;
import com.sds.janus.oauth2.authserver.request.OAuthRequest;
import com.sds.janus.oauth2.authserver.request.OAuthTokenRequestEx;
import com.sds.janus.oauth2.authserver.response.OAuthAuthzResponseEx;
import com.sds.janus.oauth2.authserver.response.OAuthResponse;
import com.sds.janus.oauth2.authserver.response.OAuthTokenResponseEx;

@RestController
public class OAuthController {
	
	private final Logger logger = LoggerFactory.getLogger(OAuthController.class);
	
	private OAuthASIssuer oAuthASIssuer = new OAuthASIssuerImpl();
	
	@Autowired
	private OAuthTokenManager oAuthTokenManager; 

	@RequestMapping("/oauth2/authorize")
	public ResponseEntity<String> authorize(OAuthRequest oAuthRequest,
			HttpSession session) throws URISyntaxException, UnsupportedEncodingException {
		
		OAuthAuthzRequestEx oAuthAuthzRequestEx = new OAuthAuthzRequestEx(oAuthRequest);
		
		validateRedirectionURI(oAuthAuthzRequestEx);

		HttpHeaders httpHeaders = new HttpHeaders();
		if (session.getAttribute("permission") != null 
				&& session.getAttribute("permission").equals("true")) {
			session.setAttribute("permission", "false");
			
			OAuthAuthzResponseEx oAuthAuthzResponseEx = 
					new OAuthAuthzResponseEx(oAuthAuthzRequestEx, oAuthASIssuer.authorizationCode());

			// response_type=token
			if (oAuthAuthzRequestEx.getResponseType().equals("token")) {
				Map<String,String> tokenInfo = new HashMap<String,String>();
				tokenInfo.put("client_id", oAuthAuthzRequestEx.getClientId());
				oAuthTokenManager.setOAuthtokenInfo(oAuthAuthzResponseEx.getToken(), 
						tokenInfo, 3600);
			}			
			
			httpHeaders.setLocation(oAuthAuthzResponseEx.getLocationUri());
		} else {
			httpHeaders.setLocation(new URI("/permissionpage?redirect="
							+URLEncoder.encode("/oauth2/authorize?" + oAuthRequest.getParam(), "UTF-8")));
		}
		
		return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
	}
	
	private void validateRedirectionURI(OAuthAuthzRequestEx oAuthzRequest) {
		logger.info(oAuthzRequest.getClientId()
				+"\n"	+ oAuthzRequest.getClientSecret()
				+"\n"	+ oAuthzRequest.getRedirectURI()
				+"\n"	+ oAuthzRequest.getResponseType()
				+"\n"	+ oAuthzRequest.getState()
				+"\n"	+ oAuthzRequest.getScopes());
	}
	
	@RequestMapping("/oauth2/authorize2")
	public void authorize(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {

		OAuthAuthzRequestEx oAuthAuthzRequestEx = new OAuthAuthzRequestEx(request);
		
		validateRedirectionURI(oAuthAuthzRequestEx);
		
		OAuthAuthzResponseEx oAuthAuthzResponseEx = 
				new OAuthAuthzResponseEx(oAuthAuthzRequestEx, oAuthASIssuer.authorizationCode());

		response.sendRedirect(oAuthAuthzResponseEx.getLocationUri().toString());
	}

	@RequestMapping(value="/oauth2/token")
	public OAuthResponse token(OAuthRequest request) {
		OAuthTokenRequestEx oAuthTokenRequestEx = new OAuthTokenRequestEx(request);
		
		validateRedirectionURI(oAuthTokenRequestEx);
		
		OAuthTokenResponseEx oAuthTokenResponseEx = 
				new OAuthTokenResponseEx(oAuthASIssuer.accessToken(), oAuthASIssuer.refreshToken(), "3600");
		
		Map<String,String> tokenInfo = new HashMap<String,String>();
		tokenInfo.put("client_id", oAuthTokenRequestEx.getClientId());
		oAuthTokenManager.setOAuthtokenInfo(oAuthTokenResponseEx.getOAuthResponse().getAccess_token(), 
				tokenInfo, 3600);
		
		return oAuthTokenResponseEx.getOAuthResponse();
	}
	
	@RequestMapping(value="/oauth2/token2")
	public void token(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		OAuthTokenRequestEx oAuthTokenRequestEx = new OAuthTokenRequestEx(request);
		
		validateRedirectionURI(oAuthTokenRequestEx);
		
		OAuthTokenResponseEx oAuthTokenResponseEx = 
				new OAuthTokenResponseEx(oAuthASIssuer.accessToken(), oAuthASIssuer.refreshToken(), "3600");
		
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter pw = response.getWriter();
		pw.print(oAuthTokenResponseEx.getJsonString());
		pw.flush();
		pw.close();
	}
	
	private void validateRedirectionURI(OAuthTokenRequestEx oAuthzRequest) {
		logger.info("\n" + oAuthzRequest.getClientId()
				+"\n"	+ oAuthzRequest.getClientSecret()
				+"\n"	+ oAuthzRequest.getRedirectURI()
				+"\n"	+ oAuthzRequest.getGrantType()
				+"\n"	+ oAuthzRequest.getCode());
	}	
	
}
