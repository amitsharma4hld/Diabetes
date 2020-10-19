package com.s.diabetesfeeding.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.s.diabetesfeeding.data.repositories.MonitorbloodGlucoseRepository

@Suppress("UNCHECKED_CAST")
class MonitorBloodGlucoseViewModelFactory(
    private val repository: MonitorbloodGlucoseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MonitorBloodGlucoseViewModel(repository) as T
    }
}