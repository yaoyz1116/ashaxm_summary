package com.ashaxm.top.basese;

import java.util.Arrays;

public class ArrayTest {

	public static void main(String[] args) {
		int[] src = new int[3];
		src[0]=3;
		src[1]=9;
		src[2]=10;
		System.out.println(Arrays.toString(src));
		src = grow(src, 8);
		System.out.println(Arrays.toString(src));
	}
	
	public static int[] grow(int[] src, int size) {
		
		int[] target = new int[src.length+size];
		System.arraycopy(src, 0, target, 0, src.length);
		return target;
	}
}
