package com.sds.janus.oauth2.authserver.request;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class OAuthRequestTest {

	@Test
	public void getParamTest() {
		OAuthRequest oAuthRequest = new OAuthRequest();
		oAuthRequest.setClient_id("abc");
		oAuthRequest.setResponse_type("test");
		Set<String> scopes = new HashSet<String>();
		scopes.add("123");
		scopes.add("456");
		oAuthRequest.setScope(scopes);

		System.out.println(oAuthRequest.getParam());
	}
	
}
