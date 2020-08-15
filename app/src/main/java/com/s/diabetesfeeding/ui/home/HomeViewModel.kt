package com.s.diabetesfeeding.ui.home

import androidx.lifecycle.ViewModel
import com.s.diabetesfeeding.data.repositories.UserRepository

class HomeViewModel(
    repository: UserRepository
): ViewModel() {
    val user = repository.getData()
}