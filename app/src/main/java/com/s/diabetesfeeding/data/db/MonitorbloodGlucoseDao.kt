package com.s.diabetesfeeding.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.s.diabetesfeeding.data.db.entities.MonitorbloodGlucose
import com.s.diabetesfeeding.data.db.entities.Symptom

@Dao
interface MonitorbloodGlucoseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun saveAllMonitorbloodGlucose(monitorbloodGlucose: List<MonitorbloodGlucose>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun saveAllSymptoms(symptom: List<Symptom>)

    @Query("SELECT * FROM MonitorbloodGlucose")
    fun getMonitorbloodGlucose(): LiveData<List<MonitorbloodGlucose>>

    @Query("SELECT * FROM Symptom")
    fun getSymptom(): LiveData<List<Symptom>>
}