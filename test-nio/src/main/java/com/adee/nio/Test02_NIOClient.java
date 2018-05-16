package com.adee.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * NIO实现的聊天客户端
 * @author Administrator
 * main线程负责创建socketChannel（初始化tcp连接），并向socketChannel写数据
 * ClientThread线程负责从socketChannel读数据
 */
public class Test02_NIOClient {
	private Selector selector=null;  //定义channel选择器
    private Charset charset=Charset.forName("UTF-8");  //定义字符集编/解码器
    private SocketChannel sc=null;  //客户端SocketChannel  
    public void init() throws IOException{  
        selector=Selector.open();  //创建一个selector
        InetSocketAddress isa=new InetSocketAddress("127.0.0.1",30000);  
        sc=SocketChannel.open(isa);   //调用open静态方法创建连接到指定ip:port的SocketChannel
        														//发起tcp请求，3次握手
        sc.configureBlocking(false);  //将socketChannel设置为非阻塞读写。注意，用selector管理socketChannel是，必须设为非阻塞
        sc.register(selector, SelectionKey.OP_READ);  //将socketChannel注册到selector.read事件的观察者集合中
        																						//当该socketChannel收到数据包时就会触发read事件，将该socketChannel封装
        																						//为SelectionKey放入selector.selectedKey集合中
        
        new ClientThread().start();  //启动读取服务器端数据的线程  
        Scanner scan=new Scanner(System.in);  //创建键盘输入流  
        while(scan.hasNextLine()){  
            String line=scan.nextLine();  //读取键盘输入  
            sc.write(charset.encode(line));  //将键盘输入的内容输出到SocketChannel中  
        }  
    }  
    
    //定义读取服务器数据的线程  
    private class ClientThread extends Thread{  
        public void run(){  
            try{  
                while(selector.select()>0){  //selector阻塞等待所有注册的socketChannel事件
                    for(SelectionKey sk:selector.selectedKeys()){  //遍历每个已发生相应事件的SelectionKey（channel）
                        selector.selectedKeys().remove(sk);  //将当前SelectionKey从selector.selectedKeys集合中移除，以防下次遍历再取到
                        if(sk.isReadable()){  //如果当前SelectionKey（channel）的read事件已发生，即该channel有数据可读了 
                            SocketChannel sc=(SocketChannel) sk.channel();  //使用NIO读取当前channel中的数据  
                            ByteBuffer buff=ByteBuffer.allocate(1024);  
                            String content="";  
                            while(sc.read(buff)>0){  
                                //sc.read(buff);  
                                buff.flip();  
                                content+=charset.decode(buff);  
                                buff.clear();
                            }  
                            System.out.println("聊天信息"+content);   //打印输出读取的内容  
                            sk.interestOps(SelectionKey.OP_READ);  //将当前SelectionKey（channel）放入selector.read事件的观察者集合中
                        }  
                    }  
                }  
            }catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }  
    }  
    public static void main(String[]args) throws IOException{  
        new Test02_NIOClient().init();  
    }  
}
