package com.bittelasia.holidayinn.domain.model.theme.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.holidayinn.domain.model.theme.item.Zone

@Dao
interface ZoneDao {
    @Query("SELECT * FROM zone")
    fun getAllZones(): List<Zone>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertZones(zone: List<Zone>)

    @Query("DELETE FROM zone")
    suspend fun deleteAllZones()
}