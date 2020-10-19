package com.s.diabetesfeeding.ui.auth

import androidx.lifecycle.ViewModel
import com.s.diabetesfeeding.data.db.entities.auth.Data
import com.s.diabetesfeeding.data.repositories.UserRepository
import com.s.diabetesfeeding.prefs
import com.s.diabetesfeeding.util.ApiException
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.NoInternetException

class PatientsListModel (
    private var repository: UserRepository
) : ViewModel() {
    var authListener: PatientsDataListner? = null
    fun getLoggedInUser() = repository.getData()

    fun getMyPatientsList(){
        authListener?.onStarted()
        Coroutines.main {
            try {
                val authResponse = repository.getPatientsList(prefs.getSavedDoctorId()!!)
                if(authResponse.status=="true"){
                    authResponse.data?.let {
                        if (it.isNotEmpty()){
                            authListener?.onSuccess(it)
                            return@main
                        }else{
                            authListener?.onFailure("Something went wrong")
                        }
                    }
                }else{
                    authListener?.onFailure(authResponse.status)
                }
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }
            catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }
    }
    suspend fun savePatientData(data: com.s.diabetesfeeding.data.db.entities.Data){
        repository.saveData(data)
    }
}