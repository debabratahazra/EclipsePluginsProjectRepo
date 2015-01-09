package design_pattern.mainclass;

import design_pattern.creational.Singleton;


public class MainSingleton {
	public static void main(String[] args) {
		A a1 = new A();
		B b1 = new B();
		Thread t1 = new Thread(a1);
		
		Thread t2 = new Thread(b1);
		t2.start();
		
		Singleton s = Singleton.getInstance();
		try {
			s.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		t1.run();
	}
}

class A implements Runnable{
	@Override
	public void run() {
		Singleton s = Singleton.getInstance();
		try {
			s.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
}



class B implements Runnable{
	@Override
	public void run() {
		Singleton s = Singleton.getInstance();
		try {
			s.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}
}