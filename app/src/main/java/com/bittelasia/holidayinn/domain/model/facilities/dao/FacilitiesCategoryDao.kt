package com.bittelasia.holidayinn.domain.model.facilities.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bittelasia.holidayinn.domain.model.facilities.category.FacilitiesCategoryData

@Dao
interface FacilitiesCategoryDao {

    @Query("SELECT * FROM facilities_category")
    fun getFacilitiesCategories(): List<FacilitiesCategoryData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFacilitiesCategories(facilitiesCategory: List<FacilitiesCategoryData>)

    @Query("DELETE FROM facilities_category")
    suspend fun deleteFacilitiesCategories()

}
