package com.s.diabetesfeeding.data.db

import androidx.room.*
import com.s.diabetesfeeding.data.db.entities.ProgressBloodGlucose
import com.s.diabetesfeeding.data.db.entities.ProgressSymptoms
import com.s.diabetesfeeding.data.db.entities.obgynentities.*
import org.threeten.bp.OffsetDateTime

@Dao
interface ObgynDao {
    // Observe MySelf
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllObservation(symptom: List<Observation>)

    @Query("SELECT * FROM Observation")
    suspend fun getAllObservation(): List<Observation>

    @Update
    suspend fun updateObservation(symptom: Observation)
    @Query("SELECT *  FROM ProgressObservation  WHERE offsetDateTime BETWEEN :from AND :to")
    fun getProgressDataWithoutId(from: OffsetDateTime, to: OffsetDateTime): List<ProgressObservation>

    @Query("UPDATE Observation SET comment=:comment,isChecked=:isChecked WHERE title=:title")
    fun updateObservationColumn(title:String,comment:String,isChecked:Boolean)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgressData(progressObservation: ProgressObservation)

    // Counseling Trimester
    @Query("SELECT *  FROM ProgressAllTrimester  WHERE trimesterId=:id AND offsetDateTime BETWEEN :from AND :to")
    fun getAllTrimesterProgressBetweenDates(id:Int,from: OffsetDateTime, to: OffsetDateTime): List<ProgressAllTrimester>
    @Query("UPDATE PostPartumData SET comment=:comment,isChecked=:isChecked,date=:date WHERE title=:title")
    fun updateTrimesterPostPartumColumn(title:String,comment:String,isChecked:Boolean,date:String)
    @Query("UPDATE TrimesterDataThree SET comment=:comment,isChecked=:isChecked,date=:date WHERE title=:title")
    fun updateTrimesterThreeColumn(title:String,comment:String,isChecked:Boolean,date:String)
    @Query("UPDATE TrimesterDataTwo SET comment=:comment,isChecked=:isChecked,date=:date WHERE title=:title")
    fun updateTrimesterTwoColumn(title:String,comment:String,isChecked:Boolean,date:String)
    @Query("UPDATE TrimesterDataOne SET comment=:comment,isChecked=:isChecked,date=:date WHERE title=:title")
    fun updateTrimesterOneColumn(title:String,comment:String,isChecked:Boolean,date:String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllTrimesterProgressData(progressAllTrimester: ProgressAllTrimester)


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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgressPrentalVisit(progressPrentalVisit: ProgressPrentalVisit )
    @Query("SELECT *  FROM ProgressPrentalVisit  WHERE prentalVisiId=:id AND dateTime BETWEEN :from AND :to")
    fun getProgressDataBetweenDates(id:Int, from: OffsetDateTime, to: OffsetDateTime): List<ProgressPrentalVisit>

    @Query("SELECT *  FROM ProgressPrentalVisit  WHERE dateTime BETWEEN :from AND :to")
    fun getProgressDataBetweenDatesWithoutId(from: OffsetDateTime, to: OffsetDateTime): List<ProgressPrentalVisit>

    @Query("UPDATE PrentalVisitRecord SET value=:value WHERE measurementOf=:measurementOf")
    fun updatePrentalVisitColumn(value:String,measurementOf:String)

    @Query("DELETE FROM ProgressPrentalVisit")
    fun deleteProgressPrentalVisit()
}