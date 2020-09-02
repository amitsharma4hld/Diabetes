package com.s.diabetesfeeding.data.db

import androidx.room.*
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BabyPoopData
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BreastFeedingSessionData
import com.s.diabetesfeeding.data.db.entities.breastfeeding.BabyWeight
import com.s.diabetesfeeding.data.db.entities.breastfeeding.ObservationBreastFeed

@Dao
interface BreastFeedingDao {
    // BreastFeedingSession
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllBreastFeedSession(session:List<BreastFeedingSessionData>)
    @Query("SELECT * FROM BreastFeedingSessionData")
    suspend fun getAllBreastFeedSession(): List<BreastFeedingSessionData>
    @Update
    suspend fun updateBreastFeedSesssion(session: BreastFeedingSessionData)

    // DiaperChange
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllDiaperChangeSession(session:List<BabyPoopData>)
    @Query("SELECT * FROM BabyPoopData")
    suspend fun getAllDiaperChangeSession(): List<BabyPoopData>
    @Update
    suspend fun updateDiaperChangeSesssion(session: BabyPoopData)

    // Observation
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllObservation(symptom: List<ObservationBreastFeed>)
    @Query("SELECT * FROM ObservationBreastFeed")
    suspend fun getAllObservation(): List<ObservationBreastFeed>
    @Update
    suspend fun updateObservation(symptom: ObservationBreastFeed)

    // Baby's Weight
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBabyWeight(weight: BabyWeight)
    @Query("SELECT * FROM BabyWeight")
    suspend fun getBabyWeight():List<BabyWeight>
}