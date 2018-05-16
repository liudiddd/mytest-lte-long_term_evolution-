package com.adee.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * NIO实现的聊天服务器
 * @author Administrator
 * NIO适用于连接多、带宽低的场景，如聊天服务器
 * IO适用于连接少、带宽高的场景，如大文件传输
 */
public class Test03_NIOServer {
    private Selector selector=null;  //定义channel选择器
    private Charset charset=Charset.forName("UTF-8");  //定义字符集编/解码器
    public void init() throws IOException{  
        selector=Selector.open();  //创建一个selector
        ServerSocketChannel server=ServerSocketChannel.open();  //通过open方法来打开一个未绑定的ServerSocketChannel实例  
        InetSocketAddress isa=new InetSocketAddress("127.0.0.1",30000);  
        server.socket().bind(isa);  //将该ServerSocketChannel绑定到指定的ip:port 
        server.configureBlocking(false);   //设置ServerSocketChannel以非阻塞方式工作，也就是读写该channel的内核缓冲区立即返回
        																//serverSocket的四要素为：*:* 127.0.0.1:30000，说明此socket处于监听状态，未与任何
        																//客户端有连接
        server.register(selector, SelectionKey.OP_ACCEPT);  //将serverSocketChannel注册到selector.accept事件的观察者集合中
        while(selector.select()>0){  //阻塞等待selector任何事件观察者集合中的任何一个channel有对应事件发生，有则返回，无则阻塞
            for(SelectionKey sk:selector.selectedKeys()){  //依次处理selector上的每个已选择的SelectionKey  
                //从selector上的已选择Key集中删除正在处理的SelectionKey  
                selector.selectedKeys().remove(sk);  //将socketChannel从selector.selectedKeys集合中移除，以防下次遍历该集合时读到
               
                if(sk.isAcceptable()){   	//sk对应的channel在selector.accept事件集合中，
                										  	//并且sk的accept事件已触发（即客户端tcp请求已发给服务器端，此处的服务器端指serverSocketChannel）
                											//只有serverSocketChannel才会产生accept事件
                    
                    SocketChannel sc=server.accept();   //调用accept方法进行tcp的3次握手，产生服务器端对应的SocketChannel
                    																	//tcp socket是端到端的连接，其四要素是：两个ip和两个端口
                    																	//由于serverSocketChannel已设置为非阻塞，调用accept方法会立即返回，如果没有新到
                    																	//的连接则返回null，而现在isAcceptalbe()返回true了，说明有新到的连接了，返回新到
                    																	//连接的socketChannel
                    sc.configureBlocking(false);  //将socketChannel设置为非阻塞模式
                    														//socket也是文件类型的一种，程序手法网络包的动作抽象为读写socket
                    														//socketChannel中维护的是socket的fd、四要素、内核缓冲区，每当收到对端的tcp网络包时，
                    														//引发一次系统中断，将tcp包的body数据放入该socket.fd对应的内核缓冲区中，此时会触发该
                    														//socketChannel的read事件，说明该socket有数据到来了，可以读socket.fd内核缓冲区了。
                    														//默认阻塞情况下，当程序通过socket.fd读相应的
                    														//内核缓冲区时，如果缓冲区为空，则当前线程阻塞在此，直到有数据到来时才读取数据继续执行，
                    														//而当程序通过socket.fd向内核缓冲区写数据时，如果缓冲区满了，当前线程就阻塞在此，直到
                    														//内核缓冲区数据被取走后，线程才会继续执行写数据操作。
                    														//将socketChannel设置为非阻塞的意思就是，当程序读写socket.fd对应的内核缓冲区的时候
                    														//不会阻塞，不管缓冲区为空或满了，立即返回。
                    														//所以，socketChannel有"是否阻塞"属性。
                    sc.register(selector, SelectionKey.OP_READ);  //将socketChannel注册到selector的read观察者集合中，当此socketChannel
                    																						//的read事件发生时，就将此socketChannel包装成一个SelectionKey对象，
                    																						//放入selector.selectedKeys集合中，此集合的功能就是存放已触发某个事件（
                    																						//connect、accept、read、write）的channel的包装对象SelectionKey
                    																						//注：
                    																						//某个socketChannel的read事件发生的意思就是：该socketChannel收到了
                    																						//数据包，并将数据包放入了自己的socket.fd对应的内核缓冲区了
                    
                    sk.interestOps(SelectionKey.OP_ACCEPT); 	//  将sk对应的Channel设置成准备接受其他请求？？？？？？？？不明白！！
                    																					//个人理解，就是将当前的SelectionKey sk对应的channel放入selector.accept
                    																					//观察者集合中。现在，当前的sk已经在accept观察者集合中了，不知道为什么
                    																					//还要添加一次，而且只有serverSocketChannel类型的channel才会有accept
                    																					//事件
                }  
                  
                if(sk.isReadable()){  //当前sk对应通道的读事件已发生
                    SocketChannel sc=(SocketChannel) sk.channel();  //获取该SelectionKey对应的Channel，该Channel中有可读的数据  
                    ByteBuffer buff=ByteBuffer.allocate(1024);  //创建用户空间Buffer，用于从channel中读数据
                    String content="";  
                    try{  
                        while(sc.read(buff)>0){  
                            buff.flip();  
                            content+=charset.decode(buff);  
                            buff.clear();
                        }  
                        System.out.println("=========="+content);  //打印从该sk对应的Channel里读到的数据  
                        sk.interestOps(SelectionKey.OP_READ);  //将sk对应的channel放入selector.accept事件的观察者集合中
                    }catch(IOException e){  	//如果捕捉到该sk对应的channel出现异常，即表明该channel对应的client出现了  
                        											//异常，所以从selector中取消sk的注册  
                        sk.cancel();  //将sk对应的channel从selector的所有事件的观察者集合中移除
                        if(sk.channel()!=null){  
                            sk.channel().close();   //关闭channel，即释放channel对应的内核缓冲区、清除对应的socket.fd
                        }  
                    }  
                    
                    if(content.length()>0){  //如果content的长度大于0，即聊天信息不为空  
                        for(SelectionKey key:selector.keys()){  //遍历该selector里注册的所有SelectKey（即channel），并将聊天消息广播
                            Channel targetChannel=key.channel();   //选取该key对应的Channel  
                            if(targetChannel instanceof SocketChannel){  //如果该channel是SocketChannel对象  
                                SocketChannel dest=(SocketChannel) targetChannel;  //将读到的内容写入该Channel中  
                                dest.write(charset.encode(content));  
                            }  
                        }  
                    }  
                }  
            }  
        }  
    }  
    public static void main(String[]args) throws IOException{  
        new Test03_NIOServer().init();  
    }  
}
