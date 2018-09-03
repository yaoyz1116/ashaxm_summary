package com.ashaxm.top.basese.structure.tree;

import java.util.List;


public class BinarySearchTree<T extends Comparable<?>> {
	
	BinaryTreeNode<T> root;
	int size;
	
	public BinarySearchTree(BinaryTreeNode<T> root){
		this.root = root;
		this.size = 0;
	}
	public BinaryTreeNode<T> getRoot(){
		return root;
	}

	public T findMin(){
		BinaryTreeNode<T> temp = root;
		while(temp.left!=null) {
			temp = temp.left;
		}
		return temp.data;
	}
	public T findMax(){
		BinaryTreeNode<T> temp = root;
		while(temp.right != null)
			temp = temp.right;
		return temp.data;
	}
	public int height() {
	    return -1;
	}
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size()==0;
	}	
	public void remove(T e){
		
	}
	public List<T> levelVisit(){
		
		return null;
	}
	public boolean isValid(){
		return false;
	}
	public T getLowestCommonAncestor(T n1, T n2){
		return null;
        
	}
	/**
	 * 返回所有满足下列条件的节点的值：  n1 <= n <= n2 , n 为
	 * 该二叉查找树中的某一节点
	 * @param n1
	 * @param n2
	 * @return
	 */
	public List<T> getNodesBetween(T n1, T n2){
		return null;
	}
	
}

