package com.s.diabetesfeeding.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.s.diabetesfeeding.data.db.entities.*
import org.threeten.bp.OffsetDateTime

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
/*    @Query("DELETE FROM BloodGlucoseCategoryItem Where value")
    suspend fun deleteTodaysMonitorGlucoseData()*/

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

    // Monitor Blood Glucose Progress Data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgressBloodGlucoseData(progressBloodGlucose: ProgressBloodGlucose)
    @Transaction
    @Query("SELECT * FROM MonitorBloodGlucoseCategory")
    fun getProgressDataWithCategory(): LiveData<List<ProgressDataWithCategory>>

    @Query("SELECT *  FROM ProgressBloodGlucose  WHERE itemsCatId=:id AND dateTime BETWEEN :from AND :to")
    fun getProgressDataBetweenDates(id:Int,from: OffsetDateTime, to: OffsetDateTime): List<ProgressBloodGlucose>

/*   // Custom scoreboard data
    @Query("select DISTINCT *  from ScoreTable st LEFT  JOIN HomeSubMenus HSM on HSM.id=ST.sub_menuid INNER  JOIN HomeMenus HM on HM.id=HSM.homeMenuId INNER  JOIN ScoreType STY on STY.id=HSM.score_type_id")
    suspend fun getFilterScoreTable():List<FilterScoreTable>
*/

}