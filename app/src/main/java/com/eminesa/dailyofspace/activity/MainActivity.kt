package com.eminesa.dailyofspace.activity

import android.app.DownloadManager
import android.net.Uri
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

    fun downloadFile(url: String) {
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            //.setTitle("SpaceDaily")
            .setDescription("Your space image is downloading...")
            .setAllowedOverMetered(true)

        val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

   fun initUI(videoUrl: String): YouTubePlayer.OnInitializedListener {
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