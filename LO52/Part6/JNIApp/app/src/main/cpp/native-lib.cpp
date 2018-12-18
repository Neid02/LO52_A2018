#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_lo52_jniapp_MainActivity_translate(JNIEnv *env, jobject /* this */, jstring nom_) {
    std::string nom = env->GetStringUTFChars(nom_, 0);
    std::string result;

    if (nom == "up"){
        result = "aufstehen";
    }
    else if (nom == "down"){
        result = "daunter";
    }
    else if (nom == "right"){
        result = "richting";
    }
    else if (nom == "left"){
        result = "links";
    }

    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_lo52_jniapp_MainActivity_read(JNIEnv *env, jobject /* this */, jint nombre) {

    int nb = nombre * nombre;
    std::string result = std::to_string(nb);

    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_lo52_jniapp_MainActivity_write(JNIEnv *env, jobject /* this */, jint nombre) {

    int nb = nombre * nombre * nombre;
    std::string result = std::to_string(nb);

    return env->NewStringUTF(result.c_str());
}