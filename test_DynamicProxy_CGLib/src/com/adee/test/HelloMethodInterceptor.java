package com.adee.test;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class HelloMethodInterceptor implements MethodInterceptor{
	@Override
	public Object intercept(Object origin, Method m, Object[] args, MethodProxy mProxy) throws Throwable {
		System.out.println("before:" + m.getName());
		Object ret = m.invoke(origin, args);
		System.out.println("after:" + m.getName());
		return ret;
	}
}
