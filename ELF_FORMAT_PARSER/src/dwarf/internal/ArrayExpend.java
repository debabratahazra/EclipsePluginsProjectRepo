/************************************************************************
 * Copyright© TATA ELXSI LTD.
 * Class Name: ArrayExpend
 * @version: 1.0
 * Date: June 2009 
 * @author Debabrata Hazra
 * Description: Expand the any type of Array size with given value.
 ************************************************************************/

package dwarf.internal;

import java.lang.reflect.Array;

public class ArrayExpend {

	public ArrayExpend() {		
	}

	/**
	 * Expand the array size with its new length value
	 * and existing array elements are still remain in its 
	 * own array position.
	 * @param array
	 * @param newLength
	 * @return Object
	 */
	public static Object expend(Object array, int newLength){		
		Class cl = array.getClass();
		if (!cl.isArray())
			return array;
		int oldLength = Array.getLength(array);
		int curLength = newLength;
		Class componentType = array.getClass().getComponentType();
		Object newArray = Array.newInstance(componentType, curLength);
		System.arraycopy(array, 0, newArray, 0, oldLength);
		return newArray;
	}

}
