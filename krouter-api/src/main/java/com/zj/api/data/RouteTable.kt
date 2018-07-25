package com.zj.api.data

import com.zj.api.interfaces.PathMatcher
import com.zj.annotation.model.InjectorMetaData
import com.zj.annotation.model.InterceptorMetaData
import com.zj.annotation.model.RouteMetadata
import java.util.*

/**
 * Created by zhangjun on 2018/4/29.
 */

/**
 * Data Holder for Route Internal
 */
internal object RouteTable {
    internal val routes = HashMap<String, RouteMetadata>()
    internal val providers = HashMap<String, Class<*>>()
    internal val injectors = HashMap<String, List<InjectorMetaData>>()
    internal val interceptors = TreeMap<Int, InterceptorMetaData>()
    internal val matchers = mutableListOf<PathMatcher>(DefaultMatcher)

    fun clear() {
        routes.clear()
        providers.clear()
        injectors.clear()
        interceptors.clear()
        matchers.clear()
    }
}

object DefaultMatcher: PathMatcher {
    override fun match(path: String, path2: String): Boolean {
        return path == path2
    }

}