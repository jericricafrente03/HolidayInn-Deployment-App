package com.bittelasia.holidayinn.data.repository.stbpref.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.bittelasia.holidayinn.data.repository.stbpref.data.License
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object LicenseDataStore {
    private val Context.licenseDataStore: DataStore<Preferences> by preferencesDataStore(name = "license")
    private val STATUS = stringPreferencesKey("STATUS")
    private val END_DATE = stringPreferencesKey("END_DATE")
    private val REMAINING_DAYS = stringPreferencesKey("REMAINING_DAYS")

    suspend fun Context.saveLicense(license: License) {
        coroutineScope {
            launch {
                licenseDataStore.edit { datastore ->
                    license.apply {
                        datastore[LicenseDataStore.STATUS] = STATUS
                        datastore[LicenseDataStore.END_DATE] = END_DATE
                        datastore[LicenseDataStore.REMAINING_DAYS] = REMAINING_DAYS
                    }
                }
            }
        }
    }
    private suspend fun Context.readLicense(callback: suspend (Flow<License>) -> Unit) {
        callback.invoke(licenseDataStore.data.map { pref ->
            License.apply {
                STATUS = pref[LicenseDataStore.STATUS]?: ""
                END_DATE = pref[LicenseDataStore.END_DATE]?:""
                REMAINING_DAYS = pref[LicenseDataStore.REMAINING_DAYS]?:""
            }
        })
    }
    fun Context.readLicense(dispatcher: CoroutineDispatcher, callback: suspend (License) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            readLicense {
                it.collect { license ->
                    launch(dispatcher) {
                        callback.invoke(license)
                    }
                }
            }
        }
    }
}