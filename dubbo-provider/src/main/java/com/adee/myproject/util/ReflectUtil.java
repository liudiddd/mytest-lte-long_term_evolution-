package com.adee.myproject.util;

import java.lang.reflect.Field;

public class ReflectUtil {
	private String name;
	public static Object getFieldValue(Object obj, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field f = obj.getClass().getDeclaredField(fieldName);
		boolean a = f.isAccessible();
		f.setAccessible(true);
		Object ret = f.get(obj);
		f.setAccessible(a);
		return ret;
	}
	
	public static void setFieldValue(Object obj, String fieldName, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field f = obj.getClass().getDeclaredField(fieldName);
		boolean a = f.isAccessible();
		f.setAccessible(true);
		f.set(obj, value);
		f.setAccessible(a);
	}
	
	public static Field getField(Object obj, String fieldName) throws NoSuchFieldException, SecurityException {
		Field f = obj.getClass().getDeclaredField(fieldName);
		f.setAccessible(true);
		return f;
	}
	
	
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		ReflectUtil r = new ReflectUtil();
		r.name = "Jack";
		System.out.println(ReflectUtil.getFieldValue(r, "name"));
	}
}
