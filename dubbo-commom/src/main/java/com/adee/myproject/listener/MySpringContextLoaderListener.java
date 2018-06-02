package com.adee.myproject.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class MySpringContextLoaderListener extends ContextLoaderListener{
	private static Logger logger = LoggerFactory.getLogger(MySpringContextLoaderListener.class);
	private WebApplicationContext context;
	
	public MySpringContextLoaderListener() {
		super();
	}
	
	public MySpringContextLoaderListener(WebApplicationContext context) {
		super(context);
		this.context = context;
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		this.context = initWebApplicationContext(event.getServletContext());
		logger.debug("MySpringContextLoaderListener.context: " + this.context);
		System.out.println("--------------------employeeService:" + context.getBean("employeeService"));
	}
	
	
}
