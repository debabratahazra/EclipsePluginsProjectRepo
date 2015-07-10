package com.tel.autosysframework.internal;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayOperation {


	/**
	 * Resize the Array Object
	 * @param Object
	 * @param size
	 * @return Object
	 */
	public static Object expand(Object array, int size) {
		Class cl = array.getClass();
		if (!cl.isArray()) return null;
		int length = Array.getLength(array);
		int newLength = size;
		Class componentType = array.getClass().getComponentType();
		Object newArray = Array.newInstance(componentType, newLength);
		System.arraycopy(array, 0, newArray, 0, length);
		return newArray;
	}

	
	/**
	 * Array Object element sort method
	 * @param unsort-array
	 * @return sort-array
	 */
	public static int[] sort(int[] array){
		Arrays.sort(array);
		return array;
	}
}
