package com.example.corona_final.api

import com.example.corona_final.data.ResponseData
import retrofit2.Call

class ApiRepository(val apiService :ApiService) {
    fun getHereData (): Call<ResponseData> {
        return apiService.getCoronaDetail()
    }

}