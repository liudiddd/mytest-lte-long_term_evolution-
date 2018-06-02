package com.adee.datastructure.tree.simple;

import org.junit.Before;
import org.junit.Test;

/**
 * 一、树的概念及特点
 * 		1.非空树有且只有一个根节点
 * 		2.树节点的子节点也是一棵树
 * 		3.节点的度：节点的子节点个数
 * 		4.树的度：树下各个节点度的最大值
 * 		5.从根节点到任意一个节点都有且只有一条路径
 * 		6.叶子节点：度为0的节点，即没有子节点的节点
 * 		7.节点的子树的根称为孩子（Child），该节点称为双亲（Parent），
 * 			双亲相同的节点称为兄弟（Sibling），其双亲在同一层的节点互称堂兄弟
 * 		8.节点的祖先（Ancestor）是：从根节点到该节点路径上的所有其他节点
 * 		9.有序树：树中的各个子树从左到右都是有顺序的（从小到大或从大到小），否则称为无序树
 * 			有序树中，最左子树的根称为第一个孩子，最右边的称为最后一个孩子
 * 		10.森林（Forest）：是m（m>0）棵互不相交的树的集合。对树中每个节点而言，其子树的集合即为森林。
 * 
 * 简单有序树的实现：
 * 1.树中的所有节点按插入的顺序放到一个一维数组中，首个元素为根元素
 * 2.每个节点下挂一个链表，放其所有孩子节点，节点从左到右依次增大
 * 3.保证节点左子树的所有节点都小于该节点，节点有子树的元素都大于该节点
 * 4.插入操作：先将元素放入数组中，然后从根节点开始比较，找到合适位置插入到孩子链表中
 * 5.查找操作：从根节点开始比较，找到相同节点并返回节点的引用
 * 5.删除操作：查找节点，找到后将链表中该节点引用置为null，再将一维数组中该元素的引用置为null（此实现中不做数组元素
 * 	移动，该缺口就放在那），返回true，如果没有找到，返回false
 * 6.更新操作：查找节点，找到后更新为指定节点并返回true，没有找到则返回false
 * 
 * @author Administrator
 *
 */
public class SimpleTree<N> {
	//一维数组，默认大小1024
	private static final int DEFAULT_ARRAY_LENGTH = 1024;
	private SimpleTreeNode<N>[] array;
	
	public SimpleTree() {
		this(DEFAULT_ARRAY_LENGTH);
	}
	
	public SimpleTree(int initCapacity) {
		if(initCapacity < 1 || initCapacity > Integer.MAX_VALUE) {
			array = new SimpleTreeNode[DEFAULT_ARRAY_LENGTH];
		}else {
			array = new SimpleTreeNode[initCapacity];
		}
	}
	
	//插入节点
	public void insert(N data) {
		assertNull(data);
		SimpleTreeNode<N> newNode = new SimpleTreeNode<N>(data); //泛型类的泛型传递
		SimpleTreeNode<N> root = array[0];
		
	}
	
	
	
	private void assertNull(N data) throws NullPointerException{
		if(data == null) throw new NullPointerException();
	}
	
	public static void main(String[] args) {
		
	}
}
