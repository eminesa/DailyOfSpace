package com.eminesa.dailyofspace.fragment.splash

import android.animation.Animator
import android.os.Bundle
import android.util.Log
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

        binding?.animationView?.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                Log.i("Animation:", "start")
            }

            override fun onAnimationEnd(animation: Animator) {
                Log.i("Animation:", "end")

                findNavController().navigate(R.id.action_splashFragment_to_dailyPhotoFragment)

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

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}