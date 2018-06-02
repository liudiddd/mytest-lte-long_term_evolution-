package com.adee.test.t003;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 使用BlockingQueue实现生产者消费者模型
 * 一、LinkedBlockingQueue内部实现
 * 		链表+ReentrantLock+Condition
 * @author Administrator
 *
 */
public class JavaLock04_BlockingQueue {
	public static void main(String[] args) {
		//创建一个产品队列
		BlockingQueue<Product> bq = new LinkedBlockingQueue<Product>(100);
		//创建10个生产者线程
		for(int i = 0; i < 10; i++) {
			new Thread(new BlockingQueueProductor<Product>(bq)).start();
		}
		//创建10个消费者线程
		for(int i = 0; i < 10; i++) {
			new Thread(new BlockingQueueConsumer<Product>(bq)).start();
		}
	}
}

/**
 * 消费者
 * @author Administrator
 *
 * @param <T>
 */
class BlockingQueueConsumer<T extends Product> implements Runnable{
	private BlockingQueue<T> q;
	public BlockingQueueConsumer(BlockingQueue<T> q) {
		this.q = q;
	}
	
	public void consume(T p) {
		System.out.println("consume<<---- " + p);
	}
	
	//该消费者不停地从产品队列拿去产品消费
	public void run() {
		for(;;) {
			T p = null;
			try {
				p = q.take();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			consume(p);
			try { Thread.sleep(100); } catch (InterruptedException e) { }
		}
	}
}

/**
 * 生产者
 * @author Administrator
 *
 * @param <T>
 */
class BlockingQueueProductor<T extends Product> implements Runnable{
	private BlockingQueue<T> q;
	public BlockingQueueProductor(BlockingQueue<T> q) {
		this.q = q;
	}
	public T product(String name) {
		Product p = new Product(name);
		System.out.println("product---->> " + p);
		return (T)p;
	}
	
	//生产者不停地生产产品放入产品队列
	public void run() {
		for(;;) {
			try {
				q.put(product(System.currentTimeMillis() + ""));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try { Thread.sleep(100); } catch (InterruptedException e) { }
		}
	}
}
