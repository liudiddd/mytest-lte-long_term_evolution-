package com.adee.test;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestExecutor {
	private static final int NTHREADS = 100;
	private static final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);
	
	public static void main(String[] args)  throws Exception{
		ServerSocket ss = new ServerSocket(80);
		while(true) {
			final Socket s = ss.accept();
			Callable<Object> task = new Callable<Object>() {
				@Override
				public Object call() throws Exception {
					System.out.println("execute callable task...");
					return new Object();
				}
			};
			
			Future<Object> future = exec.submit(task);
			try {
				future.get();
			} catch (InterruptedException e) {
				System.out.println("task execution thread has been interrupted!");
				Thread.currentThread().interrupt(); //重新设置线程的中断状态
				future.cancel(true); //由于不需要结果，因此取消任务
			} catch(ExecutionException e1) {
				throw new Exception(e1.getCause());
			}
		}
	}
}
