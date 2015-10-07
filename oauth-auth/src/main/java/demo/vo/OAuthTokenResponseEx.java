package demo.vo;

import java.util.HashMap;
import java.util.Map;

import org.apache.oltu.oauth2.common.utils.JSONUtils;

public class OAuthTokenResponseEx {

	private OAuthResponse oAuthResponse = new OAuthResponse();
	
	public OAuthTokenResponseEx(String accessToken, String refreshToken, String expiresIn) {
		oAuthResponse.setAccess_token(accessToken);
		oAuthResponse.setRefresh_token(refreshToken);
		oAuthResponse.setExpires_in(expiresIn);
	}
	
	public String getJsonString() {
		Map<String,Object> params = new HashMap<>();
		params.put("access_token", oAuthResponse.getAccess_token());
		params.put("refresh_token", oAuthResponse.getRefresh_token());
		params.put("expires_in", oAuthResponse.getExpires_in());
		return JSONUtils.buildJSON(params);
	}
	
	public OAuthResponse getOAuthResponse() {
		return oAuthResponse;
	}
}
