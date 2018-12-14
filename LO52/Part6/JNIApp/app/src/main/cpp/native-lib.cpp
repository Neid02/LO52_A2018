#include <jni.h>
#include <string>
#include <iostream>
#include <stdio.h>

//extern "C" JNIEXPORT jstring JNICALL
//Java_fr_lo52_agkc_jniapp_MainActivity_stringFromJNI(
//        JNIEnv *env,
//        jobject /* this */) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}

extern "C"
JNIEXPORT jstring JNICALL
Java_fr_lo52_agkc_jniapp_MainActivity_translate(JNIEnv *env, jobject /* this */, jstring nom_) {
    const char *nom = env->GetStringUTFChars(nom_, 0);

    std::string levier = nom;
    if (levier == "up"){
        levier = "aufstehen";
    }
    if (levier == "down"){
        levier = "daunter";
    }
    if (levier == "right"){
        levier = "richting";
    }
    if (levier == "left"){
        levier = "links";
    }

    env->ReleaseStringUTFChars(nom_, nom);

    return env->NewStringUTF(levier.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_fr_lo52_agkc_jniapp_MainActivity_lecture(JNIEnv *env, jobject /* this */, jint nombre) {

    int nb = nombre;
    nb = nb * nb;
    std::string levier = "";
    levier = "READ : "+std::to_string(nb);

    return env->NewStringUTF(levier.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_fr_lo52_agkc_jniapp_MainActivity_ecriture(JNIEnv *env, jobject /* this */, jint nombre) {

    int nb = nombre;
    nb = nb * nb * nb;
    std::string levier = "";
    levier = "READ : "+std::to_string(nb);

    return env->NewStringUTF(levier.c_str());
}