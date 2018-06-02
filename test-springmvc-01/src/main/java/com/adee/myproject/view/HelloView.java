package com.adee.myproject.view;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

@Component
public class HelloView implements View{
	public String getContentType() {
		return "text/html";
	}
	
	public void render(Map<String, ?> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.getWriter().write("helloView, time:" + new Date());
	}
}
