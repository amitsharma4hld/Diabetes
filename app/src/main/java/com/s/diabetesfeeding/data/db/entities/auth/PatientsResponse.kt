package com.s.diabetesfeeding.data.db.entities.auth

data class PatientsResponse(
    val `data`: List<Data>,
    val error_code: String,
    val status: String
)