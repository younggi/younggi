package demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.request.OAuthUnauthenticatedTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OAuthController {

	public static final String RESPONSE_TYPE_CODE = "code";
	public static final String RESPONSE_TYPE_TOKEN = "token";
	
	// Authorization Service
	@RequestMapping(value="/oauth2/authorize", method=RequestMethod.POST)
	public void authorize(HttpServletRequest request, HttpServletResponse response) throws IOException, OAuthSystemException {
		
		try {
			// Step 1.
			OAuthIssuerImpl oAuthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
			
			OAuthAuthzRequest oAuthzRequest = new OAuthAuthzRequest(request);
			
			// Step 2. 
			// if Client Secret is exist, check validation 
			// else Open the page
			// Redirection URL Validation 
			validateRedirectionURI(oAuthzRequest);
			// Authorization Permission check
			
			// Store Auth Code
			// CASE : response_typeAuthorization Code, TOKEN
			OAuthResponse resp = null;
			switch(oAuthzRequest.getResponseType()) {
				case RESPONSE_TYPE_CODE:
					// Step 3.
					resp = OAuthASResponse
						.authorizationResponse(request,HttpServletResponse.SC_FOUND)
						.setCode(oAuthIssuerImpl.authorizationCode())
						.location(oAuthzRequest.getRedirectURI())
						.buildQueryMessage();
					break;
				case RESPONSE_TYPE_TOKEN:
					// Step 3.
					 resp = OAuthASResponse
						.authorizationResponse(request,HttpServletResponse.SC_FOUND)
						.setAccessToken(oAuthIssuerImpl.authorizationCode())
						.location(oAuthzRequest.getRedirectURI())
						.buildQueryMessage();
					break;
				default:
					throw OAuthProblemException.error("invalid response type");
			}
			
			response.sendRedirect(resp.getLocationUri());
			
		} catch (OAuthProblemException e) {
			e.printStackTrace();
			
			final OAuthResponse resp = OAuthASResponse
					.errorResponse(HttpServletResponse.SC_FOUND)
					.error(e)
					.location("http://localhost:8080/callback")
					.buildQueryMessage();
			
			response.sendRedirect(resp.getLocationUri());
		}
	}	
	
	// Token Service (For Authorization token)
	@RequestMapping(value="/oauth2/token", method=RequestMethod.POST)
	public void token(HttpServletRequest request, HttpServletResponse response) throws IOException, OAuthSystemException {
		OAuthTokenRequest oauthTokenRequest = null;
		OAuthResponse r = null;
		OAuthIssuer oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
		
		try {
			oauthTokenRequest = new OAuthTokenRequest(request);
			
			// Client validation 
			validateClient(oauthTokenRequest);
			
			String authzCode = oauthTokenRequest.getCode();
			
			// Check authzCode
			
			String accessToken = oAuthIssuer.accessToken();
			String refreshToken = oAuthIssuer.refreshToken();
			
			// Store tokens
			
			// Response tokens
			r = OAuthASResponse
					.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(accessToken)
					.setExpiresIn("3600")
					.setRefreshToken(refreshToken)
					.buildJSONMessage();
			
			
		} catch (OAuthProblemException e) {			
			e.printStackTrace();
			r = OAuthResponse
					.errorResponse(401)
					.error(e)
					.buildJSONMessage();
		} finally {
			response.setStatus(r.getResponseStatus());
			PrintWriter pw = response.getWriter();
			pw.print(r.getBody());
			pw.flush();
			pw.close();
		}
	}
	
	// Token Service (For Resource owner Password Credentials)
	@RequestMapping(value="/oauth2/token2", method=RequestMethod.POST)
	public void token2(HttpServletRequest request, HttpServletResponse response) throws IOException, OAuthSystemException {
		OAuthUnauthenticatedTokenRequest oAuthRequest = null;
		OAuthResponse r = null;
		OAuthIssuer oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());

		try {
			oAuthRequest = new OAuthUnauthenticatedTokenRequest(request);
			
			// validateCredential
			
			String accessToken = oAuthIssuer.accessToken();
			String refreshToken = oAuthIssuer.refreshToken();
			
			r = OAuthASResponse
					.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(accessToken)
					.setExpiresIn("3600")
					.setRefreshToken(refreshToken)
					.buildJSONMessage();
			
			
		} catch (OAuthProblemException e) {
			e.printStackTrace();
			r = OAuthResponse
					.errorResponse(401)
					.error(e)
					.buildJSONMessage();
		} finally {
			response.setStatus(r.getResponseStatus());
			PrintWriter pw = response.getWriter();
			pw.print(r.getBody());
			pw.flush();
			pw.close();
		}
	}
	
	// Callback (Client Application Server)
	@RequestMapping(value="/callback",method=RequestMethod.GET)
	public void access(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String[]> paramMap = request.getParameterMap();
		for (String key : paramMap.keySet()) {
			System.out.print(key + " = ");
			Stream.of(paramMap.get(key))
				  .forEach(s -> System.out.println(s));
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	private void validateRedirectionURI(OAuthAuthzRequest oAuthzRequest) {
		System.out.println(oAuthzRequest.getClientId()
				+"\n"	+ oAuthzRequest.getClientSecret()
				+"\n"	+ oAuthzRequest.getRedirectURI()
				+"\n"	+ oAuthzRequest.getResponseType()
				+"\n"	+ oAuthzRequest.getState()
				+"\n"	+ oAuthzRequest.getScopes());
	}
	
	private void validateClient(OAuthTokenRequest oAuthTokenRequest) {
		System.out.println(oAuthTokenRequest.getClientId()
				+"\n" + oAuthTokenRequest.getClientSecret()
				+"\n" + oAuthTokenRequest.getRedirectURI()
				+"\n" + oAuthTokenRequest.getGrantType()
				+"\n" + oAuthTokenRequest.getCode());
	}
}
