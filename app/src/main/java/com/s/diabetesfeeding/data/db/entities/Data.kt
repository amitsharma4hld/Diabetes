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
    var msg: String? = null,
    var user_status: String? = null,
    var weekOfPregnancy: Int? = null,
    var doctorName: String? = null,
    var appointment: String? = null,
    var key: String? = null,
    var user_registered: String? = null,
    var user_activation_key:String? = null,
    var role:String?=null,
    var deviceType: String? = null
    ){
    @PrimaryKey(autoGenerate = false)
    var uid:Int = CURRENT_USER_ID
}
