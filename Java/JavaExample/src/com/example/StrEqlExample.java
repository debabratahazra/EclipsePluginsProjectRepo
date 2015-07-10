package com.example;

public class StrEqlExample {

	public static void main(String[] args) {
		String s1 = "abc";
		String s2 = "abc";
		String s3 = new String("abc");
		System.out.println(s1 == s2);
		System.out.println(s1 == s3);
		System.out.println("s1 == s2 is: " + s1 == s2); // Evaluate like: ("s1 == s2 is: " + s1) == (s2)
		System.out.println("s1 == s2 is: " + (s1 == s2));
		System.out.println("s1 == s3 is: " + (s1 == s3));
		
		if(s1==s2) {
			System.out.println("s1 and s2 equal");
		}
		if(s1==s3){
			System.out.println("s1 and s3 equal");
		}
	}
}
