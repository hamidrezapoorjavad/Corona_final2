package com.example.corona_final.fragments.country

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.example.corona_final.api.ApiClient
import com.example.corona_final.api.ApiRepository
import com.example.corona_final.data.Country
import com.example.corona_final.data.ResponseData
import com.example.corona_final.databinding.FragmentCountryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CountryFragment : Fragment() {
    lateinit var binding: FragmentCountryBinding
    var response: ResponseData? = null
    lateinit var viewModel: CountryViewModel
    private val TAG = "ResultFragment"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callApiService()
        handleClick()
        edtSearch()
    }



    private fun edtSearch() {
        binding.edtSearch.addTextChangedListener {
            val countryList = response?.countries as MutableList<Country>
            val country2 = searchByName(countryList, it.toString())
            val adapter = CountryAdapter(requireContext()){findNavController()
                .navigate(CountryFragmentDirections
                    .actionCountryFragmentToClickFragment(it.iD.toString()))}
            adapter.submitList(country2)
            binding.rvCountry.adapter = adapter
        }
    }

    private fun handleClick() {
        binding.btnMin.setOnClickListener {
           val countryList =  response?.countries?.sortedBy { it.totalDeaths }
            val adapter = CountryAdapter(requireContext()){findNavController()
                .navigate(CountryFragmentDirections
                .actionCountryFragmentToClickFragment(it.iD.toString()))}
            adapter.submitList(countryList)
            binding.rvCountry.adapter = adapter
        }

        binding.btnMax.setOnClickListener {
            val countryList =  response?.countries?.sortedByDescending { it.totalDeaths }
            val adapter = CountryAdapter(requireContext()){findNavController()
                .navigate(CountryFragmentDirections
                    .actionCountryFragmentToClickFragment(it.iD.toString()))}
            adapter.submitList(countryList)
            binding.rvCountry.adapter = adapter
        }
        binding.btnMaxPatients.setOnClickListener {
            val countryList =  response?.countries?.sortedByDescending { it.newDeaths }
            val adapter = CountryAdapter(requireContext()){findNavController()
                .navigate(CountryFragmentDirections
                    .actionCountryFragmentToClickFragment(it.iD.toString()))}
            adapter.submitList(countryList)
            binding.rvCountry.adapter = adapter

        }
    }


    private fun callApiService() {
        val apiService = ApiClient.getClient()
        val apiRepository = ApiRepository(apiService)
        val corona: Call<ResponseData> = apiRepository.getHereData()
        corona.enqueue(object : Callback<ResponseData> {
            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                this@CountryFragment.response = response.body()
                initRecyclerView()
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.i(TAG, "onFailure:$t ")
            }
        })
    }

    private fun initRecyclerView() {
        val adapter = CountryAdapter(requireContext()){findNavController()
            .navigate(CountryFragmentDirections
                .actionCountryFragmentToClickFragment(it.iD.toString()))}
        adapter.submitList(response?.countries)
        binding.rvCountry.adapter = adapter
    }
    private fun searchByName(countryList: MutableList<Country>, keyWod: String): MutableList<Country> {
        val newList = mutableListOf<Country>()
        for (item in countryList) {
            if (item.country!!.contains(keyWod, true)) {
                newList.add(item)
            } }
        return newList
    }



}