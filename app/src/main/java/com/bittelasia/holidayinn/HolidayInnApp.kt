package com.bittelasia.holidayinn

import android.app.Application
import android.content.Intent
import dagger.hilt.android.HiltAndroidApp
import kotlin.system.exitProcess

@HiltAndroidApp
class HolidayInnApp: Application(){
    override fun onCreate() {
        super.onCreate()
//        Thread.setDefaultUncaughtExceptionHandler { _, _ ->
//            restartApp()
//        }
    }
    private fun restartApp() {
        val intent = baseContext.packageManager
            .getLaunchIntentForPackage(baseContext.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        exitProcess(2)
    }
}