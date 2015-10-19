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
	@Qualifier("loginCheckInterceptor")
	private HandlerInterceptor loginCheckInterceptor;  

	@Autowired
	@Qualifier("oAuthTokenCheckInterceptor")
	private HandlerInterceptor oAuthTokenCheckInterceptor;  

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInterceptor)
			.excludePathPatterns("/loginpage","/login","/error","/oauth2/token","/resources/**");
		registry.addInterceptor(oAuthTokenCheckInterceptor)
		.excludePathPatterns("/loginpage","/login","/error","/oauth2/**","/permissionpage","/permission","/redirect");
	}
}
