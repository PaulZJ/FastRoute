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
interface IRouteLoader {
    fun loadInto(map: MutableMap<String, RouteMetadata>)
}

interface IInterceptorLoader {
    fun loadInto(map: TreeMap<Int, InterceptorMetaData>)
}

interface IProviderLoader {
    fun loadInto(map: MutableMap<String, Class<*>>)
}

interface IRouteInterceptor {
    fun intercept(context: Context, path: String, extras: Bundle): Boolean
}

interface IProvider {
    fun init(context: Context)
}

interface PathMatcher {
    fun match(path: String, path2: String): Boolean
}

interface IInjector {
    fun inject(any: Any, extras: Bundle?)
}

interface SerializationProvider: IProvider {
    fun <T> parseObject(text: String?, clazz: Type): T?

    fun serialize(intance: Any): String
}