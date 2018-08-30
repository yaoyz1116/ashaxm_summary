package com.ashaxm.top.basese;

import java.util.Iterator;

import com.sun.jndi.toolkit.ctx.HeadTail;

public class MylinkedList<T> implements MyCollection<T>{
	
	private static Node head;
	private static int size;
	
	public MylinkedList() {
		head = null;
		size = 0;
	}
		
	private static class Node<T>{
		T data;
		Node next;
		public Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size==0;
	}

	@Override
	public boolean contains(Object o) {
		if(head==null || o==null)
			return false;
		Node<?> temp = head;
		while(temp != null) {
			if(o.equals(temp.data))
				return true;
			temp = temp.next;
		}
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object[] toArray() {
		Object[] ret = new Object[size];
		int tempSize = 0;
		Node<T> temp = head;
		while(temp != null) {
			ret[tempSize++] = temp.data;
			temp = temp.next;
		}
		return ret;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean add(T t) {
		Node<T> temp = new Node<T>(t, null);
		if(head==null)
			head = temp;
		else {
			Node<T> tempHead = head;
			while(tempHead != null) {
				tempHead = tempHead.next;
				if(tempHead==null)
					tempHead=temp;
			}
		}
		size++;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if(head==null || o==null)
			return false;
		Node<T> temp = head;
		while(temp==null) {
			if(o.equals(temp.data)) {
				
			}
		}
		return false;
	}

	@Override
	public void clear() {
		head=null;
		size=0;
	}

}
