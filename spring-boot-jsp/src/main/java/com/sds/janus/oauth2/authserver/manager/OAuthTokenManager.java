package com.sds.janus.oauth2.authserver.manager;

import java.util.Map;

public interface OAuthTokenManager {
	
	public void setOAuthtokenInfo(String accessToken, Map<String,String> tokenInfo);
	public Map<String,String> getOAuthtokenInfo(String accessToken);
	public boolean checkToken(String accessToken);
	
}
