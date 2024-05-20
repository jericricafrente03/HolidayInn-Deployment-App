package com.bittelasia.holidayinn.domain.model.guest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.holidayinn.domain.model.guest.item.GuestData

@Dao
interface GuestDao {
    @Query("SELECT * FROM guestdata")
    fun getGuest(): GuestData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuest(facilities: GuestData)

    @Query("DELETE FROM guestdata")
    suspend fun deleteGuest()
}
