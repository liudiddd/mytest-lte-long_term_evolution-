package com.adee.test.t010;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 * jdk动态代理要求目标类必须实现接口
 * jdk动态代理原理：
 * 	1.动态生成一个类，这个类实现了给定的所有接口
 * 	2.该类中有一个InvocationHandler类型的成员变量，为给定的invocationHandler
 * 	3.使用Proxy.newProxyInstance(cl, ifaces, h);方法来生成目标类的代理对象
 * 	4.可以将将代理对象强制类型转换为给定的任何接口类型
 * 	5.将代理对象强制类型转换为某个接口类型后，就可以调用接口的方法了
 * 	6.在接口方法中其实调用的是invocationHandler.invoke(Object proxy, Method targetMethod, Object[] args);方法
 * 	7.通过设置jvm系统参数将动态生成的字节码文件生成.class文件：
 * 		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");  
 * 		动态代理类的package与目标类的package一样。
 * 		E:\eclipse_workspace\test-jdk\com\adee\test\t010\$Proxy0.class
 * 
 * @author Administrator
 *
 */
public class DynamicProxy01_JDK {
	public static void main(String[] args) {
		System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");  
		Service p = (Service)getProxy(DynamicProxy01_JDK.class.getClassLoader(), ServiceImpl.class.getInterfaces(),
				new MyInvocationHandler(new ServiceImpl()));
		p.save(1, "Jack");
	}
	
	public static Object getProxy(ClassLoader cl, Class[] ifaces, InvocationHandler h) {
		return Proxy.newProxyInstance(cl, ifaces, h);
	}
}

class MyInvocationHandler implements InvocationHandler{
	//目标对象
	private Object target;
	
	public MyInvocationHandler(Object target) {
		super();
		this.target = target;
	}
	
	public Object invoke(Object proxy, Method targetMethod, Object[] args) throws Throwable {
		System.out.println("----------before method " + targetMethod.getName() + "----------");
		Object ret = targetMethod.invoke(target, args);
		System.out.println("----------after method " + targetMethod.getName() + "----------");
		return ret;
	}
}

interface Service{
	public void save(int id, String name) ;
	public String get(int id);
}

class ServiceImpl implements Service{
	public void save(int id, String name) {
		System.out.println("save id: " + id + ", name:" + name);
	}
	
	public String get(int id) {
		return "this is no " + id;
	}
}