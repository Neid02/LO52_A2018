#include <jni.h>
#include <string>


// Convert jstring to std::string
// Source : https://stackoverflow.com/questions/41820039/jstringjni-to-stdstringc-with-utf8-characters/41820336
std::string jstring2string(JNIEnv *env, jstring jStr) {
    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

    size_t length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte* pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    std::string ret = std::string((char *)pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}

// JNI functions exports

extern "C" JNIEXPORT jstring

JNICALL
Java_adury_1csanchez_utbm_appjni_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C" JNIEXPORT jint

JNICALL
Java_adury_1csanchez_utbm_appjni_MainActivity_read(
        JNIEnv *env,
        jobject /* this */,
        jint a) {
    return a*a;
}


extern "C" JNIEXPORT jint

JNICALL
Java_adury_1csanchez_utbm_appjni_MainActivity_write(
        JNIEnv *env,
        jobject /* this */,
        jint a) {
    return a*a*a;
}


extern "C" JNIEXPORT jstring

JNICALL
Java_adury_1csanchez_utbm_appjni_MainActivity_translate(
        JNIEnv *env,
        jobject /* this */,
        jstring direction) {
    std::string input=jstring2string(env,direction);

    std::string out="";
    if(input=="up")
        out="oben";
    else if(input=="left")
        out="links";
    else if(input=="right")
        out="recht";
    else if(input=="down")
        out="nieder";

    return  env->NewStringUTF(out.c_str());
}


