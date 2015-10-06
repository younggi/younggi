package demo.vo;

import java.net.URI;
import java.net.URISyntaxException;

public class OAuthAuthzResponseEx {
	
	enum ResponseType { code, token };
	
	private OAuthAuthzRequestEx oAuthAuthzRequestEx = null;
	
	private String locationUri = null;
	
	public OAuthAuthzResponseEx(OAuthAuthzRequestEx oAuthAuthzRequestEx, String codeOrToken) {
		this.oAuthAuthzRequestEx = oAuthAuthzRequestEx;
		locationUri = makeLocationUrl(codeOrToken);
	}	
	
	private String makeLocationUrl(String codeOrToken) {
		String type = "";
		switch(ResponseType.valueOf(oAuthAuthzRequestEx.getResponseType())) {
		case code:
			type="?code=";
			break;
		case token:
			type="#token=";
			break;
		default:
			throw new RuntimeException("Invalid Code");
		}
		return oAuthAuthzRequestEx.getRedirectURI()+type+codeOrToken;
	}
	
	public URI getLocationUri() {
		try {
			return new URI(locationUri);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
