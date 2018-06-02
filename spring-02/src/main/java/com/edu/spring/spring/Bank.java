package com.edu.spring.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Bank {
	
	ApplicationContext context;
	
	//Spring4的一个新特性：构造方法的参数默认在bean配置中找，并注入
	//但是，如果有多个构造函数，就会找空构造函数，而如果没有去空构造函数，就会报错
	public Bank() {
		// TODO Auto-generated constructor stub
	}
	
	public Bank(ApplicationContext context) {
		this.context = context;
	}
	
	public Bank(ApplicationContext context, User user) {
		this.context = context;
	}
	
	public void show() {
		System.out.println("Bank: " + context.getClass());
	}
}
