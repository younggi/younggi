package com.sds.janus.oauth2.authserver.issuer;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.issuer.UUIDValueGenerator;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

public class OAuthASIssuerImpl implements OAuthASIssuer {
	
	OAuthIssuer oAuthIssuer = null;
	
	enum IssuerType { MD5, UUID };
	
	public OAuthASIssuerImpl() {
		this(IssuerType.MD5);
	}
	
	public OAuthASIssuerImpl(IssuerType issuerType) {
		switch(issuerType) {
		case MD5:
		default:
			oAuthIssuer = new OAuthIssuerImpl(new MD5Generator());
			break;
		case UUID:
			oAuthIssuer = new OAuthIssuerImpl(new UUIDValueGenerator());
			break;		
		}
	}
	
	@Override
	public String accessToken() {		
		String result = null;
		
		try {
			result = oAuthIssuer.accessToken();
		} catch (OAuthSystemException e) {
			throw new RuntimeException("Generate accessToken exception");
		}
		
		return result;
	}

	@Override
	public String authorizationCode() {
		String result = null;
		
		try {
			result = oAuthIssuer.authorizationCode();
		} catch (OAuthSystemException e) {
			throw new RuntimeException("Generate authorizationCode exception");
		}
		
		return result;
	}

	@Override
	public String refreshToken() {
String result = null;
		
		try {
			result = oAuthIssuer.refreshToken();
		} catch (OAuthSystemException e) {
			throw new RuntimeException("Generate refreshToken exception");
		}
		
		return result;
	}

}
