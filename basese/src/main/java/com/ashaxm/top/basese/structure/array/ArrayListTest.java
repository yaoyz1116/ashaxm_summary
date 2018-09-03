package com.ashaxm.top.basese.structure.array;

import org.junit.Test;

public class ArrayListTest {

	@Test
	public void test() {
		ArrayList list = new ArrayList();
		System.out.println(list);
		list.add(10);
		list.add(20);
		list.add(30);
		list.add(40);
		list.add(50);
		System.out.println(list);
		list.add(60);
		list.add(70);
		list.add(80);
		list.add(90);
		list.add(100);
		System.out.println(list);
		list.add(110);
		list.add(120);
		list.add(130);
		list.add(140);
		System.out.println(list);
		list.add(5, 555);
		System.out.println(list);
		list.add(150);
		System.out.println(list);
		list.remove(10);
		System.out.println(list);
		list.remove(1);
		System.out.println(list);
		


	}
}
