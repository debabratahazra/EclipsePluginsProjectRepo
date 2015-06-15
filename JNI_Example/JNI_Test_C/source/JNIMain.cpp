#include <Windows.h>
#include <iostream>
#include <string.h>
#include "..\header\jniClass_JNIClass.h"

using namespace std;


//Convert jstring to char*
char* jstringToChar(JNIEnv* env, jstring str)
{
	 int length = env->GetStringLength(str);
	 const jchar* jcstr = env->GetStringChars(str, 0 );
	 char* rtn = (char*)malloc( length * 2 + 1 );
	 int size = 0;
	 size = WideCharToMultiByte(
		 CP_ACP,
		 0,
		 (LPCWSTR)
		 jcstr,
		 length,
		 rtn,
		 (length*2 +1 ),
		 NULL,
		 NULL
		 );

	 if ( size <= 0 ) return NULL;
	 env->ReleaseStringChars( str, jcstr );
	 rtn[ size ] = 0;
	 return rtn;
}


//String operation
JNIEXPORT jstring JNICALL Java_jniClass_JNIClass_addString
(JNIEnv *env, jobject obj, jstring str){

	char *str1 = jstringToChar(env,str);
	strcat(str1, " ");
	char* str2;
	cout << "Enter your Name : " << endl;
	//flushall();
	cin >> str2;
	strcat(str1,str2);
	strupr(str1);
	return env->NewStringUTF(str1);

}


//Integer class operation
JNIEXPORT jint JNICALL Java_jniClass_JNIClass_showValue
(JNIEnv *env, jobject obj, jobject value){

	jclass cl = env->GetObjectClass(value);
	jmethodID jmID = env->GetMethodID(cl, "intValue", "()I"); 
	if(jmID == NULL){
		cout << "Method intValue not found" << endl;
		//flushall();
		return -1;
	}
	jint val = env->CallIntMethod(value,jmID);
	if(val == NULL){
		cout << "Calling method Error" << endl;
		//flushall();
		return -1;
	}	
	return val;
}



//Double-Float class operation (Return type Double)
JNIEXPORT jobject JNICALL Java_jniClass_JNIClass_addFloatValues
(JNIEnv *env, jobject classObj, jobject floatVal1, jobject floatVal2){
	
	jclass cl = env->FindClass("java/lang/Double");
	jmethodID jmFloatID = env->GetMethodID(cl, "doubleValue", "()D");
	if(jmFloatID == NULL){
		cout << "Method doubleValue not Found." << endl; 
		//flushall();
		return NULL;
	}
	jdouble val1 = env->CallDoubleMethod(floatVal1,jmFloatID);			//Call method of Double class, not from Float class.
	jdouble val2 = env->CallDoubleMethod(floatVal2,jmFloatID);
	
	jdouble total = val1 + val2;
	
	jclass dClass = env->FindClass("java/lang/Double");
	if(dClass == NULL){
		cout << "Class not found" << endl;
		//flushall();
		return NULL;
	}
	jmethodID jmDID = env->GetMethodID(dClass,"<init>","(D)V");			//Define constructor of Double class. <init> is used for constructor method.
	if(jmDID == NULL){
		cout << "Constructor Double method not found." << endl;
		//flushall();
		return NULL;
	}
	jobject obj = (jobject) env->NewObject(dClass,jmDID, total);
	if(obj == NULL){
		cout << "Data Error" << endl;
		//flushall();
		return NULL;
	}
	return (obj);
}




//jint operation
JNIEXPORT jint JNICALL Java_jniClass_JNIClass_addValue
(JNIEnv *env, jobject obj, jint val1, jint val2){
	cout << "Total of " << val1 << ", " << val2 << " is : ";
	int temp = val1 + val2;
	//flushall();
	return temp;

}


//jfloat array and jboolean operation
JNIEXPORT jfloat JNICALL Java_jniClass_JNIClass_addAllValue
(JNIEnv *env, jobject obj, jfloatArray values, jboolean apply){

	if(apply==1){
		int len = env->GetArrayLength(values);
		jfloat *jvalue = env->GetFloatArrayElements(values,NULL);
		if(jvalue==NULL){
			cout << "Exception occured" << endl;
			return 0;
		}
		jfloat total=0.0;
		for(int i=0; i< len; i++){
			total += jvalue[i];
		}
		env->ReleaseFloatArrayElements(values,jvalue,NULL);
		return total;
	}else{
		cout << "Addition disabled by user, default output: " << endl;
		//flushall();
	}
	return 0;

}


