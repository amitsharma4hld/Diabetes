package com.s.diabetesfeeding.data.db

import androidx.room.*
import com.s.diabetesfeeding.data.db.entities.*

@Dao
interface MonitorBloodGlucoseCatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllMonitorbloodGlucoseCat(monitorBloodGlucoseCategory: List<MonitorBloodGlucoseCategory>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllBloodGlucoseCategoryItem(bloodGlucoseCategoryItem: List<BloodGlucoseCategoryItem>)
    @Query("SELECT * FROM MonitorBloodGlucoseCategory")
    suspend fun getAllCategory() : List<MonitorBloodGlucoseCategory>
    @Query("SELECT * FROM BloodGlucoseCategoryItem")
    suspend fun getAllCategoryItems() : List<BloodGlucoseCategoryItem>
    @Update
    suspend fun updateBloodGlucoseCategoryItem(bloodGlucoseCategoryItem: BloodGlucoseCategoryItem)
    @Transaction
    @Query("SELECT * FROM MonitorBloodGlucoseCategory")
    suspend fun getItemsAndCategory(): List<CategoryWithItems>

    // Today's Insulin
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTodaysInsulin(insulinToday: InsulinToday)
    @Query("SELECT * FROM InsulinToday")
    suspend fun getTodaysInsulin():List<InsulinToday>

    // Today's Weight
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTodaysWeight(weight: WeightToday)
    @Query("SELECT * FROM WeightToday")
    suspend fun getTodaysWeight():List<WeightToday>



}