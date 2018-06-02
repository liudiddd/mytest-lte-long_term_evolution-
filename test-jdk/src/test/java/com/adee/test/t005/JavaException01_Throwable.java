package com.adee.test.t005;

import org.apache.log4j.Logger;

/**
 * java异常体系
 * 1.java异常体系结构
 * 	Throwable
 * 		Error
 * 			IOError
 * 			VirtualMachineError
 * 				OutOfMemoryError
 * 				StackOverFlowError
 * 				UnknownError
 * 		Exception //继承Throwable类，只定义了与Throwable一致的构造方法，其他都继承Throwable
 * 			自定义的需要显式处理的异常
 * 				...many
 * 			RuntimeException //运行时异常，不需要显示处理
 * 				NullPointerException
 * 				IndexOutOfBoundsException
 * 				...many
 * 
 * 2.Throwable类
 * 	1.主要成员变量
 * 		private String detailMessage; //异常的说明信息
 * 		private StackTraceElement[] stackTrace = UNASSIGNED_STACK; //方法调用栈
 * 		private static final StackTraceElement[] UNASSIGNED_STACK = new StackTraceElement[0];
 * 		StackTraceElement：
 * 			private String declaringClass;
			    private String methodName;
			    private String fileName;
			    private int    lineNumber;
			private Throwable cause = this; //当前异常对象是由哪个下层异常抛出来的
		2.构造方法
			public Throwable() {
		        fillInStackTrace();
		    }
		    
		    public Throwable(String message) {
		        fillInStackTrace();
		        detailMessage = message;
		    }
		    
		    public Throwable(String message, Throwable cause) {
		        fillInStackTrace();
		        detailMessage = message;
		        this.cause = cause;
		    }
		    
		    public Throwable(Throwable cause) {
		        fillInStackTrace();
		        detailMessage = (cause==null ? null : cause.toString());
		        this.cause = cause;
		    }
		    
		    protected Throwable(String message, Throwable cause,
		                        boolean enableSuppression,
		                        boolean writableStackTrace) {
		        if (writableStackTrace) {
		            fillInStackTrace();
		        } else {
		            stackTrace = null;
		        }
		        detailMessage = message;
		        this.cause = cause;
		        if (!enableSuppression)
		            suppressedExceptions = null;
		    }
	3.主要方法
		//获取异常说明信息
		public String getMessage() {
	        return detailMessage;
	    }
	    
	    //获取引起该异常的下层异常对象
	    public synchronized Throwable getCause() {
	        return (cause==this ? null : cause);
	    }
	    
	    //toString方法只是获取类名和异常说明信息
	    public String toString() {
	        String s = getClass().getName();
	        String message = getLocalizedMessage();
	        return (message != null) ? (s + ": " + message) : s;
	    }
	    
	4.public void printStackTrace(PrintStream s) 方法
		1.Throwable中的private StackTraceElement[] stackTrace成员变量默认是一个空数组，当且仅当方法printStackTrace被调用的时候才会
		填充这个数组。
		2.stackTrace数组中存放的是线程中方法调用栈的每个路径节点信息，节点信息包括类名、方法名、方法行号信息，如：
			run()
			m1()
			m2(args)
			m3(args)
			...
			mn(arts)
			这就是线程中的方法调用栈，从最初的run方法开始，一直到抛出异常的方法。
		3.printStackTrace方法首先输出Throwable.detaiMessage信息。然后调用native方法从线程方法调用栈中取出每个方法信息，然后封装成
			一个一个的StackTraceElement对象，放入stackTrace数组中，然后遍历该数组，将每个节点的信息按从mn到run的顺序输出到
			PrintStream s流中，默认该流为System.Err。
	5.log4j中方法
		
 * @author Administrator
 *
 */
public class JavaException01_Throwable {
	public static void main(String[] args) {
		Throwable t;
		RuntimeException re;
		Logger logger = Logger.getLogger(JavaException01_Throwable.class);
		try {
			int i = 1/0;
		} catch (Exception e) {
			if(logger.isDebugEnabled()) logger.debug("...");
			logger.error("error...", e);
		}
		
	}
}
