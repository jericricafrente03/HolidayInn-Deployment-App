package com.bittelasia.holidayinn.data.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bittelasia.holidayinn.data.local.BaseUrlInterceptor
import com.bittelasia.holidayinn.data.repository.stbpref.manager.STBDataStore.readSTB
import com.bittelasia.holidayinn.util.ADB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED){
            CoroutineScope(Dispatchers.IO).launch {
                launch {
                    ADB.exec("svc wifi disable")
                }
                context?.readSTB(Dispatchers.IO){
                    BaseUrlInterceptor.setBaseUrl(it.HOST+":"+it.PORT)
                }
            }
        }
    }
}