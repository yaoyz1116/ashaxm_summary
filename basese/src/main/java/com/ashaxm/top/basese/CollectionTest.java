package com.ashaxm.top.basese;

import org.junit.Test;

public class CollectionTest {

	@Test
	public void testArrayList() {
		MyArrayList<Integer> ret = new MyArrayList<Integer>();
		System.out.println(ret);
		ret.add(12);
		ret.add(18);
		ret.add(88);
		System.out.println(ret);
		ret.remove(928);
		System.out.println(ret);
		ret.remove(12);
		System.out.println(ret);
	}
}
