package com.s.diabetesfeeding.data.network

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
        @Field("name") name: String,
        @Field("type") type: String,
        @Field("key") key: String,
        @Field("deviceType") deviceType: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @PUT("Forgot Password")
    suspend fun forgotPass(
        @Field("username") email: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @GET("get-fields")
    suspend fun getMonitorbloodGlucose() :Response<MonitorbloodGlucoseResponse>

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