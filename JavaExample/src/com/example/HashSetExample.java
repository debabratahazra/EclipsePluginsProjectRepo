package com.example;

import java.util.HashSet;

public class HashSetExample {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		HashSet shortSet = new HashSet();
		for (short i = 0; i < 100; i++) {
		    shortSet.add(i);		// Adding here Short Object only
		    shortSet.remove(i - 1);  // Try to remove Integer Object which is not found anytime.
		}
		System.out.println(shortSet.size());	// So total size will be 100 as nothing is removed.
	}

}
