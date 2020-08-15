package com.s.diabetesfeeding.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val  CURRENT_USER_ID = 0

@Entity
data class Data(
    var ID: Int? = null,
    var user_login: String? = null,
    var user_email: String? = null,
    var display_name: String? = null,
    var type: String? = null,
    var profileImage: String? = null,
    var token: String? = null,
    var msg: String? = null
    ){
    @PrimaryKey(autoGenerate = false)
    var uid:Int = CURRENT_USER_ID
}
