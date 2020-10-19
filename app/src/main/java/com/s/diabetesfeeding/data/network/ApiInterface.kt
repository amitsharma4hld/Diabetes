package com.s.diabetesfeeding.data.network

import com.s.diabetesfeeding.data.db.entities.auth.PatientsResponse
import com.s.diabetesfeeding.data.network.responses.AuthResponse
import com.s.diabetesfeeding.data.network.responses.MonitorbloodGlucoseResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PUT

interface ApiInterface {
    @FormUrlEncoded
    @PUT("login")
    suspend fun userLogin(
        @Field("username") email: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @PUT("register")
    suspend fun userRegister(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") username: String,
        @Field("type") type: String,
        @Field("key") key: String,
        @Field("deviceType") deviceType: String,
        @Field("salutation") salutation: String,
        @Field("name") name: String,
        @Field("role") role: String,
        @Field("phoneNumber") cellPhoneNumber: String,
        @Field("officePhoneNumber") officePhoneNumber: String,
        @Field("group") group: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @PUT("forgot-password")
    suspend fun forgotPass(
        @Field("email") email: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @PUT("update-password")
    suspend fun updatePassword(
        @Field("email") email: String,
        @Field("code") code: String,
        @Field("password") password: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @PUT("get-patients")
    suspend fun getPatients(
        @Field("doctor_id") doctor_id: String
    ): Response<PatientsResponse>

    @FormUrlEncoded
    @PUT("get-all-users")
    suspend fun getAllUser(
        @Field("page") page: String
    ): Response<PatientsResponse>

    @GET("get-fields")
    suspend fun getMonitorbloodGlucose() :Response<MonitorbloodGlucoseResponse>

    @FormUrlEncoded
    @PUT("update-role")
    suspend fun updateRole(
        @Field("user_id") userId: String,
        @Field("role") code: String
    ): Response<AuthResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ApiInterface{
            val okkHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okkHttpClient)
                .baseUrl("http://35.194.73.39/wp-json/diabeties-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)
        }
    }

}