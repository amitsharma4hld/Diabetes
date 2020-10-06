package com.s.diabetesfeeding.data.db

import androidx.room.*
import com.s.diabetesfeeding.data.db.entities.*
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BreastFeedingSessionData
import org.threeten.bp.OffsetDateTime


@Dao
interface HomeMenusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllMenus(homeMenus: List<HomeMenus>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllSubMenus(subMenus: List<HomeSubMenus>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveScores(scoreTable: ScoreTable)


    @Query("SELECT * FROM HomeMenus")
    suspend fun getAllMenus(): List<HomeMenus>
    @Transaction
    @Query("SELECT * FROM HomeSubMenus")
    suspend fun getAllSubMenu(): List<HomeSubMenus>
    @Transaction
    @Query("SELECT * FROM HomeMenus")
    suspend fun getMenuAndSubMenu(): List<MenusWithSubMenus>

    @Transaction
    @Query("SELECT * FROM ScoreTable")
    suspend fun getAllScores(): List<ScoreTable>
 /*   @Transaction
    @Query("SELECT * FROM HomeSubMenus")
    suspend fun getScoreWithCategory(): List<ScoreWithCategory>*/

    @Transaction
    @Query("SELECT * FROM ScoreTable WHERE sub_menuId=:subId")
    suspend fun getScoreByCategory(subId:Int):List<ScoreTable>

    @Query("SELECT date_time from ScoreTable")
    suspend fun getDistinctDateTime():List<DistinctDateTimeList>

    // ScoreType Dao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllScoreType(scoreType: List<ScoreType>)
    @Transaction
    @Query("SELECT * FROM ScoreType")
    suspend fun getAllScoreType(): List<ScoreType>

    // Custom scoreboard data
    @Query("select DISTINCT homeMenuId,menuName,sub_menuId,subMenuName,score,score_type,score_type_id,date_time  from ScoreTable st LEFT  JOIN HomeSubMenus HSM on HSM.id=ST.sub_menuid INNER  JOIN HomeMenus HM on HM.id=HSM.homeMenuId INNER  JOIN ScoreType STY on STY.id=HSM.score_type_id")
    suspend fun getFilterScoreTable():List<FilterScoreTable>

    // Step Count Offline data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSteps(stepCount: StepCount)
    @Transaction
    @Query("SELECT * FROM StepCount")
    suspend fun getAllStepsCount(): List<StepCount>
    @Update
    suspend fun updateStepsCount(stepCount: StepCount)
    @Query("SELECT * FROM StepCount WHERE offsetDateTime BETWEEN :from AND :to")
    suspend fun getStepsCountDateWise(from: OffsetDateTime, to: OffsetDateTime): List<StepCount>

}