package com.adee.test.t004;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工厂类Executors
 * 一、静态工厂方法
 * 		1.newFixedThreadPool(int nThreads) 
 * 			return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
 * 			创建一个固定线程数的线程池，任务队列无界
 *			2.newCachedThreadPool()
 *				return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                      60L, TimeUnit.SECONDS,
                                      new SynchronousQueue<Runnable>());
                创建一个核心线程数为0的线程池，最大线程数不限，这样不好，高并发会把系统压垮
            3.newScheduledThreadPool(int corePoolSize)
            	super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS, new DelayedWorkQueue());
            	创建一个定时执行任务的线程池
            4.newSingleThreadExecutor()
            	new ThreadPoolExecutor(1, 1,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>())
                创建一个单线程的线程池，以无界队列方式执行
    二、使用线程池实现生产者/消费者模型
    		
 * @author Administrator
 *
 */
public class JavaConcurrentStruct02_Executors {
	public static void main(String[] args) {
		/*Executors.newCachedThreadPool();
		Executors.newScheduledThreadPool(5);
		Executors.newSingleThreadExecutor();
		ExecutorService service = Executors.newFixedThreadPool(5);*/
		
		//testRunnable();
		
		testCallable();
		
	}
	
	public static void testRunnable() {
		ExecutorService service = Executors.newFixedThreadPool(5);
		for(int i = 0; i < 100; i++) {
			Runnable r = new RunnableTask(System.currentTimeMillis() + "");
			service.execute(r);
			try { Thread.sleep(50); } catch (InterruptedException e) { }
		}
		service.shutdown();
	}
	
	public static void testCallable() {
		ExecutorService service = Executors.newFixedThreadPool(5);
		List<Future<String>> list = new ArrayList<Future<String>>();
		for(int i = 0; i < 100; i++) {
			Callable<String> r = new CallableTask(System.currentTimeMillis() + "");
			Future< String> f = service.submit(r);
			list.add(f);
			try { Thread.sleep(50); } catch (InterruptedException e) { }
		}
		service.shutdown();
		for(Future<String> f : list) {
			try {
				System.out.println(f.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}

class RunnableTask implements Runnable{
	private String taskName;
	public RunnableTask(String taskName) {
		this.taskName = taskName;
	}
	public void run() {
		System.out.println("processing---->>" + taskName);
	}
}

class CallableTask implements Callable<String>{
	private String taskName;
	public CallableTask(String taskName) {
		this.taskName = taskName;
	}
	
	public String call() throws Exception {
		System.out.println("processing---->>" + taskName);
		return "processed[" + taskName + "]";
	}
}