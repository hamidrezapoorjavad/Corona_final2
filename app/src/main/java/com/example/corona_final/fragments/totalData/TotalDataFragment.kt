package com.example.corona_final.fragments.totalData

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.corona_final.data.ResponseData
import com.example.corona_final.databinding.FragmentTotalDataBinding
import java.util.logging.Logger.global

class TotalDataFragment : Fragment() {
    lateinit var binding: FragmentTotalDataBinding
//    var response: ResponseData? = null
    lateinit var viewModel :TotalDataViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTotalDataBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(TotalDataViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.callApiServivce()
        viewModel.response.observe(viewLifecycleOwner){
            getData(it)
        }
        refresh()
        nextFragment()
    }

    private fun refresh() {
        binding.imgRefresh.setOnClickListener {
            viewModel.callApiServivce()
        }
    }

//    private fun callApiServivce(){
//        val apiService = ApiClient.getClient()
//        val apiRepository = ApiRepository(apiService)
//        val corona :Call<ResponseData> = apiRepository.getHereData()
//        corona.enqueue(object : retrofit2.Callback<ResponseData> {
//            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
//
//                this@TotalDataFragment.response = response.body()
//
//                this@TotalDataFragment.response?.let { getData(it) }
//
//            }
//
//            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//                Log.i(ContentValues.TAG, "onFailure:$t ")
//            }
//        })
//    }

    private fun nextFragment() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(
                TotalDataFragmentDirections.actionTotalDataFragmentToCountryFragment()
            )
        }
    }
    private fun getData(responseData: ResponseData) {
        binding.tv1.text =responseData.global?.totalDeaths.toString()
        binding.tv3.text = responseData.global?.totalConfirmed.toString()
        binding.tv2.text = responseData.global?.totalRecovered.toString()

    }

}
