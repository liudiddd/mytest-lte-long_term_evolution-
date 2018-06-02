package com.adee.test.t001;

import java.util.List;

/**
 * 泛型接口：
 * 1.泛型接口与泛型类基本类似，首先在接口后面加<泛型标识符>
 * 2.在泛型接口内部，泛型标识符的位置可为：成员方法返回参数（接口无static方法）类型、成员方法参数类型。注：接口无成员变量
 * 3.泛型接口的实现类也要有泛型标识符，如class RengongGenerator<E> implements Generator<E>
 * 4.泛型接口需求举例：
 * 		例1：java的容器类List<E>就是一个泛型接口，其实现类有两个：
 * 						class ArrayList<E> implements List<E>
 * 						class LinkedList<E> implements List<E>
 * 		例2：	我们有个需求要定义一个产生器的接口，这个产生器使用两种方式产生产品：手工和自动，并且产生器可以产生多种类型的产品。
 * 					基于以上需求，我们设计一个产生器接口，并使用泛型指定产品类型：
 * 							interface Generator<E>
 * 							方法：E generate(E[] elements);
 * 					然后设计两个实现类：手工产生器和自动产生器：
 * 							class ShougongGenerator<E> implements Generator<E>
 * 								E generate(E[] elements){
 * 									return elements[0];
 * 								}
 * 							class ZidongGenerator<E> implements Generator<E>
 * 								E generate(E[] elements){
 * 									return elements[random(elements.length)];
 * 								}
 * @author Administrator
 *
 * @param <E>
 */
public interface Generator <E>{
	public E generate(E[] elements);
}
