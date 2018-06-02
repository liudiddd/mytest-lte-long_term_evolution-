package com.adee.datastructure.tree.simple;

public class SimpleTreeNode<N> {
	private N data; //节点数据
	private SimpleTreeNode<N> firstChild; //节点的孩子链表 链表头元素
	
	public SimpleTreeNode(N data) {
		this.data = data;
	}
	
	public N getData() {
		return data;
	}
	public void setData(N data) {
		this.data = data;
	}
	public SimpleTreeNode<N> getFirstChild() {
		return firstChild;
	}
	public void setFirstChild(SimpleTreeNode<N> firstChild) {
		this.firstChild = firstChild;
	}

}
