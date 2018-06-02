package com.adee.datastructure.tree.binary;

import com.adee.datastructure.tree.binary.BinaryTree.TreeNode;

/**
 * 遍历节点的回调函数类
 * @author Administrator
 *
 */
public abstract class NodeCallback<T extends Comparable> {
	public abstract void doCallback(final TreeNode<T> node);
}
