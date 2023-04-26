#include "api_keys.h"
#include <jni.h>
#include <string>
#include <iostream>
using namespace std;

extern "C" JNIEXPORT jstring
Java_com_example_allvideodownloaderrevolt_common_Constant_getEncryptionKey(
        JNIEnv *env,
        jclass clazz) {
    std::string api_key = "n2ibr223ael5iceed7136aebr4sah54aack4l2o0v7e";
    return env->NewStringUTF(api_key.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_allvideodownloaderrevolt_common_Constant_getMoreAppApi(
        JNIEnv *env, jclass clazz) {
    std::string api_key = "POWex0vUDJB9rKU/7WguavYMNefzTAQDruX5UjcvxgY=]IgcsF9PjdU6pLJJwa3viEw==]423d6lnadn7lLQOO79glV1Kn+YZX+w3UnK7TlKtL+63KPohNyLwGZ+gklwBAuc1yUEdoqvdmB/czcN7ebrYN46cJ1ciRRnUfXI7lEsWx86k=";
    return env->NewStringUTF(api_key.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_allvideodownloaderrevolt_common_Constant_getLoginApi(
        JNIEnv *env, jclass clazz) {
    std::string api_key = "MQDFEFXGSuElGMtnq6J/jnAfGfTj9lUSeSPCH5S/NZQ=]4aiklAlv1RrYxho807Kk6Q==]vy57RMDv0f2eF3G+LrFGnP9AeyKDiIHcuxdlofl2vp8DSb4g/kPA6FnNNPLP9W7PQPP12M918ya5ude3UK0F6g==";
    return env->NewStringUTF(api_key.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_allvideodownloaderrevolt_common_Constant_getHeader(
        JNIEnv *env, jclass clazz) {
    std::string api_key = "2b223e5cee713615ha54ac203b24e9a123703mk122";
    return env->NewStringUTF(api_key.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_allvideodownloaderrevolt_common_Constant_getCallEnd(
        JNIEnv *env, jclass clazz) {
    std::string api_key = "FBDRd4dRUxf7iZcST89o/OY9tLTfnxqqM7hfzyZ3V34=]Fcvvau5ke8EN5YH4/HC9Ng==]H1GfHNoE3bDoh0VsJapKdvwJUiWYSdv2s6jYouHKwYhGceCY/WH3Ih4qqPzD4KeCSzvZXrikJpmNZlQlWJ26LQ==";
    return env->NewStringUTF(api_key.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_allvideodownloaderrevolt_common_Constant_getTwitterApi(
        JNIEnv *env, jclass clazz) {
    std::string api_key = "qsdYVCBtcijH5My9aSdyJ6zizZrDEmku2wErQwN69NU=]CPrUund2ZshuXYqirWqzig==]lnZ4QaU4H8CGemBh0prpUHMqrlTKZQWQEdnL+MZX9oNQ4WOifBA/2VVnAa+WyokY2Tucq48rvQzd78VhUPR8i2d5r+7ZvevoHsS95DhB3SU=";
    return env->NewStringUTF(api_key.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_allvideodownloaderrevolt_common_Constant_getToken(
        JNIEnv *env, jclass clazz) {
//    std::string api_key = "u2dLp2sJg2oD8UehFCiUtypQon9sfE30";
    std::string api_key = "dfsdfsdfdsfdfsdfsdfsdfdfdfsdf";
    return env->NewStringUTF(api_key.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_allvideodownloaderrevolt_common_Constant_getUrl(JNIEnv *env, jobject thiz) {
    // TODO: implement getUrl()
}