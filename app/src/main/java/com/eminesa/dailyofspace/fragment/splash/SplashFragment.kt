package com.eminesa.dailyofspace.fragment.splash

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.databinding.FragmentSplashBinding
import com.eminesa.dailyofspace.enum.ResponseStatus
import com.eminesa.dailyofspace.fragment.dailyPhoto.DailyPhotoFragmentViewModel
import com.eminesa.dailyofspace.util.Const
import com.yerli.sosyal.utils.storage.LocaleStorageManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null
    private val viewModel: DailyPhotoFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentSplashBinding.inflate(inflater)

        getImageOrVideo { date, explanation, title, mediaType, url ->

            Const.isRepostedPostDeleted.observe(viewLifecycleOwner, {

                if (it) {

                   val isWatchedIntro = LocaleStorageManager.getPreferencesBoolVal("intro")

                    if (isWatchedIntro) {
                        findNavController().navigate(
                            R.id.action_splashFragment_to_dailyPhotoFragment,
                            bundleOf(
                                "date" to date,
                                "explanation" to explanation,
                                "title" to title,
                                "mediaType" to mediaType,
                                "url" to url,
                            )
                        )
                    } else {

                        findNavController().navigate(
                            R.id.action_splashFragment_to_introFragment,
                            bundleOf(
                                "date" to date,
                                "explanation" to explanation,
                                "title" to title,
                                "mediaType" to mediaType,
                                "url" to url,
                            )
                        )
                    }

                    Const.isRepostedPostDeleted.postValue(false)
                }
            })

        }

        binding?.animationView?.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Log.i("Animation:", "start")
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.i("Animation:", "end")
                Const.isRepostedPostDeleted.postValue(true)
            }

            override fun onAnimationCancel(animation: Animator) {
                Log.i("Animation:", "cancel")
            }

            override fun onAnimationRepeat(animation: Animator) {
                Log.i("Animation:", "repeat")
            }
        })

        return binding?.root
    }

    private fun getImageOrVideo(imageOrVideoLoadListener: ((date: String?, explanation: String?, title: String?, mediaType: String?, url: String?) -> Unit)) {

        viewModel.getDailyPhoto(Const.nasaKey).observe(viewLifecycleOwner, { responseVersion ->
            when (responseVersion.status) {
                ResponseStatus.LOADING -> {
                    //internet kontrolu saglaman lazim
                }
                ResponseStatus.SUCCESS -> {
                    val date = responseVersion.data?.date
                    val explanation = responseVersion.data?.explanation
                    val title = responseVersion.data?.title
                    val mediaType = responseVersion.data?.media_type
                    val url = responseVersion.data?.url

                    imageOrVideoLoadListener(date, explanation, title, mediaType, url)
                }
                ResponseStatus.ERROR -> {
                }
            }
        })
    }

    override fun onDestroy() {
        binding = null
        findNavController().currentBackStackEntry?.viewModelStore?.clear()
        super.onDestroy()
    }

    override fun onDestroyView() {
        binding = null
        viewModel.getDailyPhoto(null).removeObservers(viewLifecycleOwner)
        findNavController().currentBackStackEntry?.viewModelStore?.clear()
        super.onDestroyView()
    }

}