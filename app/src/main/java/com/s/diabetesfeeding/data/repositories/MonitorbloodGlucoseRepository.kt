package com.s.diabetesfeeding.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.MonitorbloodGlucose
import com.s.diabetesfeeding.data.db.entities.Symptom
import com.s.diabetesfeeding.data.network.ApiInterface
import com.s.diabetesfeeding.data.network.SafeApiRequest
import com.s.diabetesfeeding.data.preferences.PreferenceProvider
import com.s.diabetesfeeding.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

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
            saveMonitorbloodGlucose(it.get(1))
        }
        symptom.observeForever{
            saveSymptom(it.get(0))
        }
    }

    suspend fun getMonitorbloodGlucose(): LiveData<List<MonitorbloodGlucose>> {
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
    }

    private suspend fun fetchMonitorbloodGlucose() {
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
    }
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
}