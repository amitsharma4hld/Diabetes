package com.s.diabetesfeeding.data.db

import androidx.room.*
import com.s.diabetesfeeding.data.db.entities.*
import java.time.OffsetDateTime

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
    @Transaction
    @Query("SELECT * FROM HomeSubMenus")
    suspend fun getScoreWithCategory(): List<ScoreWithCategory>
    @Transaction
    @Query("SELECT * FROM ScoreTable WHERE sub_menuId=:subId")
    suspend fun getScoreByCategory(subId:Int):List<ScoreTable>
  /*  @Query("SELECT DISTINCT date_time from ScoreTable")
    suspend fun getDistinctDateTime():List<OffsetDateTime>*/

}