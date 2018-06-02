package com.adee.zookeeper.client;

import com.adee.zookeeper.service.HelloService;

public class Client {
	public static void main(String[] args) throws Exception{
		ServiceConsumer sc = new ServiceConsumer();
		//zookeeper测试
		while(true) {
			HelloService s = sc.lookup();
			String ret = s.sayHello("Jackson");
			System.out.println(ret);
			Thread.sleep(3000);
		}
	}
}
