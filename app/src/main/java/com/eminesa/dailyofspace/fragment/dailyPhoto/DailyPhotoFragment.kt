package com.eminesa.dailyofspace.fragment.dailyPhoto

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.util.CoilUtils
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.databinding.FragmentDailyPhotoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class DailyPhotoFragment : Fragment() {

    private var downloadId = 0L

    private var binding: FragmentDailyPhotoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentDailyPhotoBinding.inflate(inflater)

        requireActivity().registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        if (arguments != null) {

            val date = arguments?.getString("date")
            val explanation = arguments?.getString("explanation")
            val title = arguments?.getString("title")
            val mediaType = arguments?.getString("media_type")
            val url = arguments?.getString("url")

            binding?.apply {
                txtTitle.text = title
                txtDescription.text = explanation

                if (mediaType == "video") {
                    //https://www.youtube.com/embed/VYWjxvm14Pk?rel=0
                    imgSpace.isVisible = false
                    imgContentOfVideo.isVisible = true
                    imgDownload.isVisible = false
                    constraintInfo.isVisible = false

                    //url?.let { initUI(it) }
                    //   val youtubePlayerInit = url?.let { initUI(it) }
                    //   binding?.youtubePlayer?.initialize(youtubeApiKey, youtubePlayerInit)
                } else {

                    imgDownload.isVisible = true
                    imgContentOfVideo.isVisible = false
                    imgSpace.isVisible = true

                    imgSpace.load(url) {
                        allowRgb565(true)
                        placeholderMemoryCacheKey(CoilUtils.metadata(imgSpace)?.memoryCacheKey)
                        dispatcher(Dispatchers.IO)
                        placeholder(R.drawable.ic_astronaut)
                        error(R.drawable.ic_astronaut)
                    }

                    imgDownload.setOnClickListener {
                        url?.let { urlWithLet ->
                            //(activity as MainActivity?)?.downloadFile(urlWithLet)
                            imgDownload.isEnabled = false
                            binding?.progressBar?.visibility = View.VISIBLE
                            downloadFile(urlWithLet)

                        }
                    }
                }
            }
        }

        binding?.setOnClickListener()

        return binding?.root
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

    private fun downloadFile(url: String) {

        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            //.setTitle("SpaceDaily")
            .setDescription("Your space image is downloading...")
            .setAllowedOverMetered(true)

        val manager =
            requireContext().getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager

        downloadId = manager.enqueue(request)
    }

    //now checking if download complete
    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadId == id) {

                binding?.imgDownload?.setImageResource(R.drawable.ic_success)
                binding?.progressBar?.visibility = View.GONE

                Toast.makeText(requireContext(), getString(R.string.daily_photo_downloaded), Toast.LENGTH_SHORT).show()

            }
        }
    }

    override fun onDestroy() {
        binding = null
        requireActivity().unregisterReceiver(onDownloadComplete)
        super.onDestroy()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}