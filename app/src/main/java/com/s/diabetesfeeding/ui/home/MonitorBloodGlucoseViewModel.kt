package com.s.diabetesfeeding.ui.home

import androidx.lifecycle.ViewModel
import com.s.diabetesfeeding.data.repositories.MonitorbloodGlucoseRepository
import com.s.diabetesfeeding.ui.auth.AuthListener
import com.s.diabetesfeeding.ui.home.fragment.diabetes.MonitorGlucoseResponseListner
import com.s.diabetesfeeding.util.ApiException
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.NoInternetException
import com.s.diabetesfeeding.util.lazyDeferred
import com.s.diabetesfeeding.util.logger.Log

class MonitorBloodGlucoseViewModel(
    private var repository: MonitorbloodGlucoseRepository
): ViewModel() {
    var monitorGlucoseResponseListner: MonitorGlucoseResponseListner? = null

    /*val monitorbloodGlucose by lazyDeferred {
        repository.getMonitorbloodGlucose()
    }
    val symptom by lazyDeferred {
        repository.getSymptom()
    }*/
    fun saveBloodGlucoseDataToServer(userId:String,type:String,wakUpFastingPoint:String,wakUpFastingTime:String,
                                     wakUpFastingTotalPoints: String,beforeBreakfastPoints:String,beforeBreakfastTime: String,beforeBreakfastTotalPoints:String,
                                     oneHrAfterBreakfastPoints:String,oneHrAfterBreakfastTime:String,oneHrAfterBreakfastTotalPoints:String,twoHrAfterBreakfastPoints:String,
                                     twoHrAfterBreakfastTime:String,twoHrAfterBreakfastTotalPoints:String,beforeLunchPoints:String,beforeLunchTime:String,
                                     beforeLunchTotal_points:String,oneHrAfterLunchPoints:String,oneHrAfterLunchTime:String,oneHrAfterLunchTotalPoints: String,
                                     twoHrAfterLunchPoints:String,twoHrAfterLunchTime:String,twoHrAfterLunchTotalPoints:String,beforeDinnerPoints:String,beforeDinnerTime:String,
                                     beforeDinnerTotalPoints: String,oneHrAfterDinnerPoints:String,oneHrAfterDinnerTime:String,oneHrAfterDinnerTotalPoints:String,
                                     twoHrAfterDinnerPoints:String,twoHrAfterDinnerTime:String,twoHrAfterDinnerTotalPoints:String,bedTimePoint:String,bedTimeTime: String,
                                     bedTimeTotalPoint: String){
        Coroutines.main {
            monitorGlucoseResponseListner?.onStarted()
            try {
                val authResponse = repository.saveDiabetesBloodGlucoseDataToServer(userId,type,wakUpFastingPoint,
                    wakUpFastingTime,wakUpFastingTotalPoints,beforeBreakfastPoints,beforeBreakfastTime,beforeBreakfastTotalPoints,
                    oneHrAfterBreakfastPoints,oneHrAfterBreakfastTime,oneHrAfterBreakfastTotalPoints,twoHrAfterBreakfastPoints,twoHrAfterBreakfastTime,
                    twoHrAfterBreakfastTotalPoints,beforeLunchPoints,beforeLunchTime,beforeLunchTotal_points,oneHrAfterLunchPoints,oneHrAfterLunchTime,
                    oneHrAfterLunchTotalPoints,twoHrAfterLunchPoints,twoHrAfterLunchTime,twoHrAfterLunchTotalPoints,beforeDinnerPoints,beforeDinnerTime,
                    beforeDinnerTotalPoints,oneHrAfterDinnerPoints,oneHrAfterDinnerTime,oneHrAfterDinnerTotalPoints,twoHrAfterDinnerPoints,twoHrAfterDinnerTime,
                    twoHrAfterDinnerTotalPoints,bedTimePoint,bedTimeTime,bedTimeTotalPoint)
                if(authResponse.success=="success"){
                    monitorGlucoseResponseListner?.onSuccess(authResponse.data)
                }else{
                    monitorGlucoseResponseListner?.onFailure(authResponse.data)
                    return@main
                }
        }catch (e: ApiException){
            Log.e("ApiException",e.message.toString())
        }
        catch (e: NoInternetException){
            Log.e("NoInternetException",e.message.toString())
        }
        }
    }

    fun saveInsulinDataToServer(userId:String,type:String,insulinType:String,totalDailyDose:String,
                                totalCarb:String,currentBloodGlucose:String,targetBloodGlucose:String,totalPoints:String){
        Coroutines.main {
            monitorGlucoseResponseListner?.onStarted()
            try {
                val authResponse = repository.saveInsulinDataToServer(userId,type,insulinType,totalDailyDose,totalCarb,currentBloodGlucose,
                    targetBloodGlucose,totalPoints)
                if(authResponse.success=="success"){
                    monitorGlucoseResponseListner?.onSuccess(authResponse.data)
                }else{
                    monitorGlucoseResponseListner?.onFailure(authResponse.data)
                    return@main
                }

            }catch (e: ApiException){
                Log.e("ApiException",e.message.toString())
            }
            catch (e: NoInternetException){
                Log.e("NoInternetException",e.message.toString())
            }
        }
    }
    fun saveBloodGlucoseWeightDataToServer(userId:String,type:String,weight:String){
        Coroutines.main {
            monitorGlucoseResponseListner?.onStarted()
            try {
                val authResponse = repository.saveBloodWeightDataToServer(userId,type,weight)
                if(authResponse.success=="success"){
                    monitorGlucoseResponseListner?.onSuccess(authResponse.data)
                }else{
                    monitorGlucoseResponseListner?.onFailure(authResponse.data)
                    return@main
                }

            }catch (e: ApiException){
                Log.e("ApiException",e.message.toString())
            }
            catch (e: NoInternetException){
                Log.e("NoInternetException",e.message.toString())
            }
        }
    }

    fun saveSymptomsDataToServer(userId:String,type:String,hypoglycemia:String,other:String,score:String){
        Coroutines.main {
            monitorGlucoseResponseListner?.onStarted()
            try {
                val authResponse = repository.saveSymptomsDataToServer(userId,type,hypoglycemia,other,score)
                if(authResponse.success=="success"){
                    monitorGlucoseResponseListner?.onSuccess(authResponse.data)
                }else{
                    monitorGlucoseResponseListner?.onFailure(authResponse.data)
                    return@main
                }

            }catch (e: ApiException){
                Log.e("ApiException",e.message.toString())
            }
            catch (e: NoInternetException){
                Log.e("NoInternetException",e.message.toString())
            }
        }
    }

/*  val  saveBloodGlucoseDataToServer by lazyDeferred{
      repository.saveDiabetesBloodGlucoseDataToServer()
    }*/


}