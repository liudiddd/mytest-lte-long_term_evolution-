package com.adee.datastructure.tree.binary;

import java.util.List;
import java.util.Stack;

import javax.swing.tree.TreeNode;

import org.w3c.dom.Node;

/**
 * 一、二叉树概念 
 * 		1.二叉树是树的一种 
 * 		2.二叉树每个节点的度不大于2，即每个节点最多有两个子节点
 * 		3.二叉树节点的两个子节点分别称为左子树和右子树，左子树和右子树不相交 
 * 二、二叉树的性质 
 * 		1.二叉树的第i层最多有2^(i-1)个节点（i>=1）
 * 		2.深度为k的二叉树最多有2^k-1个节点（k>=1） 
 * 		3.完全二叉树：深度为k的二叉树的节点个数如果为2^k-1，则为完全二叉树 
 * 
 * @author Administrator
 *
 */
public class BinaryTree<T extends Comparable> {
	private TreeNode<T> root = null;

	/**
	 * 创建一棵二叉树
	 */
	public void createBinTree(TreeNode<Integer> root) {
		TreeNode<Integer> nB = new TreeNode<Integer>(2);
		TreeNode<Integer> nC = new TreeNode<Integer>(3);
		TreeNode<Integer> nD = new TreeNode<Integer>(4);
		TreeNode<Integer> nE = new TreeNode<Integer>(5);
		TreeNode<Integer> nF = new TreeNode<Integer>(6);
		root.setLeftChild(nB); nB.setParent(root);
		root.setRightChild(nC); nC.setParent(root);
		nB.setLeftChild(nD); nD.setParent(nB);
		nB.setRightChild(nE); nE.setParent(nB);
		nC.setLeftChild(nF); nF.setParent(nC);
	}

	public boolean isEmpty() {
		return root == null;
	}

	// 树的高度
	public int height() {
		return height(root);
	}

	// 节点个数
	public int size() {
		return size(root);
	}

	private int height(TreeNode<T> subTree) {
		if (subTree == null)
			return 0;// 递归结束：空树高度为0
		else {
			int i = height(subTree.getLeftChild());
			int j = height(subTree.getRightChild());
			return (i < j) ? (j + 1) : (i + 1);
		}
	}

	private int size(TreeNode<T> subTree) {
		if (subTree == null) {
			return 0;
		} else {
			return 1 + size(subTree.getLeftChild()) + size(subTree.getRightChild());
		}
	}



	public TreeNode<T> getRoot() {
		return root;
	}

	/**
	 * 释放一个子树
	 * @param subTree
	 */
	public void destroy(TreeNode<T> subTree) {
		// 删除根为subTree的子树
		if (subTree != null) {
			// 先删除左子树
			destroy(subTree.getLeftChild());
			// 再删除右子树
			destroy(subTree.getRightChild());
			// 左右子树都删除后，最后删除根结点
			subTree = null;
		}
	}


	// 前序遍历
	public void preOrder(TreeNode<T> subTree, NodeCallback<T> nc) {
		if (subTree != null) {
			nc.doCallback(subTree);
			preOrder(subTree.getLeftChild(), nc);
			preOrder(subTree.getRightChild(), nc);
		}
	}

	// 中序遍历
	public void inOrder(TreeNode<T> subTree, NodeCallback<T> nc) {
		if (subTree != null) {
			inOrder(subTree.getLeftChild(), nc);
			nc.doCallback(subTree);
			inOrder(subTree.getRightChild(), nc);
		}
	}

	// 后续遍历
	public void postOrder(TreeNode<T> subTree, NodeCallback<T> nc) {
		if (subTree != null) {
			postOrder(subTree.getLeftChild(), nc);
			postOrder(subTree.getRightChild(), nc);
			nc.doCallback(subTree);
		}
	}

	// 前序遍历的非递归实现，有bug
	public void nonRecPreOrder(TreeNode<T> p, NodeCallback<T> nc) {
		/*Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();
		TreeNode<T> node = p;
		while (node != null || stack.size() > 0) {
			while (node != null) {
				nc.doCallback(node);
				stack.push(node);
				node = node.leftChild;
			}
			while (stack.size() > 0) {
				node = stack.pop();
				node = node.rightChild;
			}
		}*/
	}

	// 中序遍历的非递归实现，有bug
	public void nonRecInOrder(TreeNode p) {
		/*Stack<TreeNode> stack = new Stack<BinaryTree.TreeNode>();
		TreeNode node = p;
		while (node != null || stack.size() > 0) {
			// 存在左子树
			while (node != null) {
				stack.push(node);
				node = node.leftChild;
			}
			// 栈非空
			if (stack.size() > 0) {
				node = stack.pop();
				visted(node);
				node = node.rightChild;
			}
		}*/
	}

	// 后序遍历的非递归实现，有bug
	public void noRecPostOrder(TreeNode p) {
		/*Stack<TreeNode> stack = new Stack<BinaryTree.TreeNode>();
		TreeNode node = p;
		while (p != null) {
			// 左子树入栈
			for (; p.leftChild != null; p = p.leftChild) {
				stack.push(p);
			}
			// 当前结点无右子树或右子树已经输出
			while (p != null && (p.rightChild == null || p.rightChild == node)) {
				visted(p);
				// 纪录上一个已输出结点
				node = p;
				if (stack.empty())
					return;
				p = stack.pop();
			}
			// 处理右子树
			stack.push(p);
			p = p.rightChild;
		}*/
	}

	// 在二叉树中插入一个数
	public void insert(T data) {  
		TreeNode<T> node = new TreeNode<T>(data);
		if(this.root == null) {
			this.root = node;
		}else {
			TreeNode<T> q = this.root;
			while(true) {
				if(data.compareTo(q.getData()) < 0) {
					if(q.getLeftChild() == null) {
						q.setLeftChild(node);
						node.setParent(q);
						break;
					}else {
						q = q.getLeftChild();
					}
				}else if(data.compareTo(q.getData()) > 0) {
					if(q.getRightChild() == null) {
						q.setRightChild(node);
						node.setParent(q);
						break;
					}else {
						q = q.getRightChild();
					}
				}else {
					System.out.println(data + " 已存在");
				}
			}
		}
		
	}  

	/**
	 * 二叉树的节点数据结构
	 */
	public static class TreeNode<T extends Comparable> {
		private T data;
		private TreeNode<T> leftChild;
		private TreeNode<T> rightChild;
		private TreeNode<T> parent;
		
		public TreeNode(T data) {
			this.data = data;
		}
		
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
		public TreeNode<T> getLeftChild() {
			return leftChild;
		}
		public void setLeftChild(TreeNode<T> leftChild) {
			this.leftChild = leftChild;
		}
		public TreeNode<T> getRightChild() {
			return rightChild;
		}
		public void setRightChild(TreeNode<T> rightChild) {
			this.rightChild = rightChild;
		}
		public TreeNode<T> getParent() {
			return parent;
		}
		public void setParent(TreeNode<T> parent) {
			this.parent = parent;
		}
	}

	// 测试
	public static void main(String[] args) {

	}
}
