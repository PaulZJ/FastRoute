package com.zj.annotation

/**
 * Created by zhangjun on 2018/4/22.
 */

enum class RouteType(val className: String) {
    /** default type */
    UNKNOWN(""),
    /** route type for Activity */
    ACTIVITY("android.app.Activity"),
    /** route type for Service */
    SERVICE("android.app.Service"),
    /** route type for ContentProvider */
    CONTENT_PROVIDER("android.content.ContentProvider"),
    /** route type for BroadcastReceiver */
    BROADCAST(""),
    /** route type for Fragment */
    FRAGMENT("android.app.Fragment"),
    /** route type for Fragment in support v4 */
    FRAGMENT_V4("android.support.v4.app.Fragment")
}