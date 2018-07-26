package com.zj.api.interfaces

import android.content.Context
import android.os.Bundle
import com.zj.annotation.model.InterceptorMetaData
import com.zj.annotation.model.RouteMetadata
import java.lang.reflect.Type
import java.util.*

/**
 * Created by zhangjun on 2018/4/29.
 */

/** Loader for Route Table */
interface IRouteLoader {
    fun loadInto(map: MutableMap<String, RouteMetadata>)
}

/** Loader for Interceptor Table */
interface IInterceptorLoader {
    fun loadInto(map: TreeMap<Int, InterceptorMetaData>)
}

/** Loader for Provider Table */
interface IProviderLoader {
    fun loadInto(map: MutableMap<String, Class<*>>)
}

/** Interceptor for Route Request */
interface IRouteInterceptor {
    fun intercept(context: Context, path: String, extras: Bundle): Boolean
}

/** Provider for App Services */
interface IProvider {
    fun init(context: Context)
}

/** Matcher Pattern */
interface PathMatcher {
    fun match(path: String, path2: String): Boolean
}

/** Injector for Route Params */
interface IInjector {
    fun inject(any: Any, extras: Bundle?)
}

/** for Serialization Provider */
interface SerializationProvider: IProvider {
    fun <T> parseObject(text: String?, clazz: Type): T?

    fun serialize(intance: Any): String
}