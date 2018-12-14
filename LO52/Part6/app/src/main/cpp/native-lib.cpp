#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_example_root_part6app_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_root_part6app_MainActivity_textAAfficheReadFromJNI(JNIEnv *env, jobject instance,
                                                                     jdouble nombre) {
    double a = nombre *nombre;
    std::string result = std::to_string(a);
    return env->NewStringUTF(result.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_root_part6app_MainActivity_textAAfficheWriteFromJNI(JNIEnv *env, jobject instance,
                                                                     jdouble nombre) {

    double a = nombre *nombre*nombre;
    std::string result = std::to_string(a);
    return env->NewStringUTF(result.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_root_part6app_MainActivity_textDeLaDirectionFromJNI(JNIEnv *env, jobject instance,
                                                                     jstring nom_) {
    std::string nom = env->GetStringUTFChars(nom_, 0);
//    env->ReleaseStringUTFChars(nom_, nom);
    std::string  result;
    if(nom ==  "RIGHT"){
        std::string  result = "Recht";
        return env->NewStringUTF(result.c_str());
    }
    else if(nom ==  "LEFT"){
        std::string  result = "Links";
        return env->NewStringUTF(result.c_str());
    }
   else  if(nom ==  "DOWN"){
        std::string  result = "Niedring";
        return env->NewStringUTF(result.c_str());
    }
   else  if(nom ==  "UP"){
        std::string  result = "TOP";
        return env->NewStringUTF(result.c_str());
    }else{
        std::string  result = "No match found ";
        return env->NewStringUTF(result.c_str());
    }



}