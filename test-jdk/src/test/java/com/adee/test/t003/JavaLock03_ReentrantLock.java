package com.adee.test.t003;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock实现生产者/消费者模型
 * 
 * @author Administrator
 *
 */
public class JavaLock03_ReentrantLock {
	public static void main(String[] args) {
		//创建一个产品队列
		ProductQueue<Product> q = new ReentrantLockProductQueue<Product>();
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
 * 产品队列
 * 有界队列，默认长度为10
 * @author Administrator
 *
 * @param <E>
 */
class ReentrantLockProductQueue<E> extends ProductQueue<E>{
	private int len = 20;
	private List<E> q = new LinkedList<E>();
	ReentrantLock lock = new ReentrantLock();
	Condition full = lock.newCondition();
	Condition empty = lock.newCondition();
	
	//产品入队
	@Override
	public void  enqueue(E e) {
		try {
			lock.lock(); //获取队列锁
			while(q.size() >= len) {
				full.await(); //如果队列满了则线程等待在full条件上，当前线程进入lock.full的等待队列，并释放lock锁
			}
			q.add(e);
			empty.signal(); //唤醒等待在lock.empty上的消费者线程
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	//产品出队
	@Override
	public E dequeue() {
		try {
			lock.lock(); //获取队列锁
			while(q.size() == 0) {
				empty.await(); //如果队列为空则线程等待在lock.empty条件上，当前线程进入lock.empty的等待队列，并释放lock锁
			}
			full.signal(); //唤醒等待在lock.full上的生产者线程
			return q.remove(0);
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally {
			lock.unlock();
		}
	}
}
