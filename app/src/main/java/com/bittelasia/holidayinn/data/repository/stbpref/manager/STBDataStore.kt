package com.bittelasia.holidayinn.data.repository.stbpref.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bittelasia.holidayinn.data.repository.stbpref.data.STB
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

object STBDataStore {

    private val Context.stbDataStore: DataStore<Preferences> by preferencesDataStore(name = "stb")

    private val HOST = stringPreferencesKey("HTTPPreference_HOST")
    private val ROOM = stringPreferencesKey("APIKeyPreference_PREF_ROOM")
    private val PORT = stringPreferencesKey("HTTPPreference_PORT")
    private val FIRST_RUN = stringPreferencesKey("firstrun")
    private val MAC_ADDRESS = stringPreferencesKey("dev_id")
    private val API_KEY = stringPreferencesKey("api_key")
    private val USERNAME = stringPreferencesKey("XMPPPreference_USERNAME")
    private val PASSWORD = stringPreferencesKey("XMPPPreference_PASSWORD")


    suspend fun Context.saveSTB(stb: STB) {
        coroutineScope {
            launch(Dispatchers.IO) {
                stbDataStore.edit { datastore ->
                    stb.apply {
                        datastore[STBDataStore.ROOM] = ROOM
                        datastore[STBDataStore.HOST] = HOST
                        datastore[STBDataStore.PORT] = PORT
                        datastore[STBDataStore.FIRST_RUN] = FIRST_RUN
                        datastore[STBDataStore.MAC_ADDRESS] = MAC_ADDRESS
                        datastore[STBDataStore.API_KEY] = API_KEY
                        datastore[STBDataStore.USERNAME] = USERNAME
                        datastore[STBDataStore.PASSWORD] = PASSWORD
                    }
                }
            }
        }
    }

    private suspend fun Context.readSTB(callback: suspend (Flow<STB>) -> Unit) {
        callback.invoke(stbDataStore.data.map { pref ->
            STB.apply {
                ROOM = pref[STBDataStore.ROOM] ?: ""
                HOST = pref[STBDataStore.HOST] ?: "http://127.0.0.1"
                PORT = pref[STBDataStore.PORT] ?: "8080"
                FIRST_RUN = pref[STBDataStore.FIRST_RUN] ?: ""
                MAC_ADDRESS = pref[STBDataStore.MAC_ADDRESS] ?: ""
                API_KEY = pref[STBDataStore.API_KEY] ?: ""
                USERNAME = pref[STBDataStore.USERNAME] ?: ""
                PASSWORD = pref[STBDataStore.PASSWORD] ?: ""
            }
        })
    }

    fun Context.readSTB(dispatcher: CoroutineDispatcher, callback: suspend (STB) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            readSTB {
                it.collectLatest { stb ->
                    launch(dispatcher) {
                        callback.invoke(stb)
                    }
                }
            }
        }
    }
}