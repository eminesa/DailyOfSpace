package com.eminesa.dailyofspace.presenters.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.databinding.ActivityMainBinding
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
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

    fun initUI(videoUrl: String): YouTubePlayer.OnInitializedListener { // youtube api key fragmentte calismadigi icin videolar oldugunda oynatilamiyor.
        return object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                youtubePlayer: YouTubePlayer?,
                p2: Boolean
            ) {
                //kod hatasız olursa onInitializationSuccess implament metodu çalışacak
                youtubePlayer?.loadVideo(videoUrl)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                //Eğer hatalı olursa da onInitializationFailure bu implament metodu çalışacak
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.something_was_wrong),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }

    override fun onDestroy() {
        mainBinding = null
        super.onDestroy()
    }

}