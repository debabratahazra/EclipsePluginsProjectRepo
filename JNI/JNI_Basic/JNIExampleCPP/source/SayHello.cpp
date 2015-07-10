/*
 * SayHello.c
 *
 *  Created on: Jun 12, 2015
 *      Author: DEBABRATA
 */

#include <iostream>
#include <string.h>
#include "..\header\com_jni_samplecode_JNIMain.h"

//class for: Convert jstring to char*
class ConvertStringHelper {
public:
	ConvertStringHelper(JNIEnv *env, jstring value) {
		m_str = env->GetStringUTFChars(value, 0);
		m_value = &value;
		m_env = env;
	}
	~ConvertStringHelper() {
		m_env->ReleaseStringUTFChars(*m_value, m_str);
	}

	jstring* m_value;
	const char *m_str;
	JNIEnv *m_env;
};

JNIEXPORT jint JNICALL Java_com_jni_samplecode_JNIMain_sayHello(JNIEnv *env,
		jobject obj, jstring str) {
	printf("This is from CPP Codeline.\n");
	printf("Hello : ");
	// Convert jtring to char*
	ConvertStringHelper helper(env, str);
	const char* nativeStr = helper.m_str;

	printf("%s", nativeStr);
	return 0;
}
