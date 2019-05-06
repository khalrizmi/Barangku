package com.example.barangku.network

import com.example.barangku.data.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Hamz on 04/05/2019.
 * ilham011001@gmail.com
 */

interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("nis") nis: String,
              @Field("password") password: String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("scan")
    fun scan(@Field("nis") nis: String): Call<UserResponse>

    @FormUrlEncoded
    @POST("change-password")
    fun changePassword(@Field("nis") nis: String,
                       @Field("password") password: String): Call<ChangePasswordResponse>

    @Multipart
    @POST("report")
    fun report(@Part("nis") nis: RequestBody, @Part("nis_to") nisTo: RequestBody,
               @Part("note") note: RequestBody, @Part part: MultipartBody.Part): Call<ReportResponse>

    @GET("news")
    fun news(): Call<NewsResponse>

    @FormUrlEncoded
    @POST("news")
    fun news(@Field("nis") nis: String): Call<NewsResponse>

    @FormUrlEncoded
    @POST("detail-news")
    fun detailNews(@Field("id") id: Int): Call<DetailNewsResponse>
}