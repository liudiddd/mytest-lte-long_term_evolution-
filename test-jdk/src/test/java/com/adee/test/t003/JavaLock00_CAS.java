package com.adee.test.t003;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

/**
 * CAS（Compare And Swap）比较并交换
 * 一、简介
 * 		java.util.concurrent包中借助CAS实现了区别于synchronized同步锁的一种乐观锁。
 * 二、原理
 * 		CAS有3个数，内存值V、旧的预期值A、要修改的新值B，当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不做。
 *三、实现
 *			AomicLong举例
 * @author Administrator
 *
 */
public class JavaLock00_CAS {
	AtomicLong al = new AtomicLong(0L);
	@Test
	public void test01() {
		AtomicLong l = al;
		//将long类型的数原子加1，incrementAndGet()内部使用的是cas原子操作
		long a = al.incrementAndGet();
		//模拟incrementAndGet方法内部的原理，通过自旋的方式使用cas将long类型的数加1
		for(long update;;) {
			long expect = update = al.get();
			update++;
			if(al.compareAndSet(expect, update)) break;
		}
	}
}
