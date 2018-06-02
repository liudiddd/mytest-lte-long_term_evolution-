package com.adee.myproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {
	
	@RequestMapping("/helloworld")
	public String hello() {
		
		System.out.println("helloworld");
		return "success";
	}
}
