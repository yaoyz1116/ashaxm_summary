package com.ashaxm.top.basese;

import java.util.Arrays;
import java.util.Iterator;

public class MyArrayList<T> implements MyCollection<T>{
	
	private Object[] aim;
	private int position;
	
	public MyArrayList() {
		aim = new Object[32];
		position=0;
	}

	@Override
	public int size() {
		return aim.length;
	}

	@Override
	public boolean isEmpty() {
		return size()==0;
	}

	@Override
	public boolean contains(Object o) {
		if(o==null)
			return false;
		if(get(o) == -1)
			return false;
		return true;
	}

	@Override
	public Iterator<T> iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return aim;
	}

	@Override
	public boolean add(T t) {
		if(position==aim.length)
			grow(aim,aim.length+1);
		aim[position++]=(Object)t;
		return true;
	}

	private Object[] grow(Object[] src, int size) {
		Object[] target = new Object[src.length+size];
		System.arraycopy(src, 0, target, 0, src.length);
		return target;
	}

	@Override
	public boolean remove(Object o) {
		if(!contains(o))
			return false;
		int idx = get(o);
		for(int i=idx; i<aim.length-1; i++) {
			aim[i] = aim[i+1];
		}
		position--;
		return true;
	}

	@Override
	public void clear() {
		aim = new Object[32];
		position = 0;
	}
	
	public int get(Object o) {
		if(o==null)
			return -1;
		for(int i=0; i<aim.length; i++) {
			if(o.equals(aim[i]))
				return i;
		}
		return -1;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(aim);
	}

}
