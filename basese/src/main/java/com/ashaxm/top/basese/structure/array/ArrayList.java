package com.ashaxm.top.basese.structure.array;

import java.util.Arrays;

import com.ashaxm.top.basese.structure.Iterator;
import com.ashaxm.top.basese.structure.List;

public class ArrayList<T> implements List<T> {
	
	private int size;
	
	private Object[] elementData;
	
	private int DEFAULT_SIZE = 10;
	
	public ArrayList() {
		size = 0;
		elementData = new Object[DEFAULT_SIZE];
	}
	
	public void add(T o){
		verifyArray();
		elementData[size++] = o;
	}
	
	public void add(int index, T o){
		if(index >= elementData.length) {
			grow(elementData, index-elementData.length+1);
		}
		size = Math.max(size, index);
		elementData[index] = o;
	}
	
	@SuppressWarnings("unchecked")
	public T get(int index){
		if(index<0 || index > size) {
			return null;
		}
		return (T)elementData[index];
	}
	
	public T remove(int index){
		if(index<0 || index > size) {
			return null;
		}
		@SuppressWarnings("unchecked")
		T oldData = (T)elementData[index];
		for(int idx = index; idx<size; idx++) {
			elementData[idx] = elementData[idx+1];
		}
		elementData[size--] = null;
		return oldData;
	}
	
	public int size(){
		return size;
	}
	
	public Iterator iterator(){
		return null;
	}
	
	@Override
	public String toString() {
		return size+"-------"+Arrays.toString(elementData);
	}
	
	private Object[] grow(Object[] src, int size) {
		Object[] ret = new Object[src.length+size];
		System.arraycopy(src, 0, ret, 0, src.length);
		return ret;
	}
	
	private void verifyArray() {
		if(size == elementData.length) {
			elementData = grow(elementData, elementData.length+1);
		}
	}
 	
	public boolean isEmpty() {
		return size==0;
	}
}
