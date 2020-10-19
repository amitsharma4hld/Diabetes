package com.s.diabetesfeeding.ui.auth

interface PatientsDataListner {
    fun onStarted()
    fun onSuccess(data: List<com.s.diabetesfeeding.data.db.entities.auth.Data>)
    fun onFailure(message: String)
}