package com.bittelasia.holidayinn.util

import android.util.Log

object Debug {
    fun log(tag: Any, content: String) {
        val name = tag::class.java.simpleName
        Log.i("MESH-$name", content)
    }
    fun errorLog(tag: Any, content: String) {
        val name = tag::class.java.simpleName
        Log.e("MESH-$name", content)
    }
    fun logv(tag: Any, content: String) {
        val name = tag::class.java.simpleName
        Log.v("MESH-LIB-$name", content)
    }
}