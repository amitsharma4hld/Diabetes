package com.s.diabetesfeeding.ui.home.fragment.diabetes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class ProgressBloodGlucoseModelFactory(
    private val repository: ProgressBloodGlucoseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProgressBloodGlucoseModel(repository) as T
    }

}