package com.adee.test.t001;

/**
 * 一个普通的泛型类：
 * 1.在类后面加<泛型标识符>就表明这是一个泛型类
 * 2.泛型标识符可以随便写，一般为T、E、K、V
 * 3.在泛型类中，泛型标识符可用在：成员变量类型、成员方法返回类型，成员方法形参类型处，注意，不能用在类成员变量和类方法上
 * 4.使用泛型类时，一定要传入泛型参数吗？可以不传泛型参数。如果传入泛型参数，类中的泛型标识符会被限制为该泛型参数类型（编译时会
 * 	检查这种限制，不满足限制的话会编译不通过）。如果不传入泛型参数类型的话，则类中的使用泛型的方法或成员变量可以为任何类型。
 * @author Administrator
 *
 * @param <T>
 */
public class Generic <T>{
	private T key;
	
	public Generic(T key) {
		this.key = key;
	}
	
	public T getKey() {
		return key;
	}
}
