package com.adee.zookeeper.server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

import com.adee.zookeeper.service.HelloService;
import com.adee.zookeeper.service.impl.HelloServiceImpl;

public class Server {
	public static void main(String[] args) throws RemoteException, InterruptedException, MalformedURLException {
		String host = "192.168.1.100";
		int port = Integer.parseInt("11237");
		ServerProvider p = new ServerProvider();
		HelloService service = new HelloServiceImpl();
		p.publish(service, host, port);
		
		Thread.sleep(Long.MAX_VALUE);
	}
}
