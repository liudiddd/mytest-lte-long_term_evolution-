package com.edu.spring.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Book implements ApplicationContextAware{
	ApplicationContext context;
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	public void show() {
		System.out.println("User: " + context.getClass());
	}
}
