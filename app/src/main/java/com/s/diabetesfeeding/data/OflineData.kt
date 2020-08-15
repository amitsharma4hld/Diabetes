package com.s.diabetesfeeding.data

import com.orhanobut.hawk.Hawk

class OflineData {




    fun getUserName(): String {
        return Hawk.get("UserName", "")
    }

    fun  saveUserName(volume: String) {
        Hawk.put("UserName", volume)
    }





}