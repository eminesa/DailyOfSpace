package com.eminesa.photoofspace.presenters

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.eminesa.photoofspace.databinding.ActivityVideoViewBinding
import com.eminesa.photoofspace.model.DailyImage
import com.google.android.youtube.player.YouTubeBaseActivity


class VideoViewActivity : YouTubeBaseActivity() {

    private var binding: ActivityVideoViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoViewBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        // Intent'ten veriyi alma
        val dailyImage = intent.getParcelableExtra<DailyImage>("daily_image")
        val videoUrl = dailyImage?.url
        videoUrl?.let {
            loadVideo(it)
        }
    }

    private fun loadVideo(videoUrl: String) {
        val iframe = "<iframe width=\"100%\" height=\"100%\" src=\"$videoUrl\" frameborder=\"0\" allowfullscreen></iframe>"

        binding?.webview?.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.pluginState = WebSettings.PluginState.ON
            loadData(iframe, "text/html", "utf-8")
        }
    }

}




