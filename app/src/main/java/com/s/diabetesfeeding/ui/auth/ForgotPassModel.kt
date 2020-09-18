package com.s.diabetesfeeding.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.s.diabetesfeeding.data.repositories.UserRepository
import com.s.diabetesfeeding.util.ApiException
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.NoInternetException

class ForgotPassModel (
    private var repository: UserRepository
) : ViewModel() {
    var email: String? = null
    var password: String? = null
    var userDeviceType: String? = "Android"
    var authListener: AuthListener? = null

    fun onContinueButtonClick(view: View){
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid Email or Password")
            return
        }
        Coroutines.main {
            try {
                val authResponse = repository.forgotPass(email!!,password!!)
                if(authResponse.status=="success"){
                    authResponse.data?.let {
                        if (it.user_status != "0"){
                            authListener?.onSuccess(it)
                            repository.saveData(it)
                            return@main
                        }else{
                            authListener?.onFailure("Please verify the user and try again.")
                        }
                    }
                }else{
                    authListener?.onFailure(authResponse.data?.msg!!)
                }
            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }
            catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }


    }

}