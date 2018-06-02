package com.edu.spring.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
@Component
public class User {
	
	@Autowired
	private ApplicationContext context;
	
	
	public void show() {
		System.out.println("User: " + context);
	}
}
