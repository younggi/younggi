package com.netflix.zuul;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartServer implements ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(StartServer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("starting server");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("stopping server");
	}

}
