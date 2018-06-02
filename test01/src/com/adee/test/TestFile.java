package com.adee.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class TestFile {
	public static void main(String[] args) {
		File path = new File(".");
		String[] list = path.list();
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
		for(String s : list) System.out.println(s);
		
		InputStream is;
		try {
			RandomAccessFile raf = new RandomAccessFile("d:/text_nio.txt", "rw");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteBuffer bb = ByteBuffer.allocate(1024);
		
	}
}
