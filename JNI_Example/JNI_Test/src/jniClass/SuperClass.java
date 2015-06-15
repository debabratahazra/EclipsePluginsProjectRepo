package jniClass;

import internal.Constructor;

public class SuperClass {
	
	@Constructor
	public SuperClass() {
		System.out.println("SuperClass Constuctor");
	}
	
	public void superMethod(){
		System.out.println("superMethod in SuperClass");
	}

}
