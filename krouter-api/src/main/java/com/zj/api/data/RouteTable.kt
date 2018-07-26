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
    /** route table */
    internal val routes = HashMap<String, RouteMetadata>()
    /** Provider Table for App Services */
    internal val providers = HashMap<String, Class<*>>()
    /** Injectors Table for each route target */
    internal val injectors = HashMap<String, List<InjectorMetaData>>()
    /** Interceptors Table for Navigators */
    internal val interceptors = TreeMap<Int, InterceptorMetaData>()
    /** Matcher Pattern Table */
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