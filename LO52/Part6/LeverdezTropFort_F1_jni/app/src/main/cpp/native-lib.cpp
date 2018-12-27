#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_jni_lo52_utbm_part6_FirstActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
