package com.ashaxm.top.basese.structure.array;

import java.util.Arrays;

import org.junit.Test;

public class ArrayUtil {
	
	/**
	 * 给定一个整形数组a , 对该数组的值进行置换
		例如： a = [7, 9 , 30, 3]  ,   置换后为 [3, 30, 9,7]
		如果     a = [7, 9, 30, 3, 4] , 置换后为 [4,3, 30 , 9,7]
	 * @param origin
	 * @return
	 */
	public void reverseArray(int[] origin){
		//不好的实现
//		int middle = origin.length/2;
//		int num = origin.length-1;
//		for(int i=0; i<middle; i++) {
//			int temp1 = i;
//			int temp2 = num-temp1;
//			int temp = origin[temp1];
//			origin[temp1] = origin[temp2];
//			origin[temp2] = temp;
//		}
		if(origin==null || origin.length<=1)
			return;
		for(int i=0,j=origin.length-1; i<=j; i++,j--) {
			int temp = origin[i];
			origin[i] = origin[j];
			origin[j] = temp;
		}
	}
	
	@Test
	public void test1() {
		int[] a = new int[]{1,2,3,4,5,6,7,8,9};
		reverseArray(a);
		System.out.println(Arrays.toString(a));
	}
	
	/**
	 * 现在有如下的一个数组：   int oldArr[]={1,3,4,5,0,0,6,6,0,5,4,7,6,7,0,5}   
	 * 要求将以上数组中值为0的项去掉，将不为0的值存入一个新的数组，生成的新数组为：   
	 * {1,3,4,5,6,6,5,4,7,6,7,5}  
	 * @param oldArray
	 * @return
	 */
	
	public int[] removeZero(int[] oldArray){
		//不好的实现
//		int[] temp = new int[oldArray.length];
//		int tempidx=0;
//		int zeroNum = 0;
//		for(int i=0; i<oldArray.length; i++) {
//			if(oldArray[i] !=0) {
//				temp[tempidx++] = oldArray[i];
//			}else {
//				zeroNum++;
//			}
//		}
//		int[] ret = new int[temp.length-zeroNum];
//		System.arraycopy(temp, 0, ret, 0, ret.length);
//		return ret;
		if(oldArray==null)
			return null;
		int[] temp = new int[oldArray.length];
		int tempIdx = 0;
		for(int i=0; i<oldArray.length; i++) {
			if(oldArray[i]!=0) {
				temp[tempIdx++] = oldArray[i];
			}
		}
		return Arrays.copyOf(temp,tempIdx);
	}
	
	@Test
	public void test2() {
		int[] oldArr={1,3,4,5,0,0,6,6,0,5,4,7,6,7,0,5};
		System.out.println(Arrays.toString(removeZero(oldArr)));
	}
	
	/**
	 * 给定两个已经排序好的整形数组， a1和a2 ,  创建一个新的数组a3, 使得a3 包含a1和a2 的所有元素， 并且仍然是有序的
	 * 例如 a1 = [3, 5, 7,8]   a2 = [4, 5, 6,7]    则 a3 为[3,4,5,6,7,8]    , 注意： 已经消除了重复
	 * @param array1
	 * @param array2
	 * @return
	 */
	
	public int[] merge(int[] array1, int[] array2){
		if(array1==null)
			return array2;
		if(array2==null)
			return array1;
		int aIdx = 0;
		int bIdx = 0;
		int[] temp = new int[array1.length+array2.length];
		for(int tIdx = 0 ; tIdx<temp.length; tIdx++) {
			if(aIdx==array1.length && bIdx==array2.length) {
				break;
			}else if(aIdx==array1.length) {
				temp[tIdx] = array2[bIdx++];
			}else if(bIdx==array2.length) {
				temp[tIdx] = array1[aIdx++];
			}else if(array1[aIdx] > array2[bIdx]) {
				temp[tIdx] = array2[bIdx++];
			}else if(array1[aIdx] < array2[bIdx]) {
				temp[tIdx] = array1[aIdx++];
			}else {
				temp[tIdx] = array1[aIdx];
				aIdx++;
				bIdx++;
			}
		}
		int retLength = 0;
		for(int i=temp.length-1; i>=0; i--) {
			if(temp[i] !=0) {
				retLength = i+1;
				break;
			}
		}
		return Arrays.copyOf(temp, retLength);
	}
	
