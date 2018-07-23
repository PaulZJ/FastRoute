package com.zj.annotation

/**
 * Created by zhangjun on 2018/4/22.
 */

enum class RouteType(val className: String) {
    UNKNOWN(""),
    ACTIVITY("android.app.Activity"),
    SERVICE("android.app.Service"),
    CONTENT_PROVIDER("android.content.ContentProvider"),
    BROADCAST(""),
    FRAGMENT("android.app.Fragment"),
    FRAGMENT_V4("android.support.v4.app.Fragment")
}