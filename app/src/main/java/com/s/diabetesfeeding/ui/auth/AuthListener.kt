package com.s.diabetesfeeding.ui.auth

import com.s.diabetesfeeding.data.db.entities.Data

interface AuthListener {
    fun onStarted()
    fun onSuccess(data: Data)
    fun onFailure(message: String)
}