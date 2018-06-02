package com.adee.test.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	public static void main(String[] args) throws IOException {
		//绑定端口
		int port = 8889;
		//通过系统调用，在指定端口上开启监听
		ServerSocket serverSocket = new ServerSocket(port); 
		
		//阻塞，监听请求，请求到来后，建立tcp连接，返回socket对象
		Socket socket = serverSocket.accept();
		//从socket对象获取输入流
		InputStream is = socket.getInputStream();
		byte[] buffer = new byte[1024];
		int len;
		StringBuilder sb = new StringBuilder();
		while((len = is.read(buffer)) != -1) {
			//从socket中获取字节数组，放入buffer中，将字节数组编码为utf8格式的文本
			sb.append(new String(buffer, 0, len, "UTF-8"));
		}
		System.out.println(sb.toString());
		
		OutputStream os = socket.getOutputStream();
		os.write("hello, i am server".getBytes("UTF-8"));
		socket.shutdownInput();
		//关闭流
		is.close();
		os.close();
		socket.close();
		serverSocket.close();
	}
}
