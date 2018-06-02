package com.adee.test.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		String host = "192.168.1.104";
		int port = 8889;
		Socket socket = new Socket(host, port);
		OutputStream os = socket.getOutputStream();
		String msg = "hello, i am client";
		os.write(msg.getBytes("UTF-8"));
		
		//通过shutdownOutput告诉server已经发送完数据
		socket.shutdownOutput();
		
		byte[] buffer = new byte[1024];
		InputStream is = socket.getInputStream();
		is.read(buffer);
		System.out.println(new String(buffer, "UTF-8"));
		os.close();
		is.close();
		socket.close();
	}
}
