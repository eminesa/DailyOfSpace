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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.eminesa.dailyofspace.R
import com.eminesa.dailyofspace.adapter.PhotoAdapter
import com.eminesa.dailyofspace.clouddb.ObjPhoto
import com.eminesa.dailyofspace.databinding.FragmentDailyPhotoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyPhotoFragment : Fragment() {

    private var downloadId = 0L

    private var binding: FragmentDailyPhotoBinding? = null
    private var photoAdaper: PhotoAdapter? = null
    private val viewModel: DailyPhotoFragmentViewModel by viewModels()
    private val user = ObjPhoto()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null)
            binding = FragmentDailyPhotoBinding.inflate(inflater)

        viewModel.getSpots()
        observeLiveData()

        requireActivity().registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )

        if (photoAdaper == null)
            photoAdaper = PhotoAdapter { txtDescription, photoInfo ->

                if (txtDescription.maxLines < 2) {
                    txtDescription.ellipsize = null
                    txtDescription.maxLines = Integer.MAX_VALUE
                } else {
                    txtDescription.ellipsize = TextUtils.TruncateAt.END
                    txtDescription.maxLines = 1
                }
            }

        if (arguments != null) {

            val date = arguments?.getString("date")
            val explanation = arguments?.getString("explanation")
            val title = arguments?.getString("title")
            val mediaType = arguments?.getString("media_type")
            val url = arguments?.getString("url")

            user.userId = "uniaflskfnpfVKDSFL;lgjpoiafSDJOFbqoeebewafd"
            user.userName = "Emine"
            user.photoAddDate = date
            user.photoTitle = title
            user.photoDesc = explanation?.substring(0,199) ?: explanation // cloud db limitation
            user.urlType = mediaType
            user.photoUrl = url

            binding?.setOnClickListener(user)

        }
        binding?.recyclerViewPhoto?.apply {
            setHasFixedSize(false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = photoAdaper
        }


        return binding?.root
    }

    private fun observeLiveData() {
        viewModel.getSpotListLiveData().observe(requireActivity()) {
            it.add(0, user)
            photoAdaper?.submitList(it)
        }
    }

    private fun FragmentDailyPhotoBinding.setOnClickListener(user: ObjPhoto) {
        imgDownload.setOnClickListener {
            viewModel.saveUser(user, requireContext())
        }

        /*  imgDownload.setOnClickListener {
              url?.let { urlWithLet ->
                  //(activity as MainActivity?)?.downloadFile(urlWithLet)
                  imgDownload.isEnabled = false
                  binding?.progressBar?.visibility = View.VISIBLE
                  downloadFile(urlWithLet)

              }
          }  */
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
        photoAdaper = null
        requireActivity().unregisterReceiver(onDownloadComplete)
        super.onDestroy()
    }

    override fun onDestroyView() {
        binding = null
        photoAdaper = null
        super.onDestroyView()
    }
}