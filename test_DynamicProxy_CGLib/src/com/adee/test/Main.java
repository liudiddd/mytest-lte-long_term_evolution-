package com.adee.test;

import net.sf.cglib.proxy.Enhancer;

public class Main {
	
	public static void main(String[] args) {
		Enhancer eh = new Enhancer();
		eh.setSuperclass(HelloMethodInterceptor.class); //�̳б�������
		eh.setCallback(new HelloMethodInterceptor()); //���ûص�
		HelloServiceImpl hsi = (HelloServiceImpl)eh.create();
	}
}
