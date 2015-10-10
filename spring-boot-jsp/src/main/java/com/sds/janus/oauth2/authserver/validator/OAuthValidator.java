package com.sds.janus.oauth2.authserver.validator;

import java.lang.reflect.Field;
import java.util.List;

public class OAuthValidator {

	public enum CheckField { response_type, client_id, grant_type, code, redirect_uri };
	
	private List<CheckField> checkFields = null;  
	
	private Class clazz = null;
	private Object object = null;
	
	public OAuthValidator(Object object, Class clazz, List<CheckField> checkFields) {
		if (object == null || clazz == null ) {
			throw new RuntimeException("object or clazz is null");
		}
		this.object = object;
		this.clazz = clazz;
		this.checkFields = checkFields;
	}
	
	public void validate() {		
		for (CheckField checkField: checkFields) {
			try {
				Field field = clazz.getDeclaredField(checkField.toString());
				field.setAccessible(true);
				if (field.get(object) == null) {
					throw new RuntimeException();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
