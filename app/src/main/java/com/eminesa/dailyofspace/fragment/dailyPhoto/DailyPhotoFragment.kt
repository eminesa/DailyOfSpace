package com.eminesa.dailyofspace.fragment.dailyPhoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eminesa.dailyofspace.databinding.FragmentDailyPhotoBinding
import com.eminesa.dailyofspace.enum.ResponseStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyPhotoFragment : Fragment() {

    private val viewModel: DailyPhotoFragmentViewModel by viewModels()
     private var binding: FragmentDailyPhotoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentDailyPhotoBinding.inflate(inflater)

        getVersionFromService()

        return binding?.root
    }

    private fun getVersionFromService() {
        viewModel.getDailyPhoto("da2QThPK0PiunpjTKv6NUE67Od0L8E78ntl3GuOR").observe(viewLifecycleOwner, { responseVersion ->
            when (responseVersion.status) {
                ResponseStatus.LOADING -> {
                    //internet kontrolu saglaman lazim
                }
                ResponseStatus.SUCCESS -> {
                    val date = responseVersion.data?.date
                    val explanation = responseVersion.data?.explanation
                    val title = responseVersion.data?.title
                    val url = responseVersion.data?.url
                }
                ResponseStatus.ERROR -> {
                }
            }
        })
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}