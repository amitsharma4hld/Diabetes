package com.s.diabetesfeeding.data.db

import androidx.room.*
import com.s.diabetesfeeding.data.db.entities.breastfeeding.*
import com.s.diabetesfeeding.data.db.entities.obgynentities.ProgressPrentalVisit
import org.threeten.bp.OffsetDateTime

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

    // New Line
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgressBreastFeeding(progressBreastFeeding: ProgressBreastFeeding )
    @Query("SELECT *  FROM ProgressBreastFeeding  WHERE dateTime BETWEEN :from AND :to")
    fun getProgressDataBetweenDates(from: OffsetDateTime, to: OffsetDateTime): List<ProgressBreastFeeding>
    // New Line
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgressDiaperChange( progressBabyPoopDiaperChange: ProgressBabyPoopDiaperChange )
    @Query("SELECT *  FROM ProgressBabyPoopDiaperChange  WHERE dateTime BETWEEN :from AND :to")
    fun getProgressDiaperChange(from: OffsetDateTime, to: OffsetDateTime): List<ProgressBabyPoopDiaperChange>

}