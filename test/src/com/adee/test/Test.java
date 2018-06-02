package com.adee.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Test {
	Object o;
	public static void main(String[] args) throws Exception{
		
		//o = null;
		System.out.println(new Test().o);
		
		File f = new File("d:/timg.jpg");
		InputStream is = new FileInputStream(f);
		int aval = is.available();
		HashMap<Object, String> map = new HashMap<Object, String>();
		ArrayList<String> list;
		CopyOnWriteArrayList<String> cow;
		ConcurrentHashMap<String, Object> chm;
	}
}
