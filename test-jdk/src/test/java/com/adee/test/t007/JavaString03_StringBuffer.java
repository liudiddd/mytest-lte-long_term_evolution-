package com.adee.test.t007;

/**
 * StringBuffer类
 * 参考java中String类详解.txt
 * 一、实现
 * 		1.与StringBuilder一样，只是在方法上加了synchronized了，线程同步访问方法。
 * @author Administrator
 *
 */
public class JavaString03_StringBuffer {
	public static void main(String[] args) {
		
		/**
		 * StringBuffer
		 */
		StringBuffer sb = new StringBuffer();
		sb.append("hello").append("world");

	}
}
