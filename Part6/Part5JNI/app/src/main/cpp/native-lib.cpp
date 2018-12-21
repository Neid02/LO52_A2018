#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_csanchez_part5jni_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C" JNIEXPORT jint

JNICALL
Java_com_example_csanchez_part5jni_MainActivity_read(
        JNIEnv *env,
        jobject /* this */,
jint a) {
    return a*a;
}


extern "C" JNIEXPORT jint

JNICALL
Java_com_example_csanchez_part5jni_MainActivity_write(
        JNIEnv *env,
        jobject /* this */,
        jint a) {
    return a*a*a;
}


extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_csanchez_part5jni_MainActivity_up(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "oben";
    return env->NewStringUTF(hello.c_str());
}
