package com.zj.api.exceptions

/**
 * Created by zhangjun on 2018/4/29.
 */

open class HandleException: RuntimeException {
    constructor(msg: String): super(msg)

    constructor(throwable: Throwable): super(throwable)
}

/** Runtime Exception for Route Error */
class RouteNotFoundException: HandleException {
    constructor(msg: String): super(msg)

    constructor(throwable: Throwable): super(throwable)
}