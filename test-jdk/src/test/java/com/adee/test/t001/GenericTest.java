package com.adee.test.t001;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GenericTest {
	@Before
	public void before() {
		
	}
	@After
	public void after() {
		
	}
	
	/**
	 * 一、泛型简介
	 * 		泛型即参数化类型，可以在不创建新类型的情况下，通过泛型指定的不同类型来控制形参具体限制的类型。也就是说在泛型使用的过程中，
	 * 	操作的数据类型被指定为一个参数，这种参数可以用在类、接口和方法中，分别称为泛型类、泛型接口和泛型方法。
	 * 		其实，泛型的作用就是用于类型限制。
	 * 	举个例子：
	 * 				List arrayList = new ArrayList();
						arrayList.add("aaaa");
						arrayList.add(100);
						
						for(int i = 0; i< arrayList.size();i++){
						    String item = (String)arrayList.get(i);
						    Log.d("泛型测试","item = " + item);
						}
			如上程序运行会抛异常：
				java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
		二、泛型的使用：泛型类、泛型接口、泛型方法
				1.泛型类 Generic
				2.泛型接口 GenericInterface
				3.泛型方法 GenericMethod
					泛型类是在实例化类的时候指明泛型的具体类型，泛型方法是在调用方法的时候指明泛型的具体类型。
					对于泛型方法的说明：
						1.public与返回值类型之间的<T>非常重要，可以理解为声明此方法为泛型方法
						2.只有声明了<T>的方法才是泛型方法，而泛型类中只有T的成员方法并不是泛型方法
						3.<T>表明该方法将使用泛型类型T，此时才可以在方法体和形参中使用泛型类型T，而泛型类中只有T的方法是不能在方法体中使用T的
						4.与泛型类的定义一样，此处T可以随便写为任意标识，如常见的T、E、K、V等
						5.泛型方法中的泛型标识符可以放到哪些地方？
							1.泛型方法的形参类型处
							2.泛型方法的局部变量类型处
							3.泛型类的局部变量和泛型类的形参和泛型类的返回类型处，如：
								public static <T> List<T> get(Class<T> clazz) throws Exception{
									List<T> list = new ArrayList<T>();
									list.add(clazz.newInstance());
									list.add(clazz.newInstance());
									return list;
								}
							4.泛型方法调用时，可不指定泛型类型，编译器可从从引用类型反向推断出来：
								继以上例子，调用以上泛型方法：
								List<User> users = Generator.get(User.class);
						6.例如：
							public <T> T genericMethod(Class<T> tClass) throws InstantiationException, IllegalAccessException{
								T instance = tClass.newInstance();
								return instance;
							}
						7.在Spring的ApplicationContext.getBean方法就使用了泛型方法：
							接口中声明方法：
							<T> T getBean(String name, Class<T> requiredType) throws BeansException;
							
		三、泛型通配符
				现在，我们有如下需求：有如下的方法：
					class GenericWhatever{
						public void showKey(Generic<Number> element){
							syso(element.getKey());
						}
					}
					问题是：可以将Generic<Integer>类型的参数传入该方法吗？否！，只能是Generic<Number>类型的参数，尽管Integer是
									Number的子类，也不可以。
					如何解决以上问题呢？使用泛型通配符：
						class GenericWhatever{
							public void showKey(Generic<?> element){
								syso(element.getKey());
							}
						}
					解释：?相当于是泛型参数的基类，类似于java类的Object，可以传入任何类型参数。
					
	 */
	
	//测试泛型类
	@Test
	public void test01() {
		Generic<Integer> g = new Generic<Integer>(1);
		int i = g.getKey();
		System.out.println(i);
		
		//注意，不能对传了泛型参数类型的类做instanceof操作，否则编译报错。但可以对未传类型参数的泛型类做instanceof操作
		//if(g instanceof Generic<Integer>) { }
		if(g instanceof Generic) { }
	}
	
	//测试泛型通配符
	@Test
	public void test02() {
		GenericWhatever gw = new GenericWhatever();
		Generic<Integer> g1 = new Generic<Integer>(1);
		Generic<String> g2 = new Generic<String>("hello");
		gw.showKey(g1);
		gw.showKey(g2);
	}
	
	//测试泛型方法
	@Test
	public void test03() {
		GenericMethod gm = new GenericMethod();
		try {
			Object obj = gm.genericMethod(Object.class);
			System.out.println(obj);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	//测试泛型方法2
		@Test
		public void test04() {
			try {
				List<User> users = GenericMethod.get(User.class);
				for(User u: users) {
					System.out.println(u.getName());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	//测试泛型
		@Test
		public void test05() {
			List<Object> list = new ArrayList<Object>();
			//List<Object> list1 = new ArrayList<String>(); //报错，因为泛型类中的类型参数不支持父类指向子类
			
			List<?> list2 = new ArrayList<String>(); //等价于：List list2 = new ArrayList<String>();
			Iterator<?> it = list2.iterator(); //iterator()方法为ArrayList<E>泛型类中的方法，定义为Iterator<E> iterator();，所以返回list2的实际泛型类型，即List<?>，等价于List
		}
}
