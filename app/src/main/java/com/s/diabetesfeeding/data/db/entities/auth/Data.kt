package com.s.diabetesfeeding.data.db.entities.auth

data class Data(
    val ID: String,
    val appointment: String,
    val deviceType: String,
    val display_name: String,
    val doctorName: String,
    val key: String,
    val profileImage: String,
    var role: String,
    val type: String,
    val user_activation_key: String,
    val user_email: String,
    val user_login: String,
    val user_registered: String,
    val user_status: String,
    val weekOfPregnancy: Int
)