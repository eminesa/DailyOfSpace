package com.eminesa.dailyofspace.fragment.dailyPhoto

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.eminesa.dailyofspace.Const.Companion.nasaKey
import com.eminesa.dailyofspace.R
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

        getImageOrVideo()
        binding?.setOnClickListener()
        return binding?.root
    }

    private fun getImageOrVideo() {

        viewModel.getDailyPhoto(nasaKey).observe(viewLifecycleOwner, { responseVersion ->
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

                    binding?.apply {
                        txtTitle.text = title
                        txtDescription.text = explanation

                        if (mediaType == "video") {
                            //https://www.youtube.com/embed/VYWjxvm14Pk?rel=0
                            imgSpace.isVisible = false
                            imgContentOfVideo.isVisible = true
                            //url?.let { initUI(it) }
                            //   val youtubePlayerInit = url?.let { initUI(it) }
                            //   binding?.youtubePlayer?.initialize(youtubeApiKey, youtubePlayerInit)

                        } else {
                            imgContentOfVideo.isVisible = false
                            imgSpace.isVisible = true
                            Glide.with(imgSpace.context)
                                .load(url)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.ic_astronaut)
                                .error(R.drawable.ic_astronaut)
                                .into(imgSpace)
                        }
                    }
                }
                ResponseStatus.ERROR -> {
                }
            }
        })
    }

    fun FragmentDailyPhotoBinding.setGradient() {
        val paint = txtDescription.paint
        val width = paint.measureText(txtDescription.text.toString())
        val textShader: Shader = LinearGradient(
            0f, 0f, width, txtDescription.textSize, intArrayOf(
                Color.parseColor("#F97C3C"),
                Color.parseColor("#FDB54E"),
                Color.parseColor("#8446CC")
            ), null, Shader.TileMode.REPEAT
        )

        txtDescription.paint.shader = textShader
    }

    private fun FragmentDailyPhotoBinding.setOnClickListener() {
        txtDescription.setOnClickListener {
            if (txtDescription.maxLines < 3) {
                txtDescription.ellipsize = null
                txtDescription.maxLines = Integer.MAX_VALUE
            } else {
                txtDescription.ellipsize = TextUtils.TruncateAt.END
                txtDescription.maxLines = 2
            }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    /* private fun initUI(video: String): YouTubePlayer.OnInitializedListener {
         return object : YouTubePlayer.OnInitializedListener {
             override fun onInitializationSuccess(
                 p0: YouTubePlayer.Provider?,
                 youtubePlayer: YouTubePlayer?,
                 p2: Boolean
             ) {
                 //kod hatasız olursa onInitializationSuccess implament metodu çalışacak
                 youtubePlayer?.loadVideo(video)
             }

             override fun onInitializationFailure(
                 p0: YouTubePlayer.Provider?,
                 p1: YouTubeInitializationResult?
             ) {
                 //Eğer hatalı olursa da onInitializationFailure bu implament metodu çalışacak
                 Toast.makeText(
                     requireContext(),
                     getString(R.string.something_was_wrong),
                     Toast.LENGTH_LONG
                 ).show()
             }
         }

     }*/

}