package com.s.diabetesfeeding.data.db

import androidx.room.*
import com.s.diabetesfeeding.data.db.entities.obgynentities.*

@Dao
interface ObgynDao {
    // Observe MySelf
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllObservation(symptom: List<Observation>)
    @Query("SELECT * FROM Observation")
    suspend fun getAllObservation(): List<Observation>
    @Update
    suspend fun updateObservation(symptom: Observation)

    // Counseling Trimester
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllTrimesterOne(symptom: List<TrimesterDataOne>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllTrimesterTwo(symptom: List<TrimesterDataTwo>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllTrimesterThree(symptom: List<TrimesterDataThree>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllPostPartumData(symptom: List<PostPartumData>)
    @Query("SELECT * FROM TrimesterDataOne")
    suspend fun getAllTrimesterOne(): List<TrimesterDataOne>
    @Query("SELECT * FROM TrimesterDataTwo")
    suspend fun getAllTrimesterTwo(): List<TrimesterDataTwo>
    @Query("SELECT * FROM TrimesterDataThree")
    suspend fun getAllTrimesterThree(): List<TrimesterDataThree>
    @Query("SELECT * FROM PostPartumData")
    suspend fun getAllPostPartumData(): List<PostPartumData>
    @Update
    suspend fun updateTrimesterDataOne(trimesterDataOne: TrimesterDataOne)
    @Update
    suspend fun updateTrimesterDataTwo(trimesterDataTwo: TrimesterDataTwo)
    @Update
    suspend fun updateTrimesterDataThree(trimesterDataThree: TrimesterDataThree)
    @Update
    suspend fun updatePostPartumData(postPartumData: PostPartumData)

    // Parental Visit
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllPrentalVisitRecord(prentalVisitRecord: List<PrentalVisitRecord>)
    @Query("SELECT * FROM PrentalVisitRecord")
    suspend fun getAllPrentalVisitRecord(): List<PrentalVisitRecord>
    @Update
    suspend fun updatePrentalVisitRecord(prentalVisitRecord: PrentalVisitRecord)

}