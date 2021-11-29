package com.eminesa.dailyofspace.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentSplashBinding.inflate(inflater)

        binding?.imgSplash?.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_dailyPhotoFragment)

        }
        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}