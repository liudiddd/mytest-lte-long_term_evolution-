package com.adee.zookeeper.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event;

import com.adee.zookeeper.common.Constant;
import com.adee.zookeeper.service.HelloService;

public class ServiceConsumer {
	
	private static List<String> urlList = Arrays.asList(Constant.ZK_CONNECT_STRING.split(","));
	
	public ServiceConsumer() {
		ZooKeeper zk = connectZookeeperServer();
		if(zk != null) {
			watchNode(zk);
		}
	}
	public <T extends Remote> T lookup() {
		T service = null;
		int size = urlList.size();
		if(size > 0) {
			String url;
			if(size == 0) {
				url = urlList.get(0);
				System.out.println("using only url : " + url);
			}else {
				url = urlList.get(ThreadLocalRandom.current().nextInt(size));
				System.out.println("using random url : " + url);
			}
			service = lookupService(url);
		}
		return service;
	}
	
	private <T extends Remote> T lookupService(String url) {
		T s = null;
		try {
			s = (T)Naming.lookup(url);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	private void watchNode(ZooKeeper zk) {
		try {
			//注册Watcher，监控Constant.ZK_REGISTRY_PATH节点下的数据和子节点，若数据或子节点有变化就会回调Watcher.process方法
			List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() { 
				@Override
				public void process(WatchedEvent event) {
					if(event.getType() == Event.EventType.NodeChildrenChanged) {
						watchNode(zk); //Constant.ZK_REGISTRY_PATH子节点有变化时则重新获取子节点
					}
				}
			});
			List<String> dataList = new ArrayList<String>();
			for(String node : nodeList) {
				byte[] data = zk.getData(Constant.ZK_REGISTRY_PATH + "/" + node, false, null);
				dataList.add(new String(data));
			}
			System.out.println("node data:" + dataList);
			urlList = dataList;
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private ZooKeeper connectZookeeperServer() {
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(Constant.ZK_CONNECT_STRING, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
				public void process(WatchedEvent e) {
					if(e.getState() == Event.KeeperState.SyncConnected) {
						//唤醒当前正在执行的线程
						
					}
				}
			});
			Thread.sleep(5000);
		}catch(IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return zk;
	}
}
