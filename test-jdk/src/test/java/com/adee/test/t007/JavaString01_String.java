package com.adee.test.t007;

/**
 * String类
 * 参考java中String类详解.txt
 * 一、实现
 * 		1.String成员变量
 * 			private final char value[]; //字符数组
 * 			private int hash; // Default to 0
 * 		2.构造方法
 * 			public String() {
			        this.value = "".value;
			    }
			    
			    public String(String original) {
			        this.value = original.value;
			        this.hash = original.hash;
			    }
			 3.equals方法
			 	就是遍历字符数组，比较每个字符是否相等。
 * @author Administrator
 *
 */
public class JavaString01_String {
	public static void main(String[] args) {
		String s1 = "hello";
		String s2 = "hello";
		System.out.println(s1 == s2); //true
		
		String s3 = new String("hello");
		System.out.println(s3 == s1); //false
		
		String s4 = new String("hello");
		System.out.println(s3 == s4); //false
		
		String s5 = new String("hello").intern();
		System.out.println(s5 == s1); //true
		
		/**
		 * 以下会在字符串常量池中生成：
		 * 	"hello"
		 * 	"world"
		 * 	" i am "
		 * 	"Jack."
		 * 	"helloworld"
		 * 	"helloworld i am "
		 * 	"helloworld i am Jack."
		 * 一系列字符串常量，然后再将"helloworld i am Jack."的引用赋给s6。
		 * 可以看出，使用+号会在字符串常量池中生成很多无用的中间字符串。
		 * 如何避免常量池中使用+号产生的中间字符串，而只有原始字符串和最终字符串呢：
		 * 	"hello"
		 * 	"world"
		 * 	" i am "
		 * 	"Jack."
		 * 	"helloworld i am Jack."
		 * 方法是：使用StringBuilder或StringBuffer类，待述。
		 */
		String s6 = "hello" + "world" + new String(" i am ") + new String("Jack.");
		
		
		
		

	}
}
