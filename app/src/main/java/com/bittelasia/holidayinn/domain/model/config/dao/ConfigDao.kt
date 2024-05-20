package com.bittelasia.holidayinn.domain.model.config.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.holidayinn.domain.model.config.item.SystemData

@Dao
interface ConfigDao {
    @Query("SELECT * FROM config")
    fun getAllConfig(): SystemData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfig(configData: SystemData)

    @Query("DELETE FROM config")
    suspend fun deleteAllConfig()
}