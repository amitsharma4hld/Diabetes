package com.s.diabetesfeeding.data.db.entities.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.s.diabetesfeeding.data.repositories.UserRepository
import com.s.diabetesfeeding.ui.auth.PatientsListModel

class PatientListModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PatientsListModel(repository) as T
    }

}