	@Test
	public void test3() {
		int[] a1 = {3, 5, 7, 8};
		int[] a2 = {4, 5, 6, 7};
		System.out.println(Arrays.toString(merge(a1, a2)));
	}
	
	/**
	 * 把一个已经存满数据的数组 oldArray的容量进行扩展， 扩展后的新数据大小为oldArray.length + size
	 * 注意，老数组的元素在新数组中需要保持
	 * 例如 oldArray = [2,3,6] , size = 3,则返回的新数组为
	 * [2,3,6,0,0,0]
	 * @param oldArray
	 * @param size
	 * @return
	 */
	public int[] grow(int [] oldArray,  int size){
		int[] newArr = new int[oldArray.length+size];
		System.arraycopy(oldArray, 0, newArr, 0, oldArray.length);
		return newArr;
	}
	
	@Test
	public void test4() {
		int[] arr = {1,2,3,4,5};
		System.out.println(Arrays.toString(grow(arr, 8)));
	}
	
	/**
	 * 斐波那契数列为：1，1，2，3，5，8，13，21......  ，给定一个最大值， 返回小于该值的数列
	 * 例如， max = 15 , 则返回的数组应该为 [1，1，2，3，5，8，13]
	 * max = 1, 则返回空数组 []
	 * @param max
	 * @return
	 */
	public int[] fibonacci(int max){
		//不好的实现
//		int[] ret = new int[max];
//		int[] origin = {1,1};
//		if(max<=1) {
//			return new int[0];
//		}
//		fibonacci(ret, origin, max);	
//		return removeZero(ret);
		if(max<=1) {
			return new int[0];
		}
		int[] ret = new int[max];
		ret[0]=1;ret[1]=1;
		int retLength=1;
		for(int i=1; ret[i]<max; i++) {
			ret[i+1] = ret[i]+ret[i-1];
			retLength++;
		}
		return Arrays.copyOf(ret, retLength);
	}

//	private void fibonacci(int[] ret, int[] origin, int max) {
//		System.arraycopy(origin, 0, ret, 0, origin.length);
//		int[] temp = new int[origin.length+1];
//		System.arraycopy(origin, 0, temp, 0, origin.length);
//		int tempLength = temp.length;
//		temp[tempLength-1] = temp[tempLength-2]+temp[tempLength-3];
//		if(temp[temp.length-1] < max) {
//			fibonacci(ret, temp, max);
//		}
//	}
	
	@Test
	public void test5() {
		System.out.println(Arrays.toString(fibonacci(144)));
	}

	/**
	 * 返回小于给定最大值max的所有素数数组
	 * 例如max = 23, 返回的数组为[2,3,5,7,11,13,17,19]
	 * @param max
	 * @return
	 */
	public int[] getPrimes(int max){
		//不好的实现
//		int[] ret = new int[max];
//		getPrimes(ret,max);
//		return removeZero(ret);
		if(max<2) {
			return null;
		}
		int[] ret = new int[max];
		int retLength = 0;
		for(int i=2; i<max; i++) {
			if(isPrime(i)) {
				ret[retLength++] = i;
			}
		}
		return Arrays.copyOf(ret, retLength);
	}
	
//	private void getPrimes(int[] ret, int max) {
//		int[] temp = removeZero(ret);
//		int lastNum = 0;
//		if(temp.length > 0) {
//			lastNum = temp[temp.length-1]+1;			
//		}
//		while(!isPrime(lastNum)) {
//			lastNum++;
//		}
//		if(lastNum<max) {
//			ret[temp.length] = lastNum;
//			getPrimes(ret,max);
//		}
//	}
	
