package com.adee.zookeeper.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import com.adee.zookeeper.common.Constant;
import com.adee.zookeeper.service.impl.HelloServiceImpl;

public class ServerProvider {
	private CountDownLatch latch = new CountDownLatch(1);
	public String publishService(Remote obj, String host, int port) {
		String url = null;
		try {
			url = String.format("rmi://%s:%d/%s", host, port, obj.getClass().getName());
			LocateRegistry.createRegistry(port);
			Naming.rebind(url, new HelloServiceImpl());
		} catch (RemoteException e) {
			url = null;
			e.printStackTrace();
		} catch (MalformedURLException e) {
			url = null;
			e.printStackTrace();
		}
		return url;
	}
	
	public void publish(Remote obj, String host, int port) {
		String url = publishService(obj, host, port);
		if(url != null) {
			ZooKeeper zk = connectZookeeperServer();
			if(zk != null) {
				createNode(zk, url);
			}
		}
	}
	
	private void createNode(ZooKeeper zk, String url) {
		try {
			byte[] data = url.getBytes();
			String path = zk.create(Constant.ZK_PROVIDER_PATH, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			System.out.println("create zookeeper node[" + path + "=>" + url);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	private ZooKeeper connectZookeeperServer() {
		ZooKeeper zk = null;
		try {
			//Watcher的作用是监控Constant.ZK_CONNECT_STRING节点，只要节点有变化就会回调Watcher.process方法
			zk = new ZooKeeper(Constant.ZK_CONNECT_STRING, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
				public void process(WatchedEvent e) {
					if(e.getState() == Event.KeeperState.SyncConnected) { //客户端连上服务器了，并且数据已经同步了
						latch.countDown(); //latch减1
					}
				}
			});
			latch.await(); //latch不为0时，线程阻塞在此处，当latch降为0时，线程被唤醒，从此处继续执行
		}catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return zk;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(new ServerProvider().toString());
	}
}
