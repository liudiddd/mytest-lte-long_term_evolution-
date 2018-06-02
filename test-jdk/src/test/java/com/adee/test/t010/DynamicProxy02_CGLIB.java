package com.adee.test.t010;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * cglib动态代理
 * cglib动态代理要求目标类必须实现接口
 * cglib动态代理原理：
 * 	与jdk类似，只是cglib不是实现接口，而是继承目标对象的类。因此不要求目标对象要实现接口。
 * 
 * @author Administrator
 *
 */
public class DynamicProxy02_CGLIB {
	public static void main(String[] args) {
		ServiceImpl1 s = (ServiceImpl1)getProxy(new ServiceImpl1());
		System.out.println(s.get(1));
	}
	
	public static Object getProxy(Object target) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass()); //设置代理类的父类
		enhancer.setCallback(new MyMethodInterceptror()); //设置代理方法中调用的对象
		return enhancer.create(); //动态生成字节码
	}
}

class MyMethodInterceptror implements MethodInterceptor{

	public Object intercept(Object target, Method targetMethod, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("----------before method " + targetMethod.getName() + "----------");
		Object ret = proxy.invokeSuper(target, args);
		System.out.println("----------after method " + targetMethod.getName() + "----------");
		return ret;
	}
}

class ServiceImpl1{
	public String get(int id) {
		System.out.println("get方法调用");
		return "get" + id;
	}
}