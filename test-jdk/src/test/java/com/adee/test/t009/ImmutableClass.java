package com.adee.test.t009;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 不可变类
 * 要创建不可变类，要实现下面几个步骤：

	1.将类声明为final，所以它不能被继承
	2.将所有的成员声明为私有的，这样就不允许直接访问这些成员
	3.对变量不要提供setter方法
	4.将所有可变的成员声明为final，这样只能对它们赋值一次
	5.通过构造器初始化所有成员，进行深拷贝(deep copy)
	6.在getter方法中，不要直接返回对象本身，而是克隆对象，并返回对象的拷贝
 * @author Administrator
 *
 */
public final class ImmutableClass {
	private final int id;
	private final String name;
	private final HashMap<Object, Object> hashMap;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public HashMap<Object, Object> getHashMap(){
		return (HashMap<Object, Object>)hashMap.clone(); //这不是彻底的深拷贝，而是浅拷贝
	}
	
	public ImmutableClass(int id, String name, HashMap<Object, Object> hashMap) {
		this.id = id;
		this.name = name;
		HashMap<Object, Object> tmp = new HashMap<Object, Object>();
		tmp.putAll(hashMap);
		this.hashMap = tmp;
	}
}