	@Test
	public void test6() {
		System.out.println(Arrays.toString(getPrimes(100)));
	}

	public boolean isPrime(int a) {	 
		boolean flag = true;
		if (a < 2) {// 素数不小于2
			return false;
		} else {
			for (int i = 2; i <= Math.sqrt(a); i++) {
				if (a % i == 0) {// 若能被整除，则说明不是素数，返回false
					flag = false;
					break;// 跳出循环
				}
			}
		}
		return flag;
	}
	
	/**
	 * 所谓“完数”， 是指这个数恰好等于它的因子之和，例如6=1+2+3
	 * 给定一个最大值max， 返回一个数组， 数组中是小于max 的所有完数
	 * @param max
	 * @return
	 */
	public int[] getPerfectNumbers(int max){
		//不好的实现
//		int[] ret = new int[max];
//		getPerfectNumbers(ret,max);
//		return removeZero(ret);
		int[] ret = new int[max];
		int retLength = 0;
		for(int i=1; i<max; i++) {
			if(isPrefectNum(i)) {
				ret[retLength++] = i;
			}
		}
		return Arrays.copyOf(ret, retLength);
	}
	
//	private void getPerfectNumbers(int[] ret, int max) {
//		int[] temp = removeZero(ret);
//		int lastNum = 1;
//		if(temp.length > 0) {
//			lastNum = temp[temp.length-1]+1;			
//		}
//		while(!isPrefectNum(lastNum)) {
//			lastNum++;
//			if(lastNum > max)
//				break;
//		}
//		if(lastNum<max) {
//			System.err.println("lastNum        "+lastNum);
//			ret[temp.length] = lastNum;
//			getPerfectNumbers(ret,max);
//		}
//	}
	
	private boolean isPrefectNum(int num) {
		// 定义因子和，默认加上了因子1
		int factorSum = 0;
		for (int i=1; i<num; i++) {
			// 如果num 模 i 等于0 ，说明i 为 num 的因子
			if (num % i == 0) {			
				// 将i值加到factorSum 上
				factorSum += i;
			}
		}		
		return factorSum == num;
	}

//	public boolean isPerfectnumber(int a) {	 
//		boolean flag = false;
//		for(int i=2; i<Math.sqrt(a); i++) {
//			if(a%i==0) {
//				List<Integer> list = new ArrayList<>();
//				list.add(i);
//				list.add(a/i);
//				findPerfectNumber(list,i);
//				findPerfectNumber(list,a/i);
////				System.out.println(list.toString());
//				int mtemp = 1;
//				int aTemp = 1;
//				for(Integer item : list) {
//					mtemp *= item;
//					aTemp += item;
//				}
//				if(a==mtemp && a==aTemp) {
//					return true;
//				}
//			}
//		}
//		return flag;
//	}
	
//	private void findPerfectNumber(List<Integer> list, int aim) {
//		if(aim<=2)
//			return;
//		for(int idx=2; idx<Math.sqrt(aim); idx++) {
//			if(aim%idx==0) {
//				list.add(idx);
//				list.add(aim/idx);
//				findPerfectNumber(list, idx);
//				findPerfectNumber(list, aim/idx);
//			}
//		}
//	}

	@Test
	public void test7() {
		System.out.println(Arrays.toString(getPerfectNumbers(999)));
	}


	
	/**
	 * 用seperator 把数组 array给连接起来
	 * 例如array= [3,8,9], seperator = "-"
	 * 则返回值为"3-8-9"
	 * @param array
	 * @param s
	 * @return
	 */
	public String join(int[] array, String seperator){
		StringBuilder sb = new StringBuilder();
		for(int idx = 0; idx<array.length; idx++) {
			if(idx<array.length-1) {
				sb.append(array[idx]+seperator);
			}else {
				sb.append(array[idx]);
			}
		}
		String ret = sb.toString();
		return ret;
	}
	
	@Test
	public void test8() {
		int[] arr = {1,3,5,7,9};
		String sep = "---";
		System.out.println(join(arr, sep));
	}
	
	

}
