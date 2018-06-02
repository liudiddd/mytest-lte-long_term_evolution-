package com.adee.test;

public class Test {
	static void t() {
		
	}
	void say(String name) {
		System.out.println("hello " + name);
	}
	
	public static void main(String[] args) {
		/*Test t = new SubTest();
		t.toString(); //invokevirtual 调用子类成员方法
		t.say("Jack"); //invokevirtual 调用子类成员方法
		t.t(); //invoke
*/	
		String s1 = "hello";
		String s2 = new String("hello").intern();
		String s3 = "hello";
		System.out.println(s1 == s2);
		System.out.println(s1 == s3);	
	}
}

class SubTest extends Test{
	@Override
	void say(String name) {
		System.out.println("sub hello " + name);
	}
}