package demo.vo;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import demo.validator.OAuthValidator;

public class AbstractOAuthRequestEx {
	
	protected Object requestObject = null; 
	protected Class requestClazz = null;
	
	public AbstractOAuthRequestEx(Object requestObject, Class requestClazz) {
		this.requestObject = requestObject;
		this.requestClazz = requestClazz;
	}

	protected void validate(Object object, Class clazz, List<OAuthValidator.CheckField> checkFields) {
		OAuthValidator validator = new OAuthValidator(object, clazz, checkFields);
		validator.validate();
	}
	
	public Optional<Object> getValue(String methodName) {
		if (requestObject == null) {
			return null;
		}
		
		try {
			Method method = requestClazz.getMethod(methodName, null);			
			return Optional.ofNullable(method.invoke(requestObject, null));
		} catch (Exception e) {
			return Optional.empty();			
		}		
	}
	
	public String getClientId() {
		return (String)getValue("getClientId").orElse(null);
	}
	
	public String getClientSecret() {
		return (String)getValue("getClientSecret").orElse(null);
	}

	public String getRedirectURI() {
		return (String)getValue("getRedirectURI").orElse(null);
	}
	
}
