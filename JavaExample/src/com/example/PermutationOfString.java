package com.example;

/**
 * This class will print all possible way
 * of permutation of a given string
 * @author DEBABRATA
 *
 */
public class PermutationOfString {
	
	private static final String STRING = "abcde";
	private static int total_count = 0;

	public static void main(String[] args) {
		permutation(STRING);
	}
	
	public static void permutation(String str) { 
	    permutation("", str);
	    System.out.println("Total number of permutation is: " + total_count);
	}

	private static void permutation(String prefix, String str) {
	    int n = str.length();
	    if (n == 0){
	    	total_count++;
	    	System.out.println(prefix);
	    }
	    else {
	        for (int i = 0; i < n; i++)
	            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
	    }
	}
}
