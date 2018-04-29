package com.zj.api.utils

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by zhangjun on 2018/4/29.
 */
abstract class TypeToken<T> {
    private val type: Type

    init {
        val superClass = javaClass.genericSuperclass
        type = (superClass as ParameterizedType).actualTypeArguments[0]
    }

    fun getType() = type
}