package com.eminesa.dailyofspace.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eminesa.dailyofspace.databinding.FragmentDailyPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyPhotoFragment : Fragment() {

    val nasaKey = "sl5ug0ON27Pa9KcknwnYbCwRQI4nwG0vhccg7ZTI"
    var binding: FragmentDailyPhotoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentDailyPhotoBinding.inflate(inflater)

        return binding?.root
    }

}