package com.adee.test;

public class Test001 {
	public void m(final Test001 t) {
		System.out.println(t);
	}
	
	public static void main(String[] args) {
		Test001 t = new Test001();
		Test001 t1 = new Test001();
		t1.m(t);
	}
}
