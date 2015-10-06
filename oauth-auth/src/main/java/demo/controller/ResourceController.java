package demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ResourceController {
	
	@RequestMapping(value="/resources", method=RequestMethod.GET)
	public void resources(HttpServletRequest request, HttpServletResponse response) throws OAuthSystemException {
		
		try {
			// Validate OAuth Request
			OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.HEADER);
			
			// Get Access Token
			String accessToken = oauthRequest.getAccessToken();
			
			// validate token
			
			
			
		}  catch (OAuthProblemException e) {
			e.printStackTrace();
			
			OAuthResponse oauthResponse = OAuthRSResponse
					.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
					.setRealm("Album Example")
					.buildHeaderMessage();
			
			response.addHeader(OAuth.HeaderType.WWW_AUTHENTICATE,
					oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
			response.setStatus(oauthResponse.getResponseStatus());
		}
	}
}
