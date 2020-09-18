package com.s.diabetesfeeding.data.repositories

import com.s.diabetesfeeding.data.db.AppDatabase
import com.s.diabetesfeeding.data.db.entities.Data
import com.s.diabetesfeeding.data.network.ApiInterface
import com.s.diabetesfeeding.data.network.SafeApiRequest
import com.s.diabetesfeeding.data.network.responses.AuthResponse
import retrofit2.Response
import retrofit2.http.Field

class UserRepository(
    private  val api: ApiInterface,
    private val db: AppDatabase
) : SafeApiRequest() {
    suspend fun userLogin(email: String, password: String) : AuthResponse {
        return apiRequest { api.userLogin(email, password) }
        //return ApiInterface().userLogin(email,password)
    }
    suspend fun forgotPass(email: String, password: String) : AuthResponse {
        return apiRequest { api.forgotPass(email,password) }
    }
    suspend fun userRegister(
        email: String,
        password: String,
        name: String,
        type: String,
        key: String,
        deviceType: String
    ) :AuthResponse {
        return  apiRequest { api.userRegister(email, password, name, type, key, deviceType) }
    }

    suspend fun saveData(data: Data) = db.getUserDao().upsert(data)
    fun getData() = db.getUserDao().getuser()

}