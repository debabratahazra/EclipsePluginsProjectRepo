package com.example;

public class OverridingClass {

	public static void main(String[] args) {
		Clazz cl = new Clazz();
		Inter intf = new Clazz();
		SuperClz sup = new SuperClz();
		SuperClz sub = new Clazz();
		
		// cl object only call these 3 methods
		cl.intfDisplay();
		cl.superDisplay();
		cl.subDisplay();
		
		// intf object only call this 1 method
		intf.intfDisplay();
		
		// sup object only call this 1 method
		sup.superDisplay();
		
		// sub object only call this 1 method, but execute the subclass method body when it is Override in subclass
		// When not Override in subclass, then it will call on super class method.
		// It depends either this method is Overrride in sub class or not.
		sub.superDisplay();	// Polymorphism here
	}

}


interface Inter {
	public void intfDisplay();
}

 class SuperClz {
	void superDisplay() {
		System.out.println("super class display in super class");
	}
}

class Clazz extends SuperClz implements Inter {

	@Override
	public void intfDisplay() {
		System.out.println("display method of interface");
	}

	@Override
	void superDisplay() {
		System.out.println("super class display in subclass");
	}
	
	void subDisplay() {
		System.out.println("Sub class nomal display method");
	}
}