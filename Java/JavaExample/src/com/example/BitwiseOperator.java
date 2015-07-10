package com.example;

public class BitwiseOperator {

	public static void main(String[] args) {
		Integer value = 0b1011_1011_1001_1110_1100_1110_1011_1011;
		System.out.println("Value: " + Integer.toBinaryString(value));
		Integer signedRight = value>>1;
		System.out.println("Sign right: " + Integer.toBinaryString(signedRight));
		Integer unsignedRight = (value >>> 1);
		System.out.println("Unsigned right: " + Integer.toBinaryString(unsignedRight));
		Integer left = (value << 1);
		System.out.println("Left shift: " + Integer.toBinaryString(left));
	}

}
