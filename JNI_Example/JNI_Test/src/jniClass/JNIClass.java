package jniClass;


import internal.Constructor;

public class JNIClass extends SuperClass{
		
	private String str;
	private static int static_val;
	
	@Constructor
	public JNIClass() {
		System.out.println("JNIClass Constructor");
	}
	
	@Constructor(Parameter="String")
	public JNIClass(String str){
		super();
		this.str = str;
		System.out.println("Parameterized Constructor call.");
		System.out.println("Value pass from C code : " + getStr());
	}
	
	static{
		System.loadLibrary("JNI_Test");
		//Runtime.getRuntime().loadLibrary("JNI_Test");
	}
	
	public native String addString(String str);						//String operation
	public native int showValue(Integer val);						//Integer class operation
	public native Double addFloatValues(Float f1, Float f2);		//Double, Float class operation
	public native int addValue(int value1, int value2);				//integer operation
	private native float addAllValue(float[] value, boolean cond);	//float return, float-array, boolean operation
	private native void accessInstanceField();						//Access Instance variable like String
	private native void accessStaticField();						//Access Static variable like integer
	private native void accessInstanceMethod();						//Access Instance method of this class
	private native void accessStaticMethod();						//Access Static method of this class
	private native void accessSuperClassInstanceMethod();			//Access Super class instance method which is override in sub-class
	//Access or calling parameterized constructor during object creation of this class in native C code
	private native void accessConstructor();						 
	
	
	public void instanceMethod(int value){
		System.out.println("Passing value of Instance Method : " + value) ;
	}
	
		
	public static void staticMethod(float value){
		System.out.println("Passing value of static method : " + value);
		setStatic_val(300);
		System.out.println("Value of static_val is : " + static_val);
	}

	
	@Override
	public void superMethod() {
		System.out.println("superMethod in JNIClass");
	}
	
	
	public static void main(String[] args) {
		
		String str = new JNIClass().addString("Hello");
		System.out.println(str);
		
		Integer in = new Integer(20);
		int getValue = new JNIClass().showValue(in);
		System.out.println("\nInteger value: " + getValue);
		
		Float f1 = new Float(10.5);
		Float f2 = new Float(20.3);
		Double addDVal = new JNIClass().addFloatValues(f1, f2);
		if(addDVal != null) 
			System.out.println("Double added Value : " + addDVal);
		
		int total = new JNIClass().addValue(10, 20);
		System.out.println(total);
		
		float[] value = {10.5f, 20.3f, 30.2f};
		float sum = new JNIClass().addAllValue(value, true);
		System.out.println(sum);
		
		JNIClass jni = new JNIClass();
		jni.setStr("Debu");
		jni.accessInstanceField();
		System.out.println("In Java, str : " + jni.getStr());
		
		JNIClass.setStatic_val(100);
		jni.accessStaticField();
		System.out.println("\nIn Java, static_val : " + JNIClass.getStatic_val());
		
		new JNIClass().accessInstanceMethod();
		
		new JNIClass().accessStaticMethod();
		
		new JNIClass().accessSuperClassInstanceMethod();
		
		new JNIClass().accessConstructor();
	}
	
	
	
	
	public void setStr(String str) {
		this.str = str;
	}
	public String getStr() {
		return str;
	}
	public static void setStatic_val(int static_val) {
		JNIClass.static_val = static_val;
	}
	public static int getStatic_val() {
		return static_val;
	}
}
