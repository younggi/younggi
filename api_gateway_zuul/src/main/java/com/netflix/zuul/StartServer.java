package com.netflix.zuul;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.netflix.zuul.monitoring.MonitoringHelper;

public class StartServer implements ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(StartServer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("starting server");
		
		MonitoringHelper.initMocks();
		
		initGroovyFilterManager();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("stopping server");
	}
	
	private void initGroovyFilterManager() {
		FilterLoader.getInstance().setCompiler(new GroovyCompiler());
		
		String scriptRoot = System.getProperty("zuul.filter.root", "");
		if (scriptRoot.length() > 0) 
			scriptRoot = scriptRoot + File.separator;
		
		try {
			FilterFileManager.setFilenameFilter(new GroovyFileFilter());
			FilterFileManager.init(5,
					scriptRoot + "pre",
					scriptRoot + "route",
					scriptRoot + "post");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
