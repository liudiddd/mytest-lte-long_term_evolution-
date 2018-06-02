package com.adee.test.t002;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.adee.test.t001.Generic;

public class CollectionTest {
	@Before
	public void before() {
		
	}
	@After
	public void after() {
		
	}
	
	/**
	 * 一、Java容器关系
	 * 		Collection接口
	 * 				Set接口
	 * 						HashSet类
	 * 								LinkedHashSet类
	 * 						TreeSet类
	 * 				List接口
	 * 						ArrayList类
	 * 						LinkedList类
	 * 				Queue接口
	 * 						PriorityQueue类
	 * 		Map接口
	 * 				HashMap类
	 * 				TreeMap类
	 * 		Collections工具类
	 * 		Arrays工具类
	 * 二、容器使用注意事项（4个凡是，1个逻辑）
	 * 		1.凡是把类对象放到容器中，相应的类都应该重写Object中toString方法；
	 * 		2.凡是需要比较排序的类都应该实现Comparable接口的compareTo方法；
	 * 		3.凡是把类对象放入以树为内部结构的容器中都应该实现Comparable接口的compareTo方法；
	 * 		4.凡是把类对象放入以哈希表为内部存储结构的容器中，相应类的equals方法和hashCode方法必须保持一致，这样才符合哈希表真是的逻辑功能。
	 * 三、哈希冲突
	 * 		本质上讲就是:hash(对象1.hashCode())=hash2(对象2.hashCode())，即第一个对象的hashCode()方法返回的哈希码值带入到哈希函数后得到的索引位置与第二个对象的hashCode()方法返回的哈希码值带入到哈希函数后得到的索引位置相同，这就是哈希冲突。 
				最常见的哈希算法是取模法。 
				下面简单讲讲取模法的计算过程。 
				比如：数组的长度是5。这时有一个数据是6。那么如何把这个6存放到长度只有5的数组中呢。按照取模法，计算6％5，结果是1，那么就把6放到数组下标是1的位置。那么，7 
				就应该放到2这个位置。到此位置，哈斯冲突还没有出现。这时，有个数据是11，按照取模法，11％5＝1，也等于1。那么原来数组下标是1的地方已经有数了，是6。这时又计算出1这个位置，那么数组1这个位置，就必须储存两个数了。这时，就叫哈希冲突。冲突之后就要按照顺序来存放了。 
				如果数据的分布比较广泛，而且储存数据的数组长度比较大。 
				那么哈希冲突就比较少。否则冲突是很高的。	
	 * 四、Iterator接口
	 * 		Iterator接口是容器的遍历器，使用游标的方式遍历容器内部的数据结构，重要方法如下：
	 * 			boolean hasNext():是用来判断当前游标(迭代器)的后面是否存在元素，如果存在返回真，否则返回假
					Object next()：先返回当前游标右边的元素，然后游标后移一个位置
					void remove()：不推荐使用iterator的remove()方法，而是推荐使用容器自带的remove方法。
		五、容器具体实现
				1.List接口
					ArrayList类
					1.特点是随机访问元素快，但在中间插入和删除元素慢
					2.内部维护一个transient泛型数组（默认长度为10），随机访问元素的时间复杂度和数组一样为1，但由于插入删除时会将插入删除
						的元素位置后面的所有元素集体向后或向前移动，由于位置不确定，所以时间复杂度为n
					LinkedList类
					1.内部维护了一个链表，插入删除元素快，但随即访问元素慢
				2.Set接口
					HashSet类
					1.内部维护了一个private transient HashMap<E, Object>成员变量，HashSet的元素作为HashMap的key，value时固定的一个Object
					2.由于使用了hash算法，HashSet查询元素的速度比较快	
					TreeSet类
					1.红黑树实现
				3.Map接口
					HashMap类
					1.内部维护了一个数组，元素为HashMap.Node<K,V>类型
					TreeMap 
		六、fail-fast机制
				1.容器的遍历
					1.使用Iterator遍历ArrayList
						ArrayList父类abstract AbstractList维护了一个成员变量：protected transient int modCount = 0;表示List的版本号
						每向List中添加、删除一个元素E时都会modCount++，意为该List对象的版本号加1。
						但调用arrayList.iterator()方法时，返回一个Iterator接口类型的对象private Itr implements Iterator<E>，通过其next()方法
						遍历时，其实访问的arrayList.elementData[]数组，next()方法中会检查arrayList对象的版本号有没有变，如果变了，说明在遍历
						期间arrayList被其他线程或者当前线程添加或删除元素了，则抛出ConcurrentModificationException。这就是fail-fast机制。
					2.使用foreach循环遍历arrayList
						for(String s : arrayList)...
						由于foreach底层原理还是使用的Iterator，所以还是会有fail-fast发生的。
					3.使用for循环遍历arrayList
						由于for循环不涉及到版本号问题，不会有fail-fast发生，当时不保证在一个遍历时的数据有一个特定的快照版本，这种方法不可取。
					4.fail-fast发生场景
						一般会在多线程访问arrayList时发生。当在方法内部创建arrayList对象时，不会涉及到多线程同时访问，但如果arrayList对象为
						类变量或成员变量时，可能会被多个线程同时访问，可能会出现fail-fast。
						还有，即使在方法内部创建arrayList，但如果在遍历代码内部添加或删除了arrayList元素的话，也会出现fail-fast。
					5.fail-fast解决办法
						为保证遍历arrayList时有一个稳定的快照版本，防止fail-fast出现，可使用java.util.cuncurrent.CopyOnWriteArrayList
						该类与arrayList类似，内部也维护了一个数组，在add和remove方法中都是用了一个对象的全局锁：
						final transient ReentrantLock lock = new ReentrantLock();，保护方法同时只被一个线程执行，把原有数组做一个拷贝，
						然后对这个拷贝做add或remove操作，最后把拷贝赋给arrayList的数组引用，这样就不会发生fail-fast了。
					6.对于HashMap
						HashMap同样会有fail-fast问题。
						解决办法是：使用java.util.concurrent.ConcurrentHashMap类
						HashMap中key可为null，但ConcurrentHashMap的key不能为null，否则抛空指针异常。
						
			
		七、快速失败和安全失败
						Iterator的安全失败是基于对底层集合做拷贝，因此，它不受源集合上修改的影响。java.util包下面的所有的集合类都是快速失败的，
					而java.util.concurrent包下面的所有的类都是安全失败的。
						快速失败的迭代器会抛出ConcurrentModificationException异常，而安全失败的迭代器永远不会抛出这样的异常。
						ConcurrentHashMap中的迭代器主要包括entrySet、keySet、values方法。它们大同小异，这里选择entrySet解释。当我们
					调用entrySet返回值的iterator方法时，返回的是EntryIterator，在EntryIterator上调用next方法时，最终实际调用到了
					HashIterator.advance()方法。这个方法在遍历底层数组。在遍历过程中，如果已经遍历的数组上的内容变化了，迭代器不会抛出
					ConcurrentModificationException异常。如果未遍历的数组上的内容发生了变化，则有可能反映到迭代过程中。这就是
					ConcurrentHashMap迭代器弱一致的表现。ConcurrentHashMap的弱一致性主要是为了提升效率，是一致性与效率之间的
					一种权衡。要成为强一致性，就得到处使用锁，甚至是全局锁，这就与Hashtable和同步的HashMap一样了。
						Map m = Collections.synchronizedMap(new HashMap(...));由所有此类的“collection 视图方法”所返回的迭代器都是快速失败
					的：在迭代器创建之后，如果从结构上对映射进行修改，除非通过迭代器本身的 remove 方法，其他任何时间任何方式的修改，迭代器
					都将抛出 ConcurrentModificationException。因此，面对并发的修改，迭代器很快就会完全失败，而不冒在将来不确定的时间发生
					任意不确定行为的风险。
						注意，迭代器的快速失败行为不能得到保证，一般来说，存在非同步的并发修改时，不可能作出任何坚决的保证。快速失败迭代器尽
					最大努力抛出 ConcurrentModificationException。因此，编写依赖于此异常的程序的做法是错误的，正确做法是：迭代器的快速
					失败行为应该仅用于检测程序错误。
	 */
	
	//测试
	@Test
	public void test01() {
		Set<Object> s;
		HashSet<Object> ss;
		TreeSet<Object> sss;
	}
}
