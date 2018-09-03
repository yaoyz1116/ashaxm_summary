package com.ashaxm.top.basese.structure.stack;

import com.ashaxm.top.basese.structure.array.ArrayList;

public class Stack<T> {
	private ArrayList<T> elementData;
	
	public void push(Object o){	
		elementData = new ArrayList<>();
	}
	
	public T pop(){
		if(isEmpty())
			return null;
		T oldData = elementData.get(size()-1);
		elementData.remove(size()-1);
		return oldData;
	}
	
	public Object peek(){
		if(isEmpty())
			return null;
		return elementData.get(size()-1);
	}
	public boolean isEmpty(){
		return elementData.isEmpty();
	}
	public int size(){
		return elementData.size();
	}
	
		
}
