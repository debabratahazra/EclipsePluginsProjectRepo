package com.example;

public class ReverseString {

	public static void main(String[] args) {
		String str = "This is a string";
		String reverse = reverseIt(str);
		System.out.println(reverse);
		
		reverse = reverse(str);			// Another way to reverse String
		System.out.println(reverse);
	}
	
	public static String reverseIt(String source) {
	    int i, len = source.length();
	    StringBuilder dest = new StringBuilder(len);

	    for (i = (len - 1); i >= 0; i--){
	        dest.append(source.charAt(i));
	    }

	    return dest.toString();
	}

	public static String reverse(String stringToReverse){
		return reverseWithIndex(stringToReverse, stringToReverse.length()-1);
	}
	
	private static String reverseWithIndex(String stringToReverse, int index){
	    if(index == 0){
	        return stringToReverse.charAt(0) + "";
	    }

	    char letter = stringToReverse.charAt(index);
	    return letter + reverseWithIndex(stringToReverse, index-1);
	}
}
