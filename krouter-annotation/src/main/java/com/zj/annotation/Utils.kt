package com.zj.annotation

/**
 * Created by zhangjun on 2018/4/26.
 */

const val PACKAGE = "com.zj"
const val SEPARATOR = "_"
const val PROJECT_NAME = "KRouter"
const val ROUTE_LOADER_NAME = "$PROJECT_NAME${SEPARATOR}RouteLoader"
const val INTERCEPTOR_LOADER_NAME = "$PROJECT_NAME${SEPARATOR}InterceporLoader"
const val PROVIDER_LOADER_NAME = "$PROJECT_NAME${SEPARATOR}ProviderLoader"
const val INJECTOR_NAME = "$PROJECT_NAME${SEPARATOR}Injector"
const val MODULE_NAME = "moduleName"
internal const val INJECTOR_SUFFIX = "$SEPARATOR$PROJECT_NAME${SEPARATOR}Injector"

fun transferModuleName(fileName: String): String {
    return fileName.substring(fileName.lastIndexOf(SEPARATOR) + 1, fileName.length)
            .replace("[^0-9a-zA-Z]+".toRegex(), "")
}

fun getInjectorClass(instance: Any): String {
    return "${instance::class.java.`package`.name}.${instance::class.java.name.replace(".", SEPARATOR)}$INJECTOR_SUFFIX"
}

fun transferInjectorClassName(className: String): String {
    return "${className.replace(".", SEPARATOR)}$INJECTOR_SUFFIX"
}