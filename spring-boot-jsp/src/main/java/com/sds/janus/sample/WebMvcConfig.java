package com.sds.janus.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	@Qualifier("sessionCheckInterceptor")
	private HandlerInterceptor sessionInterceptor;  

	@Autowired
	@Qualifier("authorizationCheckInterceptor")
	private HandlerInterceptor authenticationInterceptor;  

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionInterceptor)
			.excludePathPatterns("/loginpage","/login","/error","/resources/**");
		registry.addInterceptor(authenticationInterceptor)
		.excludePathPatterns("/loginpage","/login","/error","/oauth2/**","/permission/**","/redirect");
	}
}
