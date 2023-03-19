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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.adapter.PhotoAdapter
import com.eminesa.dailyofspace.databinding.FragmentDailyPhotoBinding
import com.eminesa.dailyofspace.model.NasaByIdResponse
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DailyPhotoFragment : Fragment() {

    private var downloadId = 0L

    private var binding: FragmentDailyPhotoBinding? = null
    private var photoAdapter: PhotoAdapter? = null
    private var imageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentDailyPhotoBinding.inflate(inflater)

        requireActivity().registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )

        if (photoAdapter == null)
            photoAdapter = PhotoAdapter(onShowMoreClickListener = { txtDescription, photoInfo ->

                if (txtDescription.maxLines < 2) {
                    txtDescription.ellipsize = null
                    txtDescription.maxLines = Integer.MAX_VALUE
                } else {
                    txtDescription.ellipsize = TextUtils.TruncateAt.END
                    txtDescription.maxLines = 1
                }
            }, itemClickListener = { view, item ->
                //imageUrl = item.photoUrl
            }, translateListener = { titleTextview, descTextView, item ->
                val localeLang = Locale.getDefault().language

            })

        if (arguments != null) {

            val date = arguments?.getString("date")
            val explanation = arguments?.getString("explanation")
            val title = arguments?.getString("title")
            val mediaType = arguments?.getString("media_type")
            imageUrl = arguments?.getString("url")

            val list = mutableListOf(
                NasaByIdResponse(
                    date = date,
                    explanation = explanation,
                    title = title,
                    media_type = mediaType,
                    url = imageUrl
                )
            )
            photoAdapter?.submitList(list)
            binding?.setOnClickListener()


        }
        binding?.recyclerViewPhoto?.apply {
            setHasFixedSize(false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = photoAdapter
        }

        val snapHelper: SnapHelper = LinearSnapHelper() // stay on one item
        snapHelper.attachToRecyclerView(binding?.recyclerViewPhoto)

        return binding?.root
    }

    private fun FragmentDailyPhotoBinding.setOnClickListener() {

        imgDownload.setOnClickListener {
            imgDownload.isEnabled = false
            binding?.progressBar?.visibility = View.VISIBLE
            downloadFile(imageUrl)
        }
    }

    private fun downloadFile(url: String?) {

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

                Toast.makeText(
                    requireContext(),
                    getString(R.string.daily_photo_downloaded),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    override fun onDestroy() {
        binding = null
        photoAdapter = null
        imageUrl = null
        requireActivity().unregisterReceiver(onDownloadComplete)
        super.onDestroy()
    }

    override fun onDestroyView() {
        binding = null
        imageUrl = null
        photoAdapter = null
        super.onDestroyView()
    }
}