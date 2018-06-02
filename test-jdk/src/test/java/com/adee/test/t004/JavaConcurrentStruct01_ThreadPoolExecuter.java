package com.adee.test.t004;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * Java并发框架ThreadPoolExecutor
 * 一、实现
 * 		1.构造方法
 * 			public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue) 
                              
                public ThreadPoolExecutor(int corePoolSize,		--核心线程数
                              int maximumPoolSize,									--最大线程数
                              long keepAliveTime,											--超过核心线程数的线程最大空闲时间
                              TimeUnit unit,													--时间单位
                              BlockingQueue<Runnable> workQueue,	--存放任务的阻塞队列
                              ThreadFactory threadFactory,						--产生线程的工厂类
                              RejectedExecutionHandler handler) 			--任务拒绝策略
             2.public void execute(Runnable command)
                	1、首先可以通过线程池提供的submit()方法或者execute()方法，要求线程池执行某个任务。线程池收到这个要求执行的任务后，
                			会有几种处理情况：
                	1.1、如果当前线程池中运行的线程数量还没有达到corePoolSize大小时，线程池会创建一个新的线程运行你的任务，无论之前已经
                				创建的线程是否处于空闲状态。 
                	1.2、如果当前线程池中运行的线程数量已经达到设置的corePoolSize大小，线程池会把你的这个任务加入到等待队列中。直到某一个
                				的线程空闲了，线程池会根据设置的等待队列规则，从队列中取出一个新的任务执行。 
					1.3、如果根据队列规则，这个任务无法加入等待队列。这时线程池就会创建一个“非核心线程”直接运行这个任务。注意，如果这种情
								况下任务执行成功，那么当前线程池中的线程数量一定大于corePoolSize。 
					1.4、如果这个任务，无法被“核心线程”直接执行，又无法加入等待队列，又无法创建“非核心线程”直接执行，且你没有为线程池设置
								RejectedExecutionHandler。这时线程池会抛出RejectedExecutionException异常，即线程池拒绝接受这个任务。（实际上抛出RejectedExecutionException异常的操作，是ThreadPoolExecutor线程池中一个默认的RejectedExecutionHandler实现：AbortPolicy，这在后文会提到） 
					2、一旦线程池中某个线程完成了任务的执行，它就会试图到任务等待队列中拿去下一个等待任务（所有的等待任务都实现了
								BlockingQueue接口，按照接口字面上的理解，这是一个可阻塞的队列接口），它会调用等待队列的poll()方法，并停留在哪里。 
					3、当线程池中的线程超过你设置的corePoolSize参数，说明当前线程池中有所谓的“非核心线程”。那么当某个线程处理完任务后，
							如果等待keepAliveTime时间后仍然没有新的任务分配给它，那么这个线程将会被回收。线程池回收线程时，对所谓的“核心线程”
							和“非核心线程”是一视同仁的，直到线程池中线程的数量等于你设置的corePoolSize参数时，回收过程才会停止。
             3.ThreadFactory的使用
                	线程池最主要的一项工作，就是在满足某些条件的情况下创建线程。而在ThreadPoolExecutor线程池中，创建线程的工作交给
                	ThreadFactory来完成。要使用线程池，就必须要指定ThreadFactory。 
					类似于上文中，如果我们使用的构造函数时并没有指定使用的ThreadFactory，这个时候ThreadPoolExecutor会使用一个默认的
					ThreadFactory：DefaultThreadFactory。（这个类在Executors工具类中）。
			 4.线程池的等待队列
			 		在使用ThreadPoolExecutor线程池的时候，需要指定一个实现了BlockingQueue接口的任务等待队列。在ThreadPoolExecutor
			 		线程池的API文档中，一共推荐了三种等待队列，它们是：SynchronousQueue、LinkedBlockingQueue和ArrayBlockingQueue；
			 5.拒绝任务（handler）
			 		在ThreadPoolExecutor线程池中还有一个重要的接口：RejectedExecutionHandler。当提交给线程池的某一个新任务无法直接被
			 		线程池中“核心线程”直接处理，又无法加入等待队列，也无法创建新的线程执行；又或者线程池已经调用shutdown()方法停止了工作；
			 		又或者线程池不是处于正常的工作状态；这时候ThreadPoolExecutor线程池会拒绝处理这个任务，触发创建ThreadPoolExecutor
			 		线程池时定义的RejectedExecutionHandler接口的实现。
			 		在创建ThreadPoolExecutor线程池时，一定会指定RejectedExecutionHandler接口的实现。
			 		实际上，在ThreadPoolExecutor中已经提供了四种可以直接使用的RejectedExecutionHandler接口的实现：
			 		● CallerRunsPolicy： 
						这个拒绝处理器，将直接运行这个任务的run方法。但是，请注意并不是在ThreadPoolExecutor线程池中的线程中运行，而是直接
						调用这个任务实现的run方法。
					● AbortPolicy：
						这个处理器，在任务被拒绝后会创建一个RejectedExecutionException异常并抛出。这个处理过程也是ThreadPoolExecutor
						线程池默认的RejectedExecutionHandler实现。
					● DiscardPolicy： 
						DiscardPolicy处理器，将会默默丢弃这个被拒绝的任务，不会抛出异常，也不会通过其他方式执行这个任务的任何一个方法，更不会
						出现任何的日志提示。
					● DiscardOldestPolicy： 
						这个处理器很有意思。它会检查当前ThreadPoolExecutor线程池的等待队列。并调用队列的poll()方法，将当前处于等待队列列头
						的等待任务强行取出，然后再试图将当前被拒绝的任务提交到线程池执行。
 * 				实际上查阅这四种ThreadPoolExecutor线程池自带的拒绝处理器实现，您可以发现CallerRunsPolicy、DiscardPolicy、
 * 				DiscardOldestPolicy处理器针对被拒绝的任务并不是一个很好的处理方式。 
					CallerRunsPolicy在非线程池以外直接调用任务的run方法，可能会造成线程安全上的问题；DiscardPolicy默默的忽略掉被拒绝任务，
					也没有输出日志或者提示，开发人员不会知道线程池的处理过程出现了错误；DiscardOldestPolicy中e.getQueue().poll()的方式好像
					是科学的，但是如果等待队列出现了容量问题，大多数情况下就是这个线程池的代码出现了BUG。最科学的的还是AbortPolicy提供的
					处理方式：抛出异常，由开发人员进行处理。
 * @author Administrator
 *
 */
public class JavaConcurrentStruct01_ThreadPoolExecuter {
	public static void main(String[] args) {
		int corePoolSize = 5;
		int maximumPoolSize = 10;
		int keepAliveTime = 10;
		TimeUnit unit = TimeUnit.SECONDS;
		LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(100);
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		RejectedExecutionHandler handler = new AbortPolicy();
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize,
				maximumPoolSize, 
				keepAliveTime, 
				unit, 
				workQueue, 
				threadFactory, 
				handler);
		
		//execute方法
		executor.execute(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				
			}
		});
		
		//submit方法
		Future<Object> f = executor.submit(new Callable<Object>() {
			public Object call() throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		try {
			Object ret = f.get(); //阻塞指到任务执行完
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
