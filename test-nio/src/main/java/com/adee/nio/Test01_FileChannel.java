package com.adee.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Test01_FileChannel {
	public static void main(String[] args) throws Exception{
		//test01();
		//test02();
		test03();
	}
	
	/**
	 * 使用文件通道FIleChannel读文件，及ByteBuffer的使用
	 * 注：我发现一个乱码的问提，当使用while循环的时候，不能保证最后的几个byte刚好处理一个完整的字符，
	 * 尤其是使用变长编码（Unicode）的时候，出现乱码的概率会更大。当使用ASCII编码是没问题的，因为它是1Byte，
	 * 就算ByteBuffer分配一个byte也能正常读取。
	 * 感觉NIO的使用场景还是在tcp通讯实现非阻塞IO的用处比较多，在读写文件方面好像并不是特别好。
	 * 尤其是使用byteBuffer循环读取非ASCII编码字符，实在是无法保证读取的字节是完整的。
	 * NIO为的是实现非阻塞读，减少高并发时很多线程阻塞在socket上的问题，而在读磁盘文件的时候，并不会感到阻塞，
	 * 因为文件的数据就在磁盘上，通过系统调用按块读磁盘就行了，所以读磁盘文件不需要NIO
	 * @throws Exception
	 */
	public static void test01() throws Exception{
		RandomAccessFile aFile = new RandomAccessFile("d:/nio-data.txt", "rw");
		FileChannel inChannel = aFile.getChannel(); //channel为打开文件fd对应的内核缓冲区
		ByteBuffer buf = ByteBuffer.allocate(48); //Buffer为读写channel的用户空间缓冲区
		int bytesRead = inChannel.read(buf); //通过系统调用，将fd对应文件的数据读入channel，
																				//然后再将channel的数据从内核区copy到用户空间的Buffer中
																				//并将数据指针和尾指针都调到读取数据的尾部
		while (bytesRead != -1) { //读到了数据，并且Buffer的数据指针指向的最后一个byte的位置
			System.out.println("Read " + bytesRead);
			buf.flip(); //将Buffer中数据指针调整到起始位置
			while(buf.hasRemaining()){ //数据指针（游标）没有指向最后一个位置的话，则返回true
				System.out.print((char) buf.get()); //buf.get()，将数据指针（游标）向后跳一个位置，并返回刚刚指向位置的元素
			}
			System.out.println();
			buf.clear(); //将数据指针调到起始位置，尾指针调到Buffer的末端，为了再次读取channel的数据到Buffer中
			bytesRead = inChannel.read(buf);
		}
		aFile.close(); //关闭当前线程打开的文件，即告诉操作系统，
								//将内核维护的系统打开文件表中该文件fd的引用计数减一（若减一后为0则清除表项）
	}
	
	/**
	 * scatter/gather 分散/聚集
	 * 分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。
	 * 因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。
	 * 聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，
	 * 因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。
	 * scatter / gather经常用于需要将传输的数据分开处理的场合，例如传输一个由消息头和消息体组成的消息，
	 * 你可能会将消息体和消息头分散到不同的buffer中，这样你可以方便的处理消息头和消息体。
	 * @throws Exception
	 */
	public static void test02() throws Exception{
		RandomAccessFile aFile = new RandomAccessFile("d:/nio-data.txt", "rw");
		FileChannel channel = aFile.getChannel(); //channel为打开文件fd对应的内核缓冲区
		ByteBuffer header = ByteBuffer.allocate(128);
		ByteBuffer body   = ByteBuffer.allocate(256);
		ByteBuffer[] bufferArray = { header, body };
		channel.read(bufferArray); //read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，
															//当一个buffer被写满后，channel紧接着向另一个buffer中写。Scattering Reads在
															//移动下一个buffer前，必须填满当前的buffer，这也意味着它不适用于动态消息(译者注：
															//消息大小不固定)。换句话说，如果存在消息头和消息体，消息头必须完成填充（例如 128byte），
															//Scattering Reads才能正常工作。
		//scattering read
		header.flip(); //header切换到读模式
		body.flip(); //body切换到读模式
		System.out.println("header:");
		while(header.hasRemaining()) {
			System.out.print((char)header.get());
		}
		System.out.println();
		System.out.println("body:");
		while(body.hasRemaining()) {
			System.out.print((char)body.get());
		}
		
		//gathering write
		//将Buffer中的内容写入另一个channel
		//执行完查看文件d:/nio-data1.txt是否与控制台打印内容一样
		RandomAccessFile aFile1 = new RandomAccessFile("d:/nio-data1.txt", "rw");
		FileChannel channel1 = aFile1.getChannel(); //channel1为打开文件fd对应的内核缓冲区
		header.flip(); //header切换到读模式
		body.flip(); //body切换到读模式
		channel1.write(bufferArray); //将Buffer数组中的数据写入channel中
																//buffers数组是write()方法的入参，write()方法会按照buffer在数组中的顺序，
																//将数据写入到channel，注意只有position和limit之间的数据才会被写入。
																//因此，如果一个buffer的容量为128byte，但是仅仅包含58byte的数据，
																//那么这58byte的数据将被写入到channel中。因此与Scattering Reads相反，
																//Gathering Writes能较好的处理动态消息。
		
		aFile.close();
	}
	
	/**
	 * FileChannel.transferFrom  向FileChannel拷贝数据 
	 * @throws Exception
	 */
	public static void test03() throws Exception{
		RandomAccessFile fromFile = new RandomAccessFile("d:/fromFile.txt", "rw");
		FileChannel      fromChannel = fromFile.getChannel();
		RandomAccessFile toFile = new RandomAccessFile("d:/toFile.txt", "rw");
		FileChannel      toChannel = toFile.getChannel();
		long position = 0;
		long count = fromChannel.size();
		toChannel.transferFrom( fromChannel, position, count);
		fromFile.close();
		toFile.close();
	}
	
	/**
	 * selector
	 * @throws Exception
	 */
	public static void test04() throws Exception{
		/*ServerSocketChannel channel = new 
		Selector selector = Selector.open();
		channel.configureBlocking(false);
		SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
		while(true) {
		  int readyChannels = selector.select();
		  if(readyChannels == 0) continue;
		  Set selectedKeys = selector.selectedKeys();
		  Iterator keyIterator = selectedKeys.iterator();
		  while(keyIterator.hasNext()) {
		    SelectionKey key = keyIterator.next();
		    if(key.isAcceptable()) {
		        // a connection was accepted by a ServerSocketChannel.
		    } else if (key.isConnectable()) {
		        // a connection was established with a remote server.
		    } else if (key.isReadable()) {
		        // a channel is ready for reading
		    } else if (key.isWritable()) {
		        // a channel is ready for writing
		    }
		    keyIterator.remove();
		  }
		}*/

	}
}
