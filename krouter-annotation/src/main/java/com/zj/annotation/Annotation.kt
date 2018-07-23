package com.zj.annotation

/**
 * Created by zhangjun on 2018/4/22.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Route(
        val path: String,
        val pathPrefix: String = "",
        val pathPattern: String = "",
        val name: String = "",
        val priority: Int = -1
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Interceptor(
        val priority: Int = -1,
        val name: String = "DefaultInterceptorName"
)

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
annotation class Inject(
        val name: String = "",
        val isRequired: Boolean = false,
        val desc:String = "No desc"
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Provider(
        val value: String
)

