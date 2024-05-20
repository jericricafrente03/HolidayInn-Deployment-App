package com.bittelasia.holidayinn.domain.model.concierge.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.holidayinn.domain.model.concierge.item.ConciergeData

@Dao
interface ConciergeDataDao {
    @Query("SELECT * FROM conciergedata")
    fun getAllConciergeDataItems(): List<ConciergeData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConciergeData(appList: List<ConciergeData>)

    @Query("DELETE FROM conciergedata")
    suspend fun deleteAllConciergeDataItems()
}