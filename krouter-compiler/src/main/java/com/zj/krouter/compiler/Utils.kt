package com.zj.krouter.compiler

import com.zj.krouter_annotation.PACKAGE

/**
 * Created by zhangjun on 2018/4/26.
 */

const val ROUTE_LOADER = "$PACKAGE.api.interfaces.IrouteLoader"
const val INTERCEPOR_LOADER = "$PACKAGE.api.interfaces.IInterceptorLoader"
const val PROVIDER_LOADER = "$PACKAGE.api.interfaces.IProviderLoader"
const val INJECTOR = "$PACKAGE.api.interfaces.IInjector"
const val INTERCEPTOR = "$PACKAGE.api.interfaces.IRouteInterceptor"
const val PROVIDER = "$PACKAGE.api.interfaces.IProvider"
const val KROUTER = "$PACKAGE.api.core.KRouter"
const val SERIALIZE_PATH = "$PACKAGE.api.utils.SERIALIZE_PATH"
const val SERIALIZE_PROVIDER = "$PACKAGE.api.interfaces.SerializationProvider"
const val TYPE_TOKEN = "$PACKAGE.api.utils.TypeToken"

const val BUNDLE = "android.os.Bundle"

const val LANG = "java.lang"
const val TYPE = "$LANG.reflect.Type"
const val NP_EXCEPTION = "$LANG.NullPointerException"
const val ILLEGAL_ARGUMENT_EXCEPTION = "$LANG.IllegalArgumentException"

const val METADATA = "kotlin.Metadata"
const val NONULL = "android.support.annotation.NonNull"

const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"

val WARNINGS = """   ***************************************************
                  |   * THIS CODE IS GENERATED BY KRouter, DO NOT EDIT. *
                  |   ***************************************************
                     |""".trimMargin()

const val METHOD_LOAD = "loadInfo"
const val METHOD_EX_INJECT = "exInject"
const val METHOD_INJECT = "inject"
const val METHOD_GET_BUNDLE = "getBundle"
const val METHOD_PARSE_OBJECT = "parseObject"