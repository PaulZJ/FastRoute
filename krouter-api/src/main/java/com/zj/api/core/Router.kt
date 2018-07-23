package com.zj.api.core

import android.app.Fragment
import android.content.Context
import com.zj.api.data.RouteTable
import com.zj.api.exceptions.HandleException
import com.zj.api.interfaces.*
import com.zj.api.utils.Logger
import com.zj.annotation.*
import com.zj.annotation.model.RouteMetadata
import java.util.*

/**
 * Created by zhangjun on 2018/4/29.
 */

internal class Router private constructor() {

    internal lateinit var context: Context
    private val PACKAGE = "com.zj.krouter"
    internal object Inner {
        val instance = Router()
    }

    companion object {
        fun getInstance() = Inner.instance

        fun init(context: Context) {
            getInstance().context = context.applicationContext
            getInstance().loadRouteTable()
        }

    }

    private fun loadRouteTable() {
        context.assets.list("").filter { it.startsWith("$PROJECT_NAME$SEPARATOR") }.forEach {
            val  moduleName = transferModuleName(it)
            if(moduleName.isBlank()) {
                return@forEach
            }

            (loadClassForName("$PACKAGE$INTERCEPTOR_LOADER_NAME$SEPARATOR$moduleName")?.newInstance() as? IInterceptorLoader)?.loadInto(RouteTable.interceptors)
            (loadClassForName("$PACKAGE$PROVIDER_LOADER_NAME$SEPARATOR$moduleName")?.newInstance() as? IProviderLoader)?.loadInto(RouteTable.providers)
            (loadClassForName("$PACKAGE$ROUTE_LOADER_NAME$SEPARATOR$moduleName")?.newInstance() as? IRouteLoader)?.loadInto(RouteTable.routes)
        }
    }

    private fun loadClassForName(className: String): Class<*>? {
        return try {
            Class.forName(className)
        }catch (e: ClassNotFoundException) {
            null
        }
    }

    fun route(path: String): Any? {
        val clazz = RouteTable.providers[path]?: return null
        val instance = clazz.newInstance()
        if(instance is IProvider) {
            instance.init(context)
        }
        return instance
    }

    fun route(navigator: KRouter.Navigator): Any? {
        val map = addressingComponent(navigator)
        if (map.isEmpty()) {
            Logger.w("${navigator.path} Not Found!")
            navigator.routeFailedCallback?.invoke(navigator, "")
            return null
        }

        val handlers = createRouteHandler(map)
        Logger.i("Found ${handlers.size} target for ${navigator.path}")
        val isIntercept = isIntercept(navigator)
        handlers.forEach {
            navigator.beforeRouteCallback?.invoke(navigator, it.routeMetadata.clazz.name)
            try {
                if (isIntercept) {
                    navigator.routeFailedCallback?.invoke(navigator, it.routeMetadata.clazz.name)
                }else {
                    Logger.d("Start handler")
                    val result = it.handle(context, navigator)
                    navigator.routeArrivedCallback?.invoke(navigator, it.routeMetadata.clazz.name)
                    if (result is Fragment || result is android.support.v4.app.Fragment) {
                        return result
                    }
                }
            }catch (e: HandleException) {
                e.printStackTrace()
                navigator.routeFailedCallback?.invoke(navigator, it.routeMetadata.clazz.name)
            }
        }

        return null
    }

    private fun addressingComponent(navigator: KRouter.Navigator): Map<String, RouteMetadata> {
        Logger.d("Addressing >> ${navigator.path}")
        return RouteTable.routes.filterKeys {
            RouteTable.matchers.find { macher ->
                macher.match(it, navigator.path)
            } != null
        }
    }

    private fun createRouteHandler(map: Map<String, RouteMetadata>): List<AbsRouteHandler> {
        return map.map { createHandler(it.value) }.sortedWith(RoutePriorityComparator)
    }

    private fun isIntercept(navigator: KRouter.Navigator): Boolean {
        return RouteTable.interceptors.asSequence().find {
            try {
                val cls = it.value.clazz
                val interceptor = cls.newInstance() as IRouteInterceptor
                Logger.i("Before intercept!")
                return@find interceptor.intercept(context, navigator.path, navigator.extras)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: ClassCastException) {
                Logger.e("${it.value.clazz.name} is not impl IRouteInterceptor")
            }
            return@find false
        } != null
    }

    object RoutePriorityComparator : Comparator<AbsRouteHandler> {
        override fun compare(o1: AbsRouteHandler, o2: AbsRouteHandler): Int = o1.routeMetadata.priority - o2.routeMetadata.priority
    }
}