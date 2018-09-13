package com.ashaxm.top.basese.structure.linkedlist;

public class LRUPageFrame {
	
	private static class Node {		
		Node prev;
		Node next;
		int pageNum;
	}

	private int capacity;
	
	private int currentSize;
	private Node first;// 链表头
	private Node last;// 链表尾

	
	public LRUPageFrame(int capacity) {
		this.currentSize = 0;
		this.capacity = capacity;
		first = null;
		last = null;
	}

	/**
	 * 获取缓存中对象
	 */
	public void access(int pageNum) {
		boolean exist = false;
		Node temp = first;
		Node aim = new Node();
		aim.pageNum=pageNum;
		while(temp!=null) {
			if(temp.pageNum==pageNum) {
				if(temp.prev==null) {
					//当前缓存与第一个元素相同，什么都不动就可以了
				}else if(temp.next==null){
					//当前缓存中最后一个元素相同
					if(currentSize==capacity){
						last=last.prev;
					}
					temp.prev.next = temp.next;
					Node temp2 = first;
					first=aim;
					first.next=temp2;
					temp2.prev=first;			
				}else{	
					temp.prev.next = temp.next;
					temp.next.prev=temp.prev;
					Node temp2 = first;
					first=aim;
					first.next=temp2;
					temp2.prev=first;
				}
				exist = true;
				break;
			}
			temp=temp.next;
		}
		if(!exist) {
			if(currentSize==0) {
				first=aim;
				currentSize++;
			}else if(currentSize<capacity) {
				Node temp3 = first;
				first=aim;
				aim.next=temp3;
				temp3.prev=aim;
				currentSize++;
				if(currentSize==capacity) {
					Node temp6 = first;
					while(temp6.next!=null) {
						temp6 = temp6.next;
					}
					last=temp6;
				}
			}else {
				Node temp3 = first;
				first=aim;
				first.next=temp3;
				temp3.prev=first;
				Node temp7 = last.prev;
				temp7.next=null;
				last=temp7;
			}
		}
	}
	
	
	public String toString(){
		StringBuilder buffer = new StringBuilder();
		Node node = first;
		while(node != null){
			buffer.append(node.pageNum);						
			node = node.next;
			if(node != null){
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
	
	

}
