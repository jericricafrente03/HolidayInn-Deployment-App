package com.bittelasia.holidayinn.domain.model.theme.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.holidayinn.domain.model.theme.item.Theme

@Dao
interface ThemeDao {
    @Query("SELECT * FROM theme")
    fun getAllThemes(): Theme

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThemes(theme: Theme)

    @Query("DELETE FROM theme")
    suspend fun deleteAllThemes()
}