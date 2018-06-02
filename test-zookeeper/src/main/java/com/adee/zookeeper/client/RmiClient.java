package com.adee.zookeeper.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.adee.zookeeper.service.HelloService;

public class RmiClient {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, InterruptedException {
		String url = "rmi://localhost:1098/com.adee.zookeeper.service.impl.HelloServiceImpl";
		HelloService s = (HelloService)Naming.lookup(url);
		Thread.sleep(10000);
		String ret = s.sayHello("Jack");
		System.out.println(ret);
	}
}
