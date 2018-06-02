package com.adee.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		Thread t = new Thread();
		t.start();
		Vector<Object> vct;
		Iterator< Object> it;
		CopyOnWriteArrayList<Object> cow;
		HashMap<Object, Object> map;
		ConcurrentHashMap<Object, Object> chm = new ConcurrentHashMap<>();
		chm.entrySet();
	}
}
