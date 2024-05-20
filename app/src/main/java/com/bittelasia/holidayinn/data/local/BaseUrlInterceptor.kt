package com.bittelasia.holidayinn.data.local

import android.content.Context
import com.bittelasia.holidayinn.data.repository.stbpref.data.STB
import com.bittelasia.holidayinn.data.repository.stbpref.manager.STBDataStore.readSTB
import kotlinx.coroutines.Dispatchers
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

class BaseUrlInterceptor(context: Context) : Interceptor {

    companion object {
        private var newBaseUrl = STB.HOST+":"+ STB.PORT+"/"

        fun setBaseUrl(baseUrl: String) {
            newBaseUrl = baseUrl
        }

    }
    init {
        context.readSTB(Dispatchers.Main){
            newBaseUrl = it.HOST+":"+it.PORT+"/"
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .scheme(newBaseUrl.toHttpUrlOrNull()!!.scheme)
            .host(newBaseUrl.toHttpUrlOrNull()!!.host)
            .port(newBaseUrl.toHttpUrlOrNull()!!.port)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}