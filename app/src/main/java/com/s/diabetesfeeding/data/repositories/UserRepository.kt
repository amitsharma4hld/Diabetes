package com.s.diabetesfeeding.data.repositories

import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.Data
import com.s.diabetesfeeding.data.network.ApiInterface
import com.s.diabetesfeeding.data.network.SafeApiRequest
import com.s.diabetesfeeding.data.network.responses.AuthResponse

class UserRepository(
    private  val api: ApiInterface,
    private val db: AppDatabase
) : SafeApiRequest() {
    suspend fun userLogin(email: String, password: String) : AuthResponse {
        return apiRequest { api.userLogin(email, password) }
        //return ApiInterface().userLogin(email,password)
    }
    suspend fun forgotPass(email: String) : AuthResponse {
        return apiRequest { api.forgotPass(email) }
    }
    suspend fun updatePass(email: String,code:String,password: String) : AuthResponse {
        return apiRequest { api.updatePassword(email,code,password) }
    }
    suspend fun userRegister(email: String,password: String,userName: String,type: String,key: String,deviceType: String,
                             salutation: String,name: String,role: String,cellPhoneNumber: String,officePhoneNumber: String,group: String): AuthResponse {
        return  apiRequest { api.userRegister(email,password,userName,type,key,deviceType,salutation,name,role,cellPhoneNumber,officePhoneNumber,group) }
    }

    suspend fun saveData(data: Data) = db.getUserDao().upsert(data)
    fun getData() = db.getUserDao().getuser()

}