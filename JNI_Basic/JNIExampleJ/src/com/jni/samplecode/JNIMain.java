package com.jni.samplecode;

public class JNIMain {

	static{
		System.loadLibrary("JNIExampleCPP");
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("This is main method from Java");
		JNIMain main = new JNIMain();
		int returnCode = main.sayHello("Deb");
		if(returnCode != 0) {
			throw new Exception("Exception in CPP code.");
		}
	}
	
	public native int sayHello(String name);
}
