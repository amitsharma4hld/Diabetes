package com.s.diabetesfeeding.data.db

import androidx.room.*
import com.s.diabetesfeeding.data.db.entities.BloodGlucoseCategoryItem
import com.s.diabetesfeeding.data.db.entities.CategoryWithItems
import com.s.diabetesfeeding.data.db.entities.MonitorBloodGlucoseCategory
import com.s.diabetesfeeding.data.db.entities.MonitorbloodGlucose

@Dao
interface MonitorBloodGlucoseCatDao {
    @Insert
    suspend fun addCategory(monitorBloodGlucoseCategory: MonitorBloodGlucoseCategory)

    @Insert
    suspend fun addBloodGlucoseCategoryItem(bloodGlucoseCategoryItem: BloodGlucoseCategoryItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllMonitorbloodGlucoseCat(monitorBloodGlucoseCategory: List<MonitorBloodGlucoseCategory>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllBloodGlucoseCategoryItem(bloodGlucoseCategoryItem: List<BloodGlucoseCategoryItem>)

    @Query("SELECT * FROM MonitorBloodGlucoseCategory")
    suspend fun getAllCategory() : List<MonitorBloodGlucoseCategory>

    @Query("SELECT * FROM BloodGlucoseCategoryItem")
    suspend fun getAllCategoryItems() : List<BloodGlucoseCategoryItem>

    @Insert
    suspend fun addAllCategory(vararg monitorBloodGlucoseCategory: MonitorBloodGlucoseCategory)

    @Transaction
    @Query("SELECT * FROM MonitorBloodGlucoseCategory")
    suspend fun getItemsAndCategory(): List<CategoryWithItems>

    @Update
    suspend fun updateBloodGlucoseCategoryItem(bloodGlucoseCategoryItem: BloodGlucoseCategoryItem)

}