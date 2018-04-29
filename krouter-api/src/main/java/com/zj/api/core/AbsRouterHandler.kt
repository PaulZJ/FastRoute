package com.zj.api.core

import android.content.Context
import com.zj.api.exceptions.HandleException
import com.zj.krouter_annotation.model.RouteMetadata

/**
 * Created by zhangjun on 2018/4/29.
 */
internal abstract class AbsRouteHandler(val routeMetadata: RouteMetadata) {
    @Throws(HandleException::class)
    abstract fun handle(context: Context, navigator: KRouter.Navigator): Any?
}