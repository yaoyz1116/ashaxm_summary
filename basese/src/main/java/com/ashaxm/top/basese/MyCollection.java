package com.ashaxm.top.basese;

import java.util.Iterator;

public interface MyCollection<T> extends Iterable<T>{

	int size();
	
	boolean isEmpty();
	
	boolean contains(Object o);
	
	@Override
	Iterator<T> iterator();
	
	Object[] toArray();
	
	boolean add(T t);
	
	boolean remove(Object o);
	
	void clear();
	
	
}
