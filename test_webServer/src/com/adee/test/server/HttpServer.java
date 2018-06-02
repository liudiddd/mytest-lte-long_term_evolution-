package com.adee.test.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	public static void main(String[] args) throws IOException {
		//�󶨶˿�
		int port = 8889;
		//ͨ��ϵͳ���ã���ָ���˿��Ͽ�������
		ServerSocket serverSocket = new ServerSocket(port); 
		
		//���������������������󣬽���tcp���ӣ�����socket����
		Socket socket = serverSocket.accept();
		//��socket�����ȡ������
		InputStream is = socket.getInputStream();
		byte[] buffer = new byte[1024];
		int len;
		StringBuilder sb = new StringBuilder();
		while((len = is.read(buffer)) != -1) {
			//��socket�л�ȡ�ֽ����飬����buffer�У����ֽ��������Ϊutf8��ʽ���ı�
			sb.append(new String(buffer, 0, len, "UTF-8"));
		}
		System.out.println(sb.toString());
		
		OutputStream os = socket.getOutputStream();
		os.write("hello, i am server".getBytes("UTF-8"));
		socket.shutdownInput();
		//�ر���
		is.close();
		os.close();
		socket.close();
		serverSocket.close();
	}
}
