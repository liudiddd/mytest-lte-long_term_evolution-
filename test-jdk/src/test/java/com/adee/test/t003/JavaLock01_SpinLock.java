package com.adee.test.t003;

import java.util.concurrent.atomic.AtomicReference;

/**
 * java自旋锁
 * 一、原理
 * 		自旋锁是采用让当前线程不停地在循环内部检查一个条件，当发现条件改变时跳出循环，进入下面的临界区
 * 二、使用场景
 * 		自旋锁属于忙等，由于线程不阻塞，使得cpu占有率很高，当并发量极高，锁的占有时间很短时，推荐使用自旋锁，因为自旋锁会减少线程
 * 		切换的开销。而当并发量不是特别高，锁的占有时间很长，就不推荐使用自旋锁，而是用synchronized或lock，因为此时线程切换不是很
 * 		频繁，线程切换开销相对较小，而由于线程占用锁的时间较长，其他线程一直忙轮询检查锁，会造成cpu占有率很高。
 * 三、不可重入锁
 * 		不可重入锁，当线程获取锁后进入临界区，在临界区代码没有执行完后继续获取该锁，就会造成线程死锁。
 * @author Administrator
 *
 */
public class JavaLock01_SpinLock {
	public static long a = 0;
	private static SpinLock lock = new SpinLock();
	public static void main(String[] args) throws Exception {
		for(int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				
				public void run() {
					try {
						lock.lock();
						for(int i = 0; i < 100; i++) a++;
						System.out.println(a);
					} catch (Exception e) {
						lock.unlock();
					}
				}
			}).start();
		}
		
		Thread.sleep(15000);
		System.out.println(a);
	}
}

class SpinLock{
	private AtomicReference<Thread> sign =new AtomicReference<Thread>();
	
	
	public void lock() {
		Thread current = Thread.currentThread();
		for(;;) {
			//循环检查共享内存sign的值，为0则设置为1并跳出循环进入临界区，意为获得锁成功
			//线程不可重入
			if(sign.compareAndSet(null, current)) break;
		}
	}
	
	public void unlock() {
		Thread current = Thread.currentThread();
		for(;;) {
			//解锁。只有当前线程加的锁才能解锁成功
			if(sign.compareAndSet(current, null)) break;
		}
	}
}