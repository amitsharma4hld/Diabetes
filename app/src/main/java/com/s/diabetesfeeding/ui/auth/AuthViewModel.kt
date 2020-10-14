package com.s.diabetesfeeding.ui.auth

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.s.diabetesfeeding.data.repositories.UserRepository
import com.s.diabetesfeeding.util.ApiException
import com.s.diabetesfeeding.util.Coroutines
import com.s.diabetesfeeding.util.NoInternetException

class AuthViewModel(
   private var repository: UserRepository
) : ViewModel() {
    val isLoginSelected = ObservableBoolean(true)
    val isSignUpSelected = ObservableBoolean(false)

    var isSignUpDone = ObservableBoolean(false)
    var isStudyGroupSelect = ObservableBoolean(false)

    var email: String? = null
    var password: String? = null
    var confirmpassword: String? = null
    var username: String? = null
    var salutation: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var role:String? = null
    var cellPhoneNumber:String? = null
    var confirmPhoneNumber:String? = null
    var officePhoneNumber:String? = null
    var group:String? = null
    var userType: String? = "doctor"
    var userDeviceType: String? = "Android"
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
                }catch (e:ApiException){
                    authListener?.onFailure(e.message!!)
                }
                catch (e: NoInternetException){
                    authListener?.onFailure(e.message!!)
                }
            }
        }
        if (isSignUpSelected.get()){

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
            isSignUpDone.set(true)
            authListener?.onFailure("Please continue")

        }
        if (isSignUpDone.get()){
            if (salutation.isNullOrEmpty()){
                authListener?.onFailure("salutation is required")
                return
            }
            if (firstName.isNullOrEmpty()){
                authListener?.onFailure("First Name is required")
                return
            }
            if (lastName.isNullOrEmpty()){
                authListener?.onFailure("Last Name is required")
                return
            }
            if (role.isNullOrEmpty()){
                authListener?.onFailure("Role is required")
                return
            }
            if (cellPhoneNumber.isNullOrEmpty()){
                authListener?.onFailure("Cell Phone Number is required")
                return
            }
            if (cellPhoneNumber != confirmPhoneNumber){
                authListener?.onFailure("Cell Phone Number did not match")
                return
            }
            if (officePhoneNumber.isNullOrEmpty()){
                authListener?.onFailure("Office Phone Number is required")
                return
            }
            isStudyGroupSelect.set(true)
            //isSignUpDone.set(true)
            authListener?.onFailure("Please continue")
        }

         if (isStudyGroupSelect.get()){
            if (group.isNullOrEmpty()) {
                authListener?.onFailure("group is required")
                return
            }
            Coroutines.main {
                try {
                    val authResponse = repository
                        .userRegister(email!!,password!!,username!!,userType!!,password!!,userDeviceType!!,
                        salutation!!,firstName!!,role!!,cellPhoneNumber!!,officePhoneNumber!!,group!!)

                    if(authResponse.status=="success"){
                        authResponse.data?.let {
                            if (it.user_status != "0"){
                                authListener?.onSuccess(it)
                                repository.saveData(it)
                                return@main
                            }else{
                                if (authResponse.data.msg.isNullOrBlank()){
                                    authListener?.onVerificationFailed("Register Successful.","Please verify your mail and than login.")
                                }else
                                    authListener?.onFailure(authResponse.data.msg!!)
                                return@main
                            }
                        }
                    }else{
                        authListener?.onFailure(authResponse.data?.msg!!)
                    }

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
        isSignUpDone.set(false)
        showOrGoneOnLoginClick(view)

    }
    fun onSingUpSelect(view: View){
        isSignUpSelected.set(true)
        isLoginSelected.set(false)
        isSignUpDone.set(false)
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
    fun onRoleClick(){

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