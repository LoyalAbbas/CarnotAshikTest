package com.app.ashikcarnottest.utils

import android.util.Log
import com.app.ashikcarnottest.BuildConfig

class MyLogger {

    val ERROR = "Error"

    companion object {
        /**
         * Flag to show logs in debugging mode
         */

        private val DEBUG: Boolean = BuildConfig.DEBUG

        fun i(tag: String?, msg: String?) {
            if (DEBUG) if (msg != null) Log.i(tag, msg)
        }

        @JvmStatic
        fun e(tag: String?, msg: String?) {
            if (DEBUG) if (msg != null) Log.e(tag, msg)
        }

        fun d(tag: String?, msg: String?) {
            if (DEBUG) if (msg != null) Log.d(tag, msg)
        }

        fun w(tag: String?, msg: String?) {
            if (DEBUG) if (msg != null) Log.w(tag, msg)
        }

     }
}