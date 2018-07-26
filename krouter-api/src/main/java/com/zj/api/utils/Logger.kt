package com.zj.api.utils

import android.util.Log

/**
 * Created by zhangjun on 2018/4/29.
 */
@Deprecated(message = "to be replaced soon")
class Logger {
    companion object {
        private val PREFIX = "[KRouter]::"
        private var isDebug = false

        fun openDebug() {
            isDebug = true
        }

        private fun String.wrap(): String {
            return ">>>   " + this + "   <<<"
        }

        fun d(msg: String, throwable: Throwable? = null) {
            if (isDebug) {
                Log.d(PREFIX, msg.wrap(), throwable)
            }
        }

        fun i(msg: String, throwable: Throwable? = null) {
            if (isDebug) {
                Log.i(PREFIX, msg.wrap(), throwable)
            }
        }

        fun w(msg: String, throwable: Throwable? = null) {
            Log.w(PREFIX, msg.wrap(), throwable)
        }

        fun wtf(msg: String, throwable: Throwable? = null) {
            Log.wtf(PREFIX, msg.wrap(), throwable)
        }

        fun e(msg: String, throwable: Throwable? = null) {
            Log.e(PREFIX, msg.wrap(), throwable)
        }
    }
}