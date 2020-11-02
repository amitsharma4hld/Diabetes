package com.s.diabetesfeeding.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.MonitorbloodGlucose
import com.s.diabetesfeeding.data.db.entities.Symptom
import com.s.diabetesfeeding.data.network.ApiInterface
import com.s.diabetesfeeding.data.network.SafeApiRequest
import com.s.diabetesfeeding.data.network.responses.AuthResponse
import com.s.diabetesfeeding.data.network.responses.MonitorbloodGlucoseResponse
import com.s.diabetesfeeding.data.preferences.PreferenceProvider
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime

private val MINIMUM_INTERVAL = 6

class MonitorbloodGlucoseRepository (
    private  val api: ApiInterface,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
): SafeApiRequest() {
    private val monitorbloodGlucose = MutableLiveData<List<List<MonitorbloodGlucose>>>()
    private val symptom = MutableLiveData<List<List<Symptom>>>()

    init {
        monitorbloodGlucose.observeForever {
            for (i in 1 until it.size)
            saveMonitorbloodGlucose(it[i])
        }
        symptom.observeForever{
            saveSymptom(it.get(0))
        }
    }

/*    suspend fun getMonitorbloodGlucose(): LiveData<List<MonitorbloodGlucose>> {
        return withContext(Dispatchers.IO) {
            fetchMonitorbloodGlucose()
            db.getMonitorbloodGlucoseDao().getMonitorbloodGlucose()
        }
    }
    suspend fun getSymptom(): LiveData<List<Symptom>> {
        return withContext(Dispatchers.IO) {
            fetchMonitorbloodGlucose()
            db.getMonitorbloodGlucoseDao().getSymptom()
        }
    }*/

  /*  private suspend fun fetchMonitorbloodGlucose() {
        val lastSavedAt = prefs.getLastSavedAt()
        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            try {
                val response = apiRequest { api.getMonitorbloodGlucose() }
                monitorbloodGlucose.postValue(response.monitorblood_glucose)
                symptom.postValue(response.symptoms)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }*/
    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return true
        //return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }
    private fun saveMonitorbloodGlucose(monitorbloodGlucoses: List<MonitorbloodGlucose>) {
        Coroutines.io {
            prefs.savelastSavedAt(LocalDateTime.now().toString())
            db.getMonitorbloodGlucoseDao().saveAllMonitorbloodGlucose(monitorbloodGlucoses)
        }
    }
    private fun saveSymptom(symptoms: List<Symptom>) {
        Coroutines.io {
            db.getMonitorbloodGlucoseDao().saveAllSymptoms(symptoms)
        }
    }

    suspend fun saveDiabetesBloodGlucoseDataToServer(userId:String,type:String,wakUpFastingPoint:String,wakUpFastingTime:String,
                                                     wakUpFastingTotalPoints: String,beforeBreakfastPoints:String,beforeBreakfastTime: String,beforeBreakfastTotalPoints:String,
                                                     oneHrAfterBreakfastPoints:String,oneHrAfterBreakfastTime:String,oneHrAfterBreakfastTotalPoints:String,twoHrAfterBreakfastPoints:String,
                                                     twoHrAfterBreakfastTime:String,twoHrAfterBreakfastTotalPoints:String,beforeLunchPoints:String,beforeLunchTime:String,
                                                     beforeLunchTotal_points:String,oneHrAfterLunchPoints:String,oneHrAfterLunchTime:String,oneHrAfterLunchTotalPoints: String,
                                                     twoHrAfterLunchPoints:String,twoHrAfterLunchTime:String,twoHrAfterLunchTotalPoints:String,beforeDinnerPoints:String,beforeDinnerTime:String,
                                                     beforeDinnerTotalPoints: String,oneHrAfterDinnerPoints:String,oneHrAfterDinnerTime:String,oneHrAfterDinnerTotalPoints:String,
                                                     twoHrAfterDinnerPoints:String,twoHrAfterDinnerTime:String,twoHrAfterDinnerTotalPoints:String,bedTimePoint:String,bedTimeTime: String,
                                                     bedTimeTotalPoint: String) : MonitorbloodGlucoseResponse {
        return apiRequest { api.saveDiabetesBloodGlucoseDataToServer(userId,type,wakUpFastingPoint,
            wakUpFastingTime,wakUpFastingTotalPoints,beforeBreakfastPoints,beforeBreakfastTime,beforeBreakfastTotalPoints,
            oneHrAfterBreakfastPoints,oneHrAfterBreakfastTime,oneHrAfterBreakfastTotalPoints,twoHrAfterBreakfastPoints,twoHrAfterBreakfastTime,
            twoHrAfterBreakfastTotalPoints,beforeLunchPoints,beforeLunchTime,beforeLunchTotal_points,oneHrAfterLunchPoints,oneHrAfterLunchTime,
            oneHrAfterLunchTotalPoints,twoHrAfterLunchPoints,twoHrAfterLunchTime,twoHrAfterLunchTotalPoints,beforeDinnerPoints,beforeDinnerTime,
            beforeDinnerTotalPoints,oneHrAfterDinnerPoints,oneHrAfterDinnerTime,oneHrAfterDinnerTotalPoints,twoHrAfterDinnerPoints,twoHrAfterDinnerTime,
            twoHrAfterDinnerTotalPoints,bedTimePoint,bedTimeTime,bedTimeTotalPoint) }
    }
    suspend fun saveInsulinDataToServer(userId:String,type:String,insulinType:String,totalDailyDose:String,
                                        totalCarb:String,currentBloodGlucose:String,targetBloodGlucose:String,totalPoints:String) : MonitorbloodGlucoseResponse {
        return apiRequest {
            api.saveInsulinDataToServer(userId,type,insulinType,totalDailyDose,totalCarb,currentBloodGlucose,
                targetBloodGlucose,totalPoints)
        }
    }
    suspend fun saveBloodWeightDataToServer(userId:String,type:String,weight:String) : MonitorbloodGlucoseResponse{
        return  apiRequest {
            api.saveWeightDataToServer(userId,type,weight)
        }
    }

    suspend fun saveSymptomsDataToServer(userId:String,type:String,hypoglycemia:String,other:String,score:String) : MonitorbloodGlucoseResponse{
        return  apiRequest {
            api.saveSymptomsDataToServer(userId,type,hypoglycemia,other,score)
        }
    }
}