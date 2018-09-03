package com.ashaxm.top.basese.structure.linkedlist;

import java.util.Arrays;

import org.junit.Test;

public class LinkedlistTest {

	@Test
	public void test() {
		LinkedList list = new LinkedList();
		list.add(10);
		list.add(20);
		list.add(30);
		list.add(40);
		list.add(50);
		list.add(60);
		list.add(70);
		list.add(80);
		list.add(90);
		list.add(100);
		System.out.println(list);
		
		LinkedList list2 = new LinkedList();
		list2.add(10);
		list2.add(30);
		list2.add(50);
		System.out.println(list2);
		
		list.subtract(list2);
		System.out.println(list);
	}
}
