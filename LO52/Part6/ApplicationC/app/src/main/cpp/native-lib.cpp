#include <jni.h>
#include <string>

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

extern "C" JNIEXPORT jstring JNICALL
Java_com_silentpangolin_applicationc_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_silentpangolin_applicationc_MainActivity_translate(
        JNIEnv *env,
        jobject j,
        jstring direction
        ){
    std::string input=jstring2string(env,direction);

    std::string out="";
    if(input=="UP")
        out="OBEN";
    else if(input=="LEFT")
        out="LINKS";
    else if(input=="RIGHT")
        out="RECHT";
    else if(input=="DOWN")
        out="NIEDER";

    return  env->NewStringUTF(out.c_str());
}

extern "C" JNIEXPORT jint JNICALL
Java_com_silentpangolin_applicationc_MainActivity_read(
        JNIEnv *env,
        jobject /* this */,
        jint a) {
    return a*a;
}


extern "C" JNIEXPORT jint JNICALL
Java_com_silentpangolin_applicationc_MainActivity_write(
        JNIEnv *env,
        jobject /* this */,
        jint a) {
    return a*a*a;
}