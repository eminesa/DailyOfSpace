package com.eminesa.photoofspace.presenters.splash

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eminesa.photoofspace.R
import com.eminesa.photoofspace.databinding.FragmentSplashBinding
import com.eminesa.photoofspace.local_storage.LocalStorageService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null

    @Inject
    lateinit var localStorageService: LocalStorageService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentSplashBinding.inflate(inflater)

        binding?.animationView?.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Log.i("Animation:", "start")
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.i("Animation:", "end")
                navigate()
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

    fun navigate() {
        val isWatchedIntro = localStorageService.getPassIntro() ?: false

        if (isWatchedIntro) {
            findNavController().navigate(
                R.id.action_splashFragment_to_dailyPhotoFragment,
            )
        } else {

            findNavController().navigate(
                R.id.action_splashFragment_to_introFragment,
            )
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}