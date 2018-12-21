#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_utbm_fr_jnif1levier_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring

JNICALL
Java_utbm_fr_jnif1levier_MainActivity_jnif1Read(
        JNIEnv *env,
        jobject obj,
        jint a) {
    int nb = a*a;
    std::string read = "READ : "+std::to_string(nb);
    return env->NewStringUTF(read.c_str());
}

extern "C" JNIEXPORT jstring

JNICALL
Java_utbm_fr_jnif1levier_MainActivity_jnif1Write(
        JNIEnv *env,
        jobject obj,
        jint a) {
    int nb = a*a*a;
    std::string read = "READ : "+std::to_string(nb);
    return env->NewStringUTF(read.c_str());
}

extern "C" JNIEXPORT jstring

JNICALL
Java_utbm_fr_jnif1levier_MainActivity_jnif1Direction(
        JNIEnv *env,
        jobject obj,
        jstring s) {
    const char * cinput = env->GetStringUTFChars(s, NULL);
    std::string input = std::string(cinput);
    std::string translate;
    if(input.compare("UP") == 0) translate = "Oben";
    if(input.compare("DOWN") == 0) translate = "Unten";
    if(input.compare("LEFT") == 0) translate = "Links";
    if(input.compare("RIGHT") == 0) translate = "Rechts";
    return env->NewStringUTF(translate.c_str());
}