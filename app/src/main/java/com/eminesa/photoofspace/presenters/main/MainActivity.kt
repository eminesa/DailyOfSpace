package com.eminesa.photoofspace.presenters.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.eminesa.photoofspace.R
import com.eminesa.photoofspace.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var mainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)
        setNavigation()
    }

    private fun setNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.splashFragment)
    }

    override fun onDestroy() {
        mainBinding = null
        super.onDestroy()
    }

}