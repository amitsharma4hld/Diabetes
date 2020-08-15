package com.s.diabetesfeeding.ui.auth

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.s.diabetesfeeding.data.repositories.UserRepository
import com.s.diabetesfeeding.util.ApiException
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.NoInternetException
import kotlinx.android.synthetic.main.activity_login.view.*

class AuthViewModel(
   private var repository: UserRepository
) : ViewModel() {
    val isLoginSelected = ObservableBoolean(true)
    val isSignUpSelected = ObservableBoolean(false)

    var email: String? = null
    var password: String? = null
    var confirmpassword: String? = null
    var username: String? = null

    var authListener: AuthListener? = null
    fun getLoggedInUser() = repository.getData()

    fun onContinueButtonClick(view: View){
        authListener?.onStarted()
        if (isLoginSelected.get()){
            if (email.isNullOrEmpty() || password.isNullOrEmpty()){
                authListener?.onFailure("Invalid Email or Password")
                return
            }
            Coroutines.main {
                try {
                    val authResponse = repository.userLogin(email!!,password!!)
                    authResponse.data?.let {
                        authListener?.onSuccess(it)
                        repository.saveData(it)
                        return@main
                    }
                    authListener?.onFailure(authResponse.message!!)
                }catch (e:ApiException){
                    authListener?.onFailure(e.message!!)
                }
                catch (e: NoInternetException){
                    authListener?.onFailure(e.message!!)
                }
            }
        }else if (isSignUpSelected.get()){

            if (email.isNullOrEmpty()){
                authListener?.onFailure("email is required")
                return
            }
            if (password.isNullOrEmpty()){
                authListener?.onFailure("password is required")
                return
            }
            if (username.isNullOrEmpty()){
                authListener?.onFailure("username is required")
                return
            }
            if (password != confirmpassword){
                authListener?.onFailure("Password did not match")
                return
            }
            Coroutines.main {
                try {
                    val authResponse = repository.userRegister(email!!,password!!,username!!,"doctor",password!!,"Android")
                    authResponse.data?.let {
                        authListener?.onSuccess(it)
                        repository.saveData(it)
                        return@main
                    }
                    authListener?.onFailure(authResponse.message!!)
                }catch (e:ApiException){
                    authListener?.onFailure(e.message!!)
                }
                catch (e: NoInternetException){
                    authListener?.onFailure(e.message!!)
                }
            }

        }

    }

    fun onLoginSelect(view: View){
        isLoginSelected.set(true)
        isSignUpSelected.set(false)
        showOrGoneOnLoginClick(view)

    }
    fun onSingUpSelect(view: View){
        isSignUpSelected.set(true)
        isLoginSelected.set(false)
        showOrGoneOnSignUpClick(view)
    }

    private fun showOrGoneOnLoginClick(view: View) {
        view.visibility = if (isLoginSelected.get()) View.VISIBLE else View.GONE

    }
    fun showOrGoneOnSignUpClick(view: View) {
        view.visibility = if (isSignUpSelected.get()) View.VISIBLE else View.GONE
    }
    fun showHide(view:View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.INVISIBLE
        } else{
            View.VISIBLE
        }
    }
    /*
    fun View.showOrGone(show: Boolean) {
        visibility = if(show) {
            View.VISIBLE
        } else {
            View.GONE
         }
    }
    */

    fun View.showOrInvisible(show: Boolean) {
        visibility = if(show) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }


}