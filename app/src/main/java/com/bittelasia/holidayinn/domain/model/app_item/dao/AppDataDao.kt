package com.bittelasia.holidayinn.domain.model.app_item.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.holidayinn.domain.model.app_item.item.AppData

@Dao
interface AppDataDao {
    @Query("SELECT * FROM AppData")
    fun getAllAppDataItems(): List<AppData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppData(appList: List<AppData>)

    @Query("DELETE FROM AppData")
    suspend fun deleteAllAppDataItems()
}