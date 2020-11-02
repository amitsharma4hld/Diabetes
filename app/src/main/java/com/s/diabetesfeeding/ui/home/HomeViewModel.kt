package com.s.diabetesfeeding.ui.home

import androidx.lifecycle.ViewModel
import com.s.diabetesfeeding.data.repositories.UserRepository
import com.s.diabetesfeeding.util.ApiException
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.NoInternetException
import com.s.diabetesfeeding.util.logger.Log

class HomeViewModel(
    private var  repository: UserRepository
): ViewModel() {
    val user = repository.getData()

    fun saveStepCountToServer(userId:String,stepCount:String){
        Coroutines.main {
            try {
                val authResponse = repository.saveStepCountToSeverRepo(userId,stepCount)
                if(authResponse.success=="success"){
                    Log.d("success",authResponse.success)
                }else{
                    Log.e("onFailure",authResponse.success)
                }
            }catch (e: ApiException){
                Log.e("ApiException",e.message.toString())
            }
            catch (e: NoInternetException){
                Log.e("NoInternetException",e.message.toString())
            }
        }
    }
}