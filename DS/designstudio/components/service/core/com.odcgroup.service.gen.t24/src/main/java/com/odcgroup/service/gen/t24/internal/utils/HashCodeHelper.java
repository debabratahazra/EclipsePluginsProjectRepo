package com.odcgroup.service.gen.t24.internal.utils;

import com.odcgroup.service.gen.t24.internal.data.Cardinality;
import com.odcgroup.service.gen.t24.internal.data.Complexity;
import com.odcgroup.service.gen.t24.internal.data.Direction;

public class HashCodeHelper {

	public static int getHashCode(Cardinality cardinality) {
		if (cardinality == null) {
			return 0;
		} else if (Cardinality.MULTIPLE == cardinality) {
			return 2;
		} else {
			return 1;
		} 
	}
	
	public static int getHashCode(Complexity complexity) {
		if (complexity == null) {
			return 0;
		} else if (Complexity.COMPLEX == complexity) {
			return 2;
		} else {
			return 1;
		} 
	}
	
	public static int getHashCode(Direction direction) {
		if (direction == null) {
			return 0;
		} else if (Direction.RETURN == direction) {
			return 4;
		} else if (Direction.OUT == direction) {
			return 3;
		} else if (Direction.INOUT == direction) {
			return 2;
		} else {
			return 1;
		} 
	}
}
