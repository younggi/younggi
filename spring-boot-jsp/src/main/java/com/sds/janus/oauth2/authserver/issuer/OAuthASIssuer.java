package com.sds.janus.oauth2.authserver.issuer;

public interface OAuthASIssuer {
	
  public String accessToken();

  public String authorizationCode();

  public String refreshToken();

}
