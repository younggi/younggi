package com.sds.janus.oauth2.authserver.request;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OAuthRequest {

	private String client_id;
	private String redirect_uri;
	private String response_type;
	private String grant_type;
	private String client_secret;
	private String code;
	private Set<String> scope;
	
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getRedirect_uri() {
		return redirect_uri;
	}
	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}
	public String getResponse_type() {
		return response_type;
	}
	public void setResponse_type(String response_type) {
		this.response_type = response_type;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Set<String> getScope() {
		return scope;
	}
	public void setScope(Set<String> scope) {
		this.scope = scope;
	}
	
	// For AbstractOAuthRequestEx
	public String getClientId() {
		return getClient_id();
	}
	public String getRedirectURI() {
		return getRedirect_uri();
	}
	public String getResponseType() {
		return getResponse_type();
	}
	public Set<String> getScopes() {
		return getScope();
	}
	public String getGrantType() {
		return getGrant_type();
	}
	public String getClientSecret() {
		return getClient_secret();
	}	
	
	public String getParam() {
		Class clazz = OAuthRequest.class;
		Field[] fields = clazz.getDeclaredFields();		
		Stream<Field> fieldStream = Stream.of(fields); 
		return fieldStream.filter(t -> t.get(this) != null)
								.map(t -> t.getName() + "=" + t.getType().cast(t.get(this)))
								.collect(Collectors.joining("&"));

//		for (Field field : fields) {
//			field.setAccessible(true);
//			try {
//				Object object = field.get(this);
//				
//				if (object != null) {
//					buffer.append(field.getName())
//								.append("=")
//								.append(field.getType().cast(object));
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	public static wrapCheckException
}
