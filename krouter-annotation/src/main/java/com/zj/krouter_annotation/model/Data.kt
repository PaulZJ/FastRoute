package com.zj.krouter_annotation.model

import com.zj.krouter_annotation.RouteType

/**
 * Created by zhangjun on 2018/4/22.
 */

data class RouteMetadata(
        val routeType: RouteType = RouteType.UNKNOWN,
        val priority: Int = -1,
        val name: String = "not defined",
        val path: String = "",
        val pathPrefix: String = "",
        val pathPattern: String = "",
        val clazz: Class<*> = Any::class.java
)

data class InterceptorMetaData(
        val priority: Int = -1,
        val name: String = "not defined",
        val clazz: Class<*> = Any::class.java
)

data class InjectorMetaData(
        val isRequired: Boolean = false,
        val key: String = "",
        val fileName: String = ""
)