//Access Instance variable (String) in Java programe
JNIEXPORT void JNICALL Java_jniClass_JNIClass_accessInstanceField
	(JNIEnv * env, jobject obj){

	jclass cl = env->GetObjectClass(obj);
	jfieldID jfield = env->GetFieldID(cl,"str","Ljava/lang/String;");
	if(jfield==NULL){
		cout << "str field not found" << endl; return;
	}

	jstring jstr = (jstring)env->GetObjectField(obj,jfield);
	const char* str = env->GetStringUTFChars(jstr, NULL);				//jstring to const char* converter	
	if(str==NULL){
		cout << "Memory Error" << endl; return;
	}
	cout << "In C Code, str : " << str << endl;
	env->ReleaseStringUTFChars(jstr, str);

	//Assign new value in field of java program.
	jstr = env->NewStringUTF("Debabrata");						
	if(jstr==NULL){
		cout << "Memory Error" << endl; return;
	}
	env->SetObjectField(obj,jfield,jstr);
	//flushall();
}


//Access static variable (int) from java program.
JNIEXPORT void JNICALL Java_jniClass_JNIClass_accessStaticField
(JNIEnv *env, jobject obj){

	jclass cl = env->GetObjectClass(obj);
	jfieldID fieldID = env->GetStaticFieldID(cl, "static_val", "I");
	if(fieldID == NULL){
		cout << "Static Variable not found" << endl; return;
	}

	jint jval = env->GetStaticIntField(cl, fieldID);
	cout << endl << "In C, static_val : " << jval << endl;

	//Change the static field value
	env->SetStaticIntField(cl, fieldID, 200);
	//flushall();
}


//Access instance method in java program
JNIEXPORT void JNICALL Java_jniClass_JNIClass_accessInstanceMethod
(JNIEnv *env, jobject obj){

	jclass cl = env->GetObjectClass(obj);
	jmethodID jmID = env->GetMethodID(cl,"instanceMethod", "(I)V");
	if(jmID == NULL){
		cout << "Method instanceMethod not found." << endl;
		//flushall();
		return;
	}
	jint arg = 120;
	env->CallVoidMethod(obj,jmID, arg);
}


//Access static method in java program
JNIEXPORT void JNICALL Java_jniClass_JNIClass_accessStaticMethod
(JNIEnv *env, jobject obj){
	
	jclass cl = env->GetObjectClass(obj);
	jmethodID jmiD = env->GetStaticMethodID(cl,"staticMethod","(F)V");
	if(jmiD == NULL){
		cout << "Method staticMethod not found" << endl;
		//flushall();
		return;
	}
	jfloat arg = 10.5;
	env->CallStaticVoidMethod(cl,jmiD,arg);
}


//Access super class instance method which is override in child class too.
JNIEXPORT void JNICALL Java_jniClass_JNIClass_accessSuperClassInstanceMethod
(JNIEnv *env, jobject obj){

	jclass cl = env->GetObjectClass(obj);		
	jclass supercl = env->GetSuperclass(cl);			//Find and create super class instance using sub class.
	jobject superobj = env->AllocObject(supercl);		//Create jobject using super class instance and it is un-initialized.
	
	jmethodID jinitID = env->GetMethodID(supercl, "<init>", "()V");
	//Create super class jobject and also initilized by calling super class constructor.
	jobject superobj_2 = env->NewObject(supercl, jinitID);	
	
	if(supercl == NULL){
		cout << "SuperClass not found" << endl;
		//flushall();
	}
	if(superobj == NULL){
		cout << "Super Class object creation Error." << endl;
		//flushall();
	}
	jmethodID jmID = env->GetMethodID(cl,"superMethod", "()V");
	jmethodID superjmID = env->GetMethodID(supercl,"superMethod", "()V");
	if(jmID == NULL || superjmID == NULL){
		cout << "Method superMethod not found." << endl;
		//flushall();
		return;
	}
	
	env->CallNonvirtualVoidMethod(obj,cl,jmID);						//Call overrride method in sub class
	env->CallNonvirtualVoidMethod(superobj, supercl, superjmID);	//Call super class method
	env->CallNonvirtualVoidMethod(superobj_2, supercl, superjmID);	//Call super class method in another way

}


//Access & calling sub-class parameterized constructor using create a new object.
JNIEXPORT void JNICALL Java_jniClass_JNIClass_accessConstructor
(JNIEnv *env, jobject obj){
	
	cout << "Constructor Call from C code." << endl;
	//flushall();

	jclass cl = env->GetObjectClass(obj);
	jmethodID jmID = env->GetMethodID(cl, "<init>", "(Ljava/lang/String;)V");		//Hints: Any constructor name: <init>
	if(jmID == NULL){
		cout << "Construtor of sub class not found" << endl;
		//flushall();
		return;
	}
	
	const char * arg = "Debabrata_Hazra";
	jstring str = env->NewStringUTF(arg);		//jstring object create using const char*
	env->NewObject(cl,jmID,str);				//Create a new object of the calling class, and it call parameterized constructor too.
}
