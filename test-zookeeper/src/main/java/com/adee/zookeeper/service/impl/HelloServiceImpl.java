package com.adee.zookeeper.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.adee.zookeeper.service.HelloService;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService{
	
	public HelloServiceImpl() throws RemoteException{
		// TODO Auto-generated constructor stub
	}
	
	public String sayHello(String name) {
		String s = "hello, " + name + "!";
		System.out.println(s);
		return s;
	}
}
