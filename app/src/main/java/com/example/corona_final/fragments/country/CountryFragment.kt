package com.example.corona_final.fragments.country

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.corona_final.R
import com.example.corona_final.data.Country
import com.example.corona_final.databinding.FragmentCountryBinding
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.hypot


class CountryFragment : Fragment() {

    lateinit var countryList: MutableList<Country>

    lateinit var binding: FragmentCountryBinding

    //    var response: ResponseData? = null
    lateinit var viewModel: CountryViewModel

    //    private val TAG = "ResultFragment"
    lateinit var adapter: CountryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountryBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.collapsinfg?.setExpandedTitleColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )



        viewModel.callApiService()
        viewModel.response.observe(viewLifecycleOwner) {
            countryList = it.countries as MutableList<Country>
            initRecyclerView(countryList)
            handleClick(countryList)
            edtSearch(countryList)
        }



        binding.toolbar.setOnClickListener {
            val width = binding.rvCountry.width
            val height = binding.rvCountry.height
            val radius = hypot(width / 2.0, height / 2.0)
            val anim = ViewAnimationUtils.createCircularReveal(
                binding.rvCountry, width / 2, height / 2,
                0f, radius.toFloat()
            )
//            binding.rvCountry.setBackgroundColor(
//                ContextCompat.getColor(
//                    requireContext(),
//                    R.color.gray_600
//                )
//            )
            anim.duration = 5000
            anim.start()
        }
    }


    private fun edtSearch(countryList: MutableList<Country>) {
        binding.edtSearch.addTextChangedListener {

            val country2 = searchByName(countryList, it.toString())

            adapter.submitList(country2)
            adapter.notifyDataSetChanged()
        }
    }

    private fun handleClick(countryList: MutableList<Country>) {
        binding.btnMin.setOnClickListener {

            val country2 = countryList.sortedBy { it.totalDeaths }

            adapter.submitList(country2)
            adapter.notifyDataSetChanged()
        }

        binding.btnMax.setOnClickListener {

            val country2 = countryList.sortedByDescending { it.totalDeaths }


            adapter.submitList(country2)
            adapter.notifyDataSetChanged()
        }
        binding.btnMaxPatients.setOnClickListener {
            val country2 = countryList.sortedByDescending { it.newDeaths }

            adapter.submitList(country2)
            adapter.notifyDataSetChanged()
        }
    }

//    private fun callApiService() {
//        val apiService = ApiClient.getClient()
//        val apiRepository = ApiRepository(apiService)
//        val corona: Call<ResponseData> = apiRepository.getHereData()
//        corona.enqueue(object : Callback<ResponseData> {
//            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
//                this@CountryFragment.response = response.body()
//                initRecyclerView()
//            }
//
//            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//                Log.i(TAG, "onFailure:$t ")
//            }
//        })
//    }

    private fun initRecyclerView(countryList: MutableList<Country>) {
        adapter = CountryAdapter(requireContext()) {
            findNavController()
                .navigate(
                    CountryFragmentDirections
                        .actionCountryFragmentToClickFragment(it)
                )
        }
        adapter.submitList(countryList)
        binding.rvCountry.adapter = adapter
    }

    private fun searchByName(
        countryList: MutableList<Country>,
        keyWod: String
    ): MutableList<Country> {
        val newList = mutableListOf<Country>()
        for (item in countryList) {
            if (item.country!!.contains(keyWod, true)) {
                newList.add(item)
            }
        }
        return newList
    }
}