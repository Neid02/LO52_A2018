#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_lo52_jniapp_MainActivity_translate(JNIEnv *env, jobject /* this */, jstring direction_) {
    std::string direction = env->GetStringUTFChars(direction_, 0);
    std::string result;

    if (direction == "up"){
        result = "Oben";
    }
    else if (direction == "down"){
        result = "Niedrig";
    }
    else if (direction == "right"){
        result = "Recht";
    }
    else if (direction == "left"){
        result = "Links";
    }

    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_lo52_jniapp_MainActivity_read(JNIEnv *env, jobject /* this */, jint nombre) {

    int nb = nombre * nombre;
    std::string result = "READ : " + std::to_string(nb);

    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_lo52_jniapp_MainActivity_write(JNIEnv *env, jobject /* this */, jint nombre) {

    int nb = nombre * nombre * nombre;
    std::string result = "WRITE : " + std::to_string(nb);

    return env->NewStringUTF(result.c_str());
}