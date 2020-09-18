package com.s.diabetesfeeding.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.s.diabetesfeeding.data.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class ForgotPassModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ForgotPassModel(repository) as T
    }

}