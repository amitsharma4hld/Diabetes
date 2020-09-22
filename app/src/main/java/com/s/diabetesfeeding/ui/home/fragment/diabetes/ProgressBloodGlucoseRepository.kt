package com.s.diabetesfeeding.ui.home.fragment.diabetes

import androidx.lifecycle.LiveData
import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.ProgressBloodGlucose
import com.s.diabetesfeeding.data.db.entities.ProgressDataWithCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.OffsetDateTime

class ProgressBloodGlucoseRepository (
    private val db: AppDatabase
){
     //fun getProgressData() = db.getMonitorBloodGlucoseCatDao().getProgressDataWithCategory()

    suspend fun getProgressData(): LiveData<List<ProgressDataWithCategory>> {
        return withContext(Dispatchers.IO) {
            db.getMonitorBloodGlucoseCatDao().getProgressDataWithCategory()
        }
    }
  /*  suspend fun getProgressDataByDate(id:Int,FromDate:OffsetDateTime,ToDate:OffsetDateTime): LiveData<List<ProgressBloodGlucose>> {
        return withContext(Dispatchers.IO) {
            db.getMonitorBloodGlucoseCatDao().getProgressDataBetweenDates(id,FromDate,ToDate)
        }
    }*/
}