package com.adee.test.t001;

public class ShougongGenerator<E> implements Generator<E>{
	public E generate(E[] elements) {
		return elements[0];
	}
}
