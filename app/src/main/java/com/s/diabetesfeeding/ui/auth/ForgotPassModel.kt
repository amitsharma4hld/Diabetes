package com.s.diabetesfeeding.ui.auth

import android.view.View
import androidx.databinding.ObservableBoolean
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
    var code: String? = null
    var userDeviceType: String? = "Android"
    var authListener: AuthListener? = null
    val isOtpSend = ObservableBoolean(false)


    fun onContinueButtonClick(view: View){
        authListener?.onStarted()
        if (isOtpSend.get()){
            if (email.isNullOrEmpty() && code.isNullOrEmpty() && password.isNullOrEmpty()){
                authListener?.onFailure("All fields are required ")
                return
            }
            Coroutines.main {
                try {
                    val authResponse = repository.updatePass(email!!,code!!,password!!)
                    if(authResponse.success=="true"){
                        authResponse.data?.let {
                                authListener?.onFailure(it.msg!!)
                                return@main

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
        }else{
            if (email.isNullOrEmpty()){
                authListener?.onFailure("Invalid Email")
                return
            }
            Coroutines.main {
                try {
                    val authResponse = repository.forgotPass(email!!)
                    if(authResponse.status=="success"){
                        authResponse.data?.let {
                            if (it.user_status != "0"){
                                isOtpSend.set(true)
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

}