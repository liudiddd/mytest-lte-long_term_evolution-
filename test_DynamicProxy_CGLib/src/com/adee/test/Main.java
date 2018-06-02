package com.adee.test;

import net.sf.cglib.proxy.Enhancer;

public class Main {
	
	public static void main(String[] args) {
		Enhancer eh = new Enhancer();
		eh.setSuperclass(HelloMethodInterceptor.class); //继承被代理类
		eh.setCallback(new HelloMethodInterceptor()); //设置回调
		HelloServiceImpl hsi = (HelloServiceImpl)eh.create();
	}
}
