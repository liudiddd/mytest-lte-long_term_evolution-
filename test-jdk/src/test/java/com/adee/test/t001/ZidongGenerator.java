package com.adee.test.t001;

public class ZidongGenerator<E> implements Generator<E> {
	public E generate(E[] elements) {
		return elements[random(elements.length)];
	}
	
	private int random(int len) {
		return 0;
	}
}
