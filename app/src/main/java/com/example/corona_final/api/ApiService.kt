package com.example.corona_final.api

import com.example.corona_final.data.ResponseData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("summary")
    fun getCoronaDetail(): Call<ResponseData>
}