#include <jni.h>
#include <string>


/**
 * Function getReadString
 *
 * With an integer param <a>
 * Will return a string "READ a*a"
 */
extern "C"
JNIEXPORT jstring
JNICALL
Java_com_rebels_f1levierjni_MainActivity_getReadString(
        JNIEnv *env,
        jobject /* this */,
        jint a) {
    std::string msg = "READ : " + std::to_string(a*a);
    return env->NewStringUTF(msg.c_str());
}


/**
 * Function getReadString
 *
 * With an integer param <a>
 * Will return a string "WRITE a*a*a"
 */
extern "C"
JNIEXPORT jstring
JNICALL
Java_com_rebels_f1levierjni_MainActivity_getWriteString(
        JNIEnv *env,
        jobject /* this */,
        jint a) {
    std::string msg = "WRITE : " + std::to_string(a*a*a);
    return env->NewStringUTF(msg.c_str());
}


/**
 * Function getGermanTranslatedText
 *
 * With a string param <text> (must be in lowercase)
 * Will return a string which will be the german translation of <entry>
 */
extern "C"
JNIEXPORT jstring
JNICALL
Java_com_rebels_f1levierjni_MainActivity_getGermanTranslatedText(
        JNIEnv *env,
        jobject /* this */,
        jstring entry) {
    std::string msg = "";
    const char* ch = env->GetStringUTFChars(entry, 0);
    if (std::strcmp(ch, "up") == 0) msg = "oben";
    else if (std::strcmp(ch, "down") == 0) msg = "nieder";
    else if (std::strcmp(ch, "left") == 0) msg = "links";
    else if (std::strcmp(ch, "right") == 0) msg = "recht";
    env->ReleaseStringUTFChars(entry, ch);
    return env->NewStringUTF(msg.c_str());
}
