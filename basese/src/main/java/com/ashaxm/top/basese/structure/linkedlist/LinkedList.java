package com.ashaxm.top.basese.structure.linkedlist;

import java.util.Arrays;

import com.ashaxm.top.basese.structure.Iterator;
import com.ashaxm.top.basese.structure.List;

public class LinkedList implements List {
	
	public LinkedList() {
		head = new Node();
		head.data = null;
		head.next = null;
		size = 0;
	}
	
	private Node head;	
	
	private int size;
	
	public void add(Object o){
		Node newNode = new Node();
		newNode.data = o;
		newNode.next = null;
		if(head.next==null) {	
			head.next = newNode;
		}else {
			Node temp = head.next;
			while(temp != null) {
				if(temp.next==null) {
					temp.next = newNode;
					break;
				}
				temp = temp.next;
			}
		}
		size++;
	}
	
	public void add(int index , Object o){
		if(index<0 || index>size) {
			throw new RuntimeException("参数不正确");
		}
		Node newData = new Node();
		newData.data = o;
		newData.next = null;
		
		Node pre = head;
		Node temp = head.next;
		int tempIdx = 0;
		while(tempIdx<=index) {
			if(tempIdx==index) {
				pre.next = newData;
				newData.next = temp.next;
			}
			pre = temp;
			temp = temp.next;
			tempIdx ++;
		}
	}
	
	public Object get(int index){
		if(index<0 || index > size) {
			throw new RuntimeException("参数不正确");
		}
		int tempIndex = 0;
		Node temp = head.next;
		while(tempIndex <= index) {
			if(tempIndex==index) {
				return temp.data;
			}
			temp = temp.next;
			tempIndex++;
		}
		return null;
	}
	public Object remove(int index){
		if(index<0 || index > size) {
			throw new RuntimeException("参数不正确");
		}
		int tempIndex = 0;
		Node temp = head.next;
		Node pre = head;
		while(tempIndex <= index) {
			if(tempIndex == index) {
				pre.next = temp.next;
				size--;
				return temp.data;
			}
			pre = temp;
			temp = temp.next;
			tempIndex++;
		}
		return null;
	}
	
	public int size(){
		return size;
	}
	
	public void addFirst(Object o){
		Node nowNode = new Node();
		nowNode.data = o;
		if(head.next==null) {
			add(o);
		}else {
			nowNode.next = head.next;
			head.next=nowNode;
			size++;
		}
		
	}
	
	public void addLast(Object o){
		add(o);;
	}
	
	public Object removeFirst(){
		if(head.next==null)
			return null;
		Object oldData = head.next.data;
		head.next = head.next.next;
		size--;
		return oldData;
	}
	
	public Object removeLast(){
		Node pre = head;
		Node temp = head.next;
		while(temp.next!=null) {
			pre = temp;
			temp = temp.next;
		}
		Object oldData = temp.data;
		pre.next = null;
		size--;
		return oldData;
	}
	public Iterator iterator(){
		return null;
	}
	
	
	private static class Node{
		Object data;
		Node next;
	}
	
	public Object[] toArray() {
		Object[] ret = new Object[size];
		Node temp = head.next;
		int tempIdx = 0;
		while(temp!=null) {
			ret[tempIdx++] = temp.data;
			temp = temp.next;
		}
		return ret;
	}
	
	public void clear() {
		size=0;
		head.next=null;
	}
	
	/**
	 * 把该链表逆置
	 * 例如链表为 3->7->10 , 逆置后变为  10->7->3
	 */
	public  void reverse(){	
		
		Object[] ret = toArray();
		clear();
		for(int i=ret.length-1; i>=0; i--) {
			add(ret[i]);
		}
	}
	
	/**
	 * 删除一个单链表的前半部分
	 * 例如：list = 2->5->7->8 , 删除以后的值为 7->8
	 * 如果list = 2->5->7->8->10 ,删除以后的值为7,8,10

	 */
	public  void removeFirstHalf(){
		int half = size/2;
		int tempIdx = 0;
		Node temp = head.next;
		while(tempIdx < half) {
			temp = temp.next;
			tempIdx++;
		}
		head.next=temp;
		size = size-half;
	}
	
	/**
	 * 从第i个元素开始， 删除length 个元素 ， 注意i从0开始
	 * @param i
	 * @param length
	 */
	public  void remove(int i, int length){
		Node temp = head;
		Node pre = head;
		int tempIdx = 0;
		while(tempIdx < i+length+1) {
			if(tempIdx==i)
				pre = temp;
			temp = temp.next;
			tempIdx++;
		}
		pre.next=temp;
		size=size-length;
	}
	
	public boolean contain(Object o) {
		if(o==null)
			return false;
		Node temp = head.next;
		while(temp != null) {
			if(o.equals(temp.data))
				return true;
			temp = temp.next;
		}
		return false;
	}
	
	/**
	 * 假定当前链表和listB均包含已升序排列的整数
	 * 从当前链表中取出那些listB所指定的元素
	 * 例如当前链表 = 11->101->201->301->401->501->601->701
	 * listB = 1->3->4->6
	 * 返回的结果应该是[101,301,401,601]  
	 */
	public  Object[] getElements(LinkedList list){
		Object[] ret = new Object[list.size()];
		Node temp = head.next;
		int listIdx = 0;
		int tempIdx = 0;
		while(temp != null) {
			if(list.get(listIdx).equals(tempIdx)){
				ret[listIdx] = temp.data;
				listIdx++;
				if(listIdx>=list.size)
					break;
			}
			tempIdx++;
			temp=temp.next;
		}
		return ret;
	}
	
	/**
	 * 已知链表中的元素以值递增有序排列，并以单链表作存储结构。
	 * 从当前链表中中删除在listB中出现的元素 
	 */
	
	public  void subtract(LinkedList list){
		Node temp = head.next;
		Node pre = head;
		while(temp != null) {
			if(list.contain(temp.data)) {
				pre.next = temp.next;
				if(pre.next==null) {
					temp=null;
				}else {					
					temp=pre.next;
					size--;
				}
			}else {
				pre = temp;
				temp = temp.next;
			}
		}
	}
	
	/**
	 * 已知当前链表中的元素以值递增有序排列，并以单链表作存储结构。
	 * 删除表中所有值相同的多余元素（使得操作后的线性表中所有元素的值均不相同）
	 */
	public  void removeDuplicateValues(){
		
	}
	
	/**
	 * 已知链表中的元素以值递增有序排列，并以单链表作存储结构。
	 * 试写一高效的算法，删除表中所有值大于min且小于max的元素（若表中存在这样的元素）
	 * @param min
	 * @param max
	 */
	public  void removeRange(int min, int max){
		
	}
	
	/**
	 * 假设当前链表和参数list指定的链表均以元素依值递增有序排列（同一表中的元素值各不相同）
	 * 现要求生成新链表C，其元素为当前链表和list中元素的交集，且表C中的元素有依值递增有序排列
	 * @param list
	 */
	public  LinkedList intersection( LinkedList list){
		return null;
	}
	
	@Override
	public String toString() {
		Object[] ret = new Object[size];
		Node temp = head.next;
		int tempIndex = 0;
		while(temp!=null) {
			ret[tempIndex++] = temp.data;
			temp = temp.next;
		}
		return size+"------"+Arrays.toString(ret);
	}
}
