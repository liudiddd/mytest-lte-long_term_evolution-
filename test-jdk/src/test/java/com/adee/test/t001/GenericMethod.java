package com.adee.test.t001;

import java.util.ArrayList;
import java.util.List;

public class GenericMethod {
	public <T> T genericMethod(Class<T> tClass) throws InstantiationException, IllegalAccessException{
		T instance = tClass.newInstance();
		return instance;
	}
	
	//泛型方法中的泛型传递
	public static <T> List<T> get(Class<T> clazz) throws Exception{
		List<T> list = new ArrayList<T>();
		list.add(clazz.newInstance());
		list.add(clazz.newInstance());
		return list;
	}
}
