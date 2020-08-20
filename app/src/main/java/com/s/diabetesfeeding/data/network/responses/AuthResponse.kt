package com.s.diabetesfeeding.data.network.responses

import com.s.diabetesfeeding.data.db.entities.Data


data class AuthResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val data: Data?,
    val status: String?,
    val error_code: String?
)