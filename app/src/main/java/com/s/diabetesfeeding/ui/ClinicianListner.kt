package com.s.diabetesfeeding.ui

import com.s.diabetesfeeding.data.db.entities.Data

interface ClinicianListner {
    fun onPatientSelect(data:Data)
}