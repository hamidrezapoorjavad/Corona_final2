package com.example.corona_final.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.corona_final.R
import com.example.corona_final.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {
lateinit var bindind :FragmentSplashBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindind = FragmentSplashBinding.inflate(inflater)
        return bindind.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({findNavController()
            .navigate(SplashFragmentDirections.actionSplashFragmentToTotalDataFragment())},7000)
    }

    }
