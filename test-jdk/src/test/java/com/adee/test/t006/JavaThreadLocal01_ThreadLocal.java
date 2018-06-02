package com.adee.test.t006;

/**
 * ThreadLocal类
 * 一、ThreadLocal原理
 * 		1.Thread类中有map成员变量：ThreadLocal.ThreadLocalMap threadLocals = null;其k-v为：
 * 			(ThreadLocal<?> firstKey, Object firstValue)，key就是ThreadLocal对象，value就是ThreadLocal泛型类型对应的对象，默认是null，
 * 			可使用set(T t)方法设置。
 * 		2.set方法
 * 			set方法就是把value值设置为当前线程CurrentThread.threadLocalMap中key为threadLocal对象的value。
 * 		3.get方法
 * 			get方法就是取出当前线程CurrentThread.threadLocalMap中key为threadLocal对象的value值。
 * 
 * @author Administrator
 *
 */
public class JavaThreadLocal01_ThreadLocal {
	public static void main(String[] args) {
		JNDISupport.setJndiName("main");
		System.out.println(JNDISupport.getJndiName());
		new Thread(new Runnable() {
			
			public void run() {
				JNDISupport.setJndiName("thread1");
				System.out.println(JNDISupport.getJndiName());
			}
		}).start();
	}
}

class JNDISupport{
	private static ThreadLocal<String> jndiNamePerThread = new ThreadLocal<String>();
	
	public static String getJndiName() {
		return jndiNamePerThread.get();
	}
	
	public static void setJndiName(String jndiName) {
		jndiNamePerThread.set(jndiName);
	}
}
