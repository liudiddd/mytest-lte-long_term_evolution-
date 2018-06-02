package com.edu.spring.spring;

public class LogUser extends User{
	
	
	
	@Override
	public void show() {
		System.out.println("before show ...");
		super.show();
		System.out.println("after show ...");
	}
}
