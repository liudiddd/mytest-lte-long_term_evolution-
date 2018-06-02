package com.adee.test.t003;

import java.util.LinkedList;
import java.util.List;

/**
 * synchrohized实现生产着消费者模式
 * 一、设计
 * 		生产者Productor implements Runnable
 * 		消费者Consumer implements Runnable
 * 		公共访问区（产品对列）ProductQueue
 * 		产品Product
 * 		创建3个生产线程，并发生产产品放入产品队列
 * 		创建3个消费线程，并发从产品队列取出产品消费
 * 二、优缺点
 * 		
 * @author Administrator
 *
 */
public class JavaLock02_synchronized {
	public static void main(String[] args) {
		//创建一个产品队列
		ProductQueue<Product> q = new ProductQueue<Product>();
		//创建10个生产者线程
		for(int i = 0; i < 10; i++) {
			new Thread(new Productor<Product>(q)).start();
		}
		//创建10个消费者线程
		for(int i = 0; i < 10; i++) {
			new Thread(new Consumer<Product>(q)).start();
		}
	}
}


/**
 * 消费者
 * @author Administrator
 *
 * @param <T>
 */
class Consumer<T extends Product> implements Runnable{
	private ProductQueue<T> q;
	public Consumer(ProductQueue<T> q) {
		this.q = q;
	}
	
	public void consume(T p) {
		System.out.println("consume<<---- " + p);
	}
	
	//该消费者不停地从产品队列拿去产品消费
	public void run() {
		for(;;) {
			T p = q.dequeue();
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
class Productor<T extends Product> implements Runnable{
	private ProductQueue<T> q;
	public Productor(ProductQueue<T> q) {
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
			q.enqueue(product(System.currentTimeMillis() + ""));
			try { Thread.sleep(100); } catch (InterruptedException e) { }
		}
	}
}

/**
 * 产品队列
 * 有界队列，默认长度为10
 * @author Administrator
 *
 * @param <E>
 */
class ProductQueue<E>{
	private int len = 20;
	private List<E> q = new LinkedList<E>();
	
	//产品入队，如果队列满了则线程等待在队列上，等待队列有空余时被唤醒
	public synchronized void  enqueue(E e) {
		while(q.size() >= len) {
			try {
				wait(); //当前线程进入当前对象的wait队列，并释放当前对象的锁
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		q.add(e);
		//唤醒等在在队列上的线程（应该是消费者线程，但是synchronized区分不出来消费者线程还是生产者线程，所有wait的线程都唤醒）
		notifyAll();
	}
	
	//产品出队，如果队列为空则线程等待在队列上，等待队列有产品时被唤醒
	public synchronized E dequeue() {
		while(q.size() == 0) {
			try {
				wait(); //如果队列为空，则当前线程进入当前对象的wait队列，等待当前队列不为空时被唤醒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		notifyAll(); //唤醒等待在当前对象上的线程（准确说是唤醒生产者线程，但无法区分，可能造成线程饥饿），被唤醒线程进入就绪队列
							//注意，该方法return之后才会释放当前对象的锁，被唤醒的线程才可能进入执行状态
		return q.remove(0);
	}
}

/**
 * 产品
 * @author Administrator
 *
 */
class Product{
	private String name;
	
	public Product(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}