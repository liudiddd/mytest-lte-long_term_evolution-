package com.adee.test.t007;

/**
 * StringBuilder类
 * 参考java中String类详解.txt
 * 一、实现
 * 		1.StringBuilder成员变量
 * 			char[] value; //内部维护的字符数组
 * 		2.append(String str)方法
 * 			现将StringBuilder.value字符数组扩容，再将str.value字符数组放入StringBuilder.value字符数组中的后半部分。只有字符数组的操作，
 * 			并不会像+那样生成中间字符常量。当有很多小字符串拼接成一个大字符串的时候，可以节省很大的中间字符串的内存空间。
 * 		3.append方法的并发访问问题
 * 			当多个线程调用了StringBuilder对象的append方法时，如果两个线程同时对一个value字符数组扩容后再将新数组的引用赋给value的话，
 * 			后扩容的字符数组会将先扩容的字符数组覆盖。
 * 			涉及到并发时，应使用StringBuffer类。待述。
 * @author Administrator
 *
 */
public class JavaString02_StringBuilder {
	public static void main(String[] args) {
		
		/**
		 * StringBuilder
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("hello").append("world");

	}
}
