package com.adee.zookeeper.common;

public interface Constant {
	String ZK_CONNECT_STRING = "192.168.1.110:2181,192.168.1.110:2182,192.168.1.110:2183";
	int ZK_SESSION_TIMEOUT = 5000;
	String ZK_REGISTRY_PATH = "/registry";
	String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider";
}
