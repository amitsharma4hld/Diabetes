package com.s.diabetesfeeding.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.s.diabetesfeeding.data.SymptomsData
import com.s.diabetesfeeding.data.db.entities.Symptom

@Dao
interface SymptomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllSymptoms(symptom: List<SymptomsData>)

    @Query("SELECT * FROM SymptomsData")
    suspend fun getSymptom(): List<SymptomsData>

    @Update
    suspend fun updateSymptom(symptom: SymptomsData)

}