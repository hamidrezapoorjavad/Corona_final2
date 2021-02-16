package com.example.corona_final.fragments.totalData

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.corona_final.api.ApiClient
import com.example.corona_final.api.ApiRepository
import com.example.corona_final.data.ResponseData
import retrofit2.Call
import retrofit2.Response

class TotalDataViewModel(appContext: Application) : AndroidViewModel(appContext) {
     var _response = MutableLiveData<ResponseData>()
    val response: LiveData<ResponseData>
    get() = _response

    init {
        callApiServivce()
    }


    fun callApiServivce() {
        val apiService = ApiClient.getClient()
        val apiRepository = ApiRepository(apiService)
        val corona: Call<ResponseData> = apiRepository.getHereData()
        corona.enqueue(object : retrofit2.Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {

                _response.value = response.body()


            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.i(ContentValues.TAG, "onFailure:$t ")
            }
        })
    }

}
