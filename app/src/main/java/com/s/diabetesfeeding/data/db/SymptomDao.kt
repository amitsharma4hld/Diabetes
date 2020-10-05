package com.s.diabetesfeeding.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.entities.ProgressBloodGlucose
import com.s.diabetesfeeding.data.db.entities.ProgressSymptoms
import com.s.diabetesfeeding.data.db.entities.Symptom
import org.threeten.bp.OffsetDateTime

@Dao
interface SymptomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllSymptoms(symptom: List<SymptomsData>)

    @Query("SELECT * FROM SymptomsData")
    suspend fun getSymptom(): List<SymptomsData>

    @Update
    suspend fun updateSymptom(symptom: SymptomsData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgressData(progressSymptoms: ProgressSymptoms)

    @Query("UPDATE SymptomsData SET comment=:comment,isChecked=:isChecked WHERE Symptom=:title")
    fun updateSymptomColumn(title:String,comment:String,isChecked:Boolean)

    @Query("SELECT *  FROM ProgressSymptoms  WHERE offsetDateTime BETWEEN :from AND :to")
    fun getProgressDataWithoutId(from: OffsetDateTime, to: OffsetDateTime): List<ProgressSymptoms>

}