package com.example.corona_final.fragments.click

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.corona_final.R
import com.example.corona_final.api.ApiClient
import com.example.corona_final.api.ApiRepository
import com.example.corona_final.data.Country
import com.example.corona_final.data.ResponseData
import com.example.corona_final.databinding.FragmentClickBinding
import com.example.corona_final.databinding.FragmentCountryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClickFragment : Fragment() {
    lateinit var binding:FragmentClickBinding
    private  val TAG = "ClickFragment"
    var response: ResponseData? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentClickBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callApiService()
        backToFirstFragment()







    }

    private fun backToFirstFragment(){

        binding.button.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun callApiService() {
        val apiService = ApiClient.getClient()
        val apiRepository = ApiRepository(apiService)
        val corona: Call<ResponseData> = apiRepository.getHereData()
        corona.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                this@ClickFragment.response = response.body()
                this@ClickFragment.response?.let { getData() }

            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.i(TAG, "onFailure:$t ")
            }
        })
    }
    private fun getData(){
        val idList = response?.countries
        val id1 =  ClickFragmentArgs.fromBundle(requireArguments()).id

        if (idList != null) {
            for (data in idList){
                if (id1 == data.iD ) {
                    binding.tvCountryClick.text = data.country
                    binding.tvClickNconfirmed.text = data.newConfirmed.toString()
                    binding.tvClickNdeaths.text = data.newDeaths.toString()
                    binding.tvClickNrecoverd.text = data.newRecovered.toString()
                    binding.tvClickTconfirmed.text = data.totalConfirmed.toString()
                    binding.tvClickTotaldead.text = data.totalDeaths.toString()
                    binding.tvClickTrecoveed.text = data.totalRecovered.toString()


                }
            }
        }



    }

}