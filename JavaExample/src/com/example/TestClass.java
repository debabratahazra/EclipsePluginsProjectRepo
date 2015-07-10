package com.example;

public class TestClass {
	
	private String str;

	public static final void main(String[] args) {
		
		TestClass cl = new TestClass();
		System.out.println(cl.str);
		System.out.println("END");
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}
}

interface I {
	void m1();
	void m2();
}

abstract class A implements I {

	@Override
	public void m1() {
	}
}