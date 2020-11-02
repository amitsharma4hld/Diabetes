package com.s.diabetesfeeding.data.network

import com.s.diabetesfeeding.data.db.entities.auth.PatientsResponse
import com.s.diabetesfeeding.data.network.responses.AuthResponse
import com.s.diabetesfeeding.data.network.responses.MonitorbloodGlucoseResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

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

    @FormUrlEncoded
    @PUT("save-step-count")
    suspend fun saveStepCountToServer(
        @Field("user_id") userId: String,
        @Field("steps") steps: String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @PUT("save-data")
    suspend fun saveDiabetesBloodGlucoseDataToServer(
        @Field("user_id") userId: String,
        @Field("type") type: String,
        @Field("wake_up_fasting_points") wake_up_fasting_points: String,
        @Field("wake_up_fasting_time") wake_up_fasting_time: String,
        @Field("wake_up_fasting_total_points") wake_up_fasting_total_points: String,
        @Field("before_breakfast_points") before_breakfast_points: String,
        @Field("before_breakfast_time") before_breakfast_time: String,
        @Field("before_breakfast_total_points") before_breakfast_total_points: String,
        @Field("1hr_after_breakfast_points") one_hr_after_breakfast_points: String,
        @Field("1hr_after_breakfast_time") one_hr_after_breakfast_time: String,
        @Field("1hr_after_breakfast_total_points") one_hr_after_breakfast_total_points: String,
        @Field("2hr_after_breakfast_points") two_hr_after_breakfast_points: String,
        @Field("2hr_after_breakfast_time") two_hr_after_breakfast_time: String,
        @Field("2hr_after_breakfast_total_points") two_hr_after_breakfast_total_points: String,
        @Field("before_lunch_points") before_lunch_points: String,
        @Field("before_lunch_time") before_lunch_time: String,
        @Field("before_lunch_total_points") before_lunch_total_points: String,
        @Field("1hr_after_lunch_points") one_hr_after_lunch_points: String,
        @Field("1hr_after_lunch_time") one_hr_after_lunch_time: String,
        @Field("1hr_after_lunch_total_points") one_hr_after_lunch_total_points: String,
        @Field("2hr_after_lunch_points") two_hr_after_lunch_points: String,
        @Field("2hr_after_lunch_time") two_hr_after_lunch_time: String,
        @Field("2hr_after_lunch_total_points") two_hr_after_lunch_total_points: String,
        @Field("before_dinner_points") before_dinner_points: String,
        @Field("before_dinner_time") before_dinner_time: String,
        @Field("before_dinner_total_points") before_dinner_total_points: String,
        @Field("1hr_after_dinner_points") one_hr_after_dinner_points: String,
        @Field("1hr_after_dinner_time") one_hr_after_dinner_time: String,
        @Field("1hr_after_dinner_total_points") one_hr_after_dinner_total_points: String,
        @Field("2hr_after_dinner_points") two_hr_after_dinner_points: String,
        @Field("2hr_after_dinner_time") two_hr_after_dinner_time: String,
        @Field("2hr_after_dinner_total_points") two_hr_after_dinner_total_points: String,
        @Field("bedtime_points") bedtime_points: String,
        @Field("bedtime_time") bedtime_time: String,
        @Field("bedtime_total_points") bedtime_total_points: String

    ): Response<MonitorbloodGlucoseResponse>


    @FormUrlEncoded
    @PUT("save-data")
    suspend fun saveInsulinDataToServer(
        @Field("user_id") userId: String,
        @Field("type") type: String,
        @Field("insuline_type") insulineType: String,
        @Field("total_daily_dose") totalDailyDose: String,
        @Field("total_carbs") totalCarbs: String,
        @Field("current_blood_glucose") currentBloodGlucose: String,
        @Field("target_blood_glucose") targetBloodGlucose: String,
        @Field("total_points") totalPoints: String
    ): Response<MonitorbloodGlucoseResponse>

    @FormUrlEncoded
    @PUT("save-data")
    suspend fun saveWeightDataToServer(
        @Field("user_id") userId: String,
        @Field("type") type: String,
        @Field("weight") weight: String
    ): Response<MonitorbloodGlucoseResponse>

    @FormUrlEncoded
    @PUT("save-data")
    suspend fun saveSymptomsDataToServer(
        @Field("user_id") userId: String,
        @Field("type") type: String,
        @Field("hypoglycemia") hypoglycemia: String,
        @Field("other") other : String,
        @Field("total_points") total_points : String

    ): Response<MonitorbloodGlucoseResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : ApiInterface {
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