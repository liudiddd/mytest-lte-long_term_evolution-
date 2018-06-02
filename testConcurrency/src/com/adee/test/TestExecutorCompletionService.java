package com.adee.test;

public class TestExecutorCompletionService {
	public static void main(String[] args) {
		Thread t = new Thread();
		Object obj = new Object();
		try {
			obj.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			t.join(); //
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
