package com.edu.spring.spring;

import org.springframework.context.annotation.Bean;

public class App2 {
	@Bean
	public Runnable createRunnable() {
		return () -> {System.out.println("spring boot is running");};
	}
}